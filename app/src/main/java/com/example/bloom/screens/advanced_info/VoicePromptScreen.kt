package com.example.bloom.screens.advanced_info

import android.Manifest
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.R
import com.example.bloom.screens.RequestPermissionDialog
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.ui.theme.orange

@Composable
fun VoicePromptScreen(
    uiState: AdvancedInformationUiState,
    onStartRecording: (Context) -> Unit,
    onStopRecording: () -> Unit,
    onPlayRecording: (Context) -> Unit,
    onPromptChange: (String) -> Unit
) {
    val context = LocalContext.current
    RequestPermissionDialog(
        title = "give voice recording permission",
        permission = Manifest.permission.RECORD_AUDIO
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add a Voice Prompt to your profile",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = uiState.selectedVoicePrompt,
            onValueChange = { onPromptChange(it) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = { Icon(Icons.Default.Edit, contentDescription = "Edit") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(color = DividerDefaults.color)
            )
            IconButton(
                onClick = {
                    if (uiState.isRecording) onStopRecording()
                    else onStartRecording(context)
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = orange,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
            ) {
                Icon(
                    painter = if (uiState.isRecording) painterResource(R.drawable.waves) else painterResource(
                        R.drawable.ac_2_voice
                    ),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(color = DividerDefaults.color)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onPlayRecording(context)
            },
            colors = ButtonDefaults.buttonColors(containerColor = orange)
        ) {
            Text(text = "Play Recorded Audio", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoicePromptPreview() {
    BloomTheme {
        VoicePromptScreen(
            uiState = AdvancedInformationUiState(),
            onPromptChange = {},
            onStopRecording = {},
            onStartRecording = {},
            onPlayRecording = {}
        )
    }
}