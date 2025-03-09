package com.example.bloom.screens.advanced_info

import android.media.MediaRecorder
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class AdvancedInformationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdvancedInformationUiState())
    val uiState get() = _uiState.asStateFlow()

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null

    private val currentTab: Int get() = _uiState.value.currentTab

    fun goToPrevious() {
        _uiState.update { it.copy(currentTab = currentTab - 1) }
    }

    fun goToNext(navigateToNext: () -> Unit) {
        val imageCount = _uiState.value.images.count { it != Uri.EMPTY }
        val promptCount = _uiState.value.selectedTextPrompts.count { it != null }
        when (currentTab) {
            0 -> if (imageCount == 6) incrementCurrentTab() else showSnackbar("Please select at least 6 images")
//            1 -> if (_uiState.value.recorderAudioUri != null) incrementCurrentTab() else showSnackbar(
//                "Please record audio"
//            )

            1 -> if (promptCount == 3) navigateToNext() else showSnackbar("Please select at least 3 text prompts")
        }
    }

    fun addImage(index: Int, imageUri: Uri) {
        val updatedImages = _uiState.value.images.toMutableList().apply {
            this[index] = imageUri
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