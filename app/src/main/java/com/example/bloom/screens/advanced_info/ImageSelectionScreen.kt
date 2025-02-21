package com.example.bloom.screens.advanced_info

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File
import java.io.FileOutputStream

@Composable
fun ImageSelectionScreen(
    images: List<Uri>,
    onAddImage: (Int, Uri) -> Unit,
    onRemoveImage: (Int) -> Unit
) {

    var clickedIndex by remember { mutableIntStateOf(-1) }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                if (clickedIndex != -1) {
                    cropImage(
                        context = context,
                        sourceUri = uri,
                        onCropped = {
                            onAddImage(clickedIndex, it)
                            clickedIndex = -1
                        })
                }
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pick your photos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(images.size) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            if (images[index] == Uri.EMPTY) {
                                clickedIndex = index
                                launcher.launch("image/*")
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (images[index] == Uri.EMPTY) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Image",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(images[index])
                                .build(),
                            contentDescription = "Selected Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    deleteFile(context, images[index])
                                    onRemoveImage(index)
                                }
                        )
                    }
                }
            }
        }

        Text(
            text = "Click on the Image to Remove it\n6 required",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

fun deleteFile(context: Context, uri: Uri) {
    val file = File(context.filesDir, uri.lastPathSegment!!)
    file.delete()
}

fun cropImage(context: Context, sourceUri: Uri, onCropped: (Uri) -> Unit) {
    val croppedUri = getTempCroppedImageUri(context, sourceUri)
    val cropIntent = Intent("com.android.camera.action.CROP").apply {
        setDataAndType(croppedUri, "image/*")
        putExtra("crop", "true")
        putExtra("aspectX", 1)
        putExtra("aspectY", 1)
        putExtra("outputX", 512)
        putExtra("outputY", 512)
        putExtra("return-data", false)
        putExtra(MediaStore.EXTRA_OUTPUT, croppedUri)
        putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    val launcher = (context as ComponentActivity).activityResultRegistry.register(
        "cropImage",
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onCropped(croppedUri)
        }
    }

    launcher.launch(cropIntent)
}

private fun getTempCroppedImageUri(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "temp_image_${System.currentTimeMillis()}.jpg")

    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

@Preview(showBackground = true)
@Composable
fun PreviewImagePickerScreen() {
    ImageSelectionScreen(
        images = List(6) { Uri.EMPTY },
        onAddImage = { _, _ -> },
        onRemoveImage = {}
    )
}