package com.example.bloom.screens.basic_information

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NotificationScreen() {
    var isNotificationEnabled by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Never miss a message from someone great",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp, top = 24.dp)
        )

        // Enable Notifications Button
        Button(
            onClick = { isNotificationEnabled = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isNotificationEnabled == true) Color(0xFFB2FF59) else Color(
                    0xFFE0E0E0
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Enable notifications", color = Color.Black)
        }

        // Disable Notifications Button
        Button(
            onClick = { isNotificationEnabled = false },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isNotificationEnabled == false) Color(0xFFFF5252) else Color(
                    0xFFE0E0E0
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Disable notifications", color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationSettingsUI() {
    NotificationScreen()
}
