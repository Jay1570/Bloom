package com.example.bloom.screens.advanced_info

import android.provider.OpenableColumns
import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import com.example.bloom.UserPreference
import com.example.bloom.model.User_prompt
import com.example.bloom.model.insertinfo
import com.example.bloom.model.insertinformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class AdvancedInformationViewModel(val userPreference: UserPreference,val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(AdvancedInformationUiState())
    val uiState get() = _uiState.asStateFlow()

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null

    private  val userid=userPreference.user.value
    private val currentTab: Int get() = _uiState.value.currentTab

    fun goToPrevious() {
        _uiState.update { it.copy(currentTab = currentTab - 1) }
    }

    fun goToNext(navigateToNext: () -> Unit) {
        val imageCount = _uiState.value.images.count { it != Uri.EMPTY }
        val promptCount = _uiState.value.selectedTextPrompts.count { it != null }
        when (currentTab) {
            0 -> if (imageCount >= 1) incrementCurrentTab() else showSnackbar("Please select at least 1 images")
//            1 -> if (_uiState.value.recorderAudioUri != null) incrementCurrentTab() else showSnackbar(
//                "Please record audio"
//            )

            1 -> if (promptCount >= 1) navigateToNext() else showSnackbar("Please select at least 1 text prompts")
        }
    }

    fun addImage(index: Int, imageUri: Uri) {
        val updatedImages = _uiState.value.images.toMutableList().apply {
            this[index] = imageUri
            Log.d("images",imageUri.toString())

        }
        _uiState.update { it.copy(images = updatedImages) }
    }

    fun removeImage(index: Int) {
        val updatedImages = _uiState.value.images.toMutableList().apply {
            this[index] = Uri.EMPTY
        }
        _uiState.update { it.copy(images = updatedImages) }
    }

//    fun updatePrompt(newPrompt: String) {
//        _uiState.update {
//            it.copy(selectedVoicePrompt = newPrompt)
//        }
//    }
//
//    fun startRecording(context: Context) {
//        if (_uiState.value.isRecording) return
//        try {
//            outputFile = File(context.filesDir, "voice_prompt.3gp")
//
//            mediaRecorder = MediaRecorder().apply {
//                setAudioSource(MediaRecorder.AudioSource.MIC)
//                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//                setOutputFile(outputFile!!.absolutePath)
//                setMaxDuration(30000)
//                try {
//                    prepare()
//                    start()
//                    _uiState.update { it.copy(isRecording = true) }
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        if (_uiState.value.isRecording) stopRecording()
//                    }, 30000)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        } catch (e: Exception) {
//            showSnackbar(e.message.toString())
//        }
//    }
//
//    fun stopRecording() {
//        if (!_uiState.value.isRecording) return
//
//        mediaRecorder?.apply {
//            stop()
//            release()
//        }
//        mediaRecorder = null
//        val uri = outputFile?.let { Uri.fromFile(it) }
//        _uiState.update {
//            it.copy(
//                isRecording = false,
//                recorderAudioUri = uri
//            )
//        }
//    }
//
//    fun playRecording(context: Context) {
//        if (_uiState.value.recorderAudioUri != null) {
//            MediaPlayer().apply {
//                setDataSource(context, _uiState.value.recorderAudioUri!!)
//                prepare()
//                start()
//            }
//        } else {
//            showSnackbar("Recording is not available")
//        }
//    }

    fun addTextPrompt(index: Int, prompt: String, answer: String) {
        val newList = _uiState.value.selectedTextPrompts.toMutableList().apply {
            this[index] = Pair(prompt, answer)
            Log.d("prompts","$prompt | $answer")
            senddata(index.toString(),prompt,answer)
        }
        _uiState.update { it.copy(selectedTextPrompts = newList) }
    }

    fun removeTextPrompt(index: Int) {
        val newList = _uiState.value.selectedTextPrompts.toMutableList().apply {
            this[index] = null
        }
        _uiState.update { it.copy(selectedTextPrompts = newList) }
    }

    fun toggleTextPromptList() {
        _uiState.update { it.copy(isTextPromptListVisible = !it.isTextPromptListVisible) }
    }

    fun toggleTextField() {
        _uiState.update { it.copy(isTextFieldVisible = !it.isTextFieldVisible) }
    }

    private fun incrementCurrentTab() {
        _uiState.update { it.copy(currentTab = currentTab + 1) }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }

    private fun senddata(promptid: String,prompt: String,answer: String){
        CoroutineScope(Dispatchers.IO).launch {
            val userData= User_prompt(userID = userid, promptID = promptid, prompt = prompt, answer = answer)
            Log.d("userdata",userData.toString())
            val client = OkHttpClient()
            val jsonMediaType = "application/json; charset=utf-8".toMediaType()
            val jsonBody = Json.encodeToString(userData)

            val requestBody = jsonBody.toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url("http://192.168.0.131:8100/insert")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("failure","Error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("success",response.body?.string() ?: "No response")
                }
            })
        }
    }

}




data class AdvancedInformationUiState(
    val currentTab: Int = 0,
    val images: List<Uri> = List(6) { Uri.EMPTY },
    val selectedVoicePrompt: String = "The way to win me over",
    val isRecording: Boolean = false,
    val recorderAudioUri: Uri? = null,
    val selectedTextPrompts: List<Pair<String, String>?> = List(3) { null },
    val isTextPromptListVisible: Boolean = false,
    val isTextFieldVisible: Boolean = false
)