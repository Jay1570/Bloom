package com.example.bloom.screens.basic_information

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.bloom.screens.RequestPermissionDialog

@Composable
fun NotificationScreen() {
    var isNotificationEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Never miss a message from someone great",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding( top = 15.dp, bottom = 25.dp)
        )

        Button(
            onClick = { isNotificationEnabled = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isNotificationEnabled) Color(0xFF007842) else Color(
                    0xFFE0E0E0
                )
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth().height(50.dp).padding(horizontal = 5.dp)
        ) {
            Text(text = "Enable notifications", color = Color.Black)
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { isNotificationEnabled = false },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isNotificationEnabled) Color(0xFFFF5252) else Color(
                    0xFFE0E0E0
                )
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth().height(50.dp).padding(horizontal = 5.dp)
        ) {
            Text(text = "Disable notifications", color = Color.Black)
        }
    }
    if (isNotificationEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RequestPermissionDialog(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            title = "give notification permission"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationSettingsUI() {
    NotificationScreen()
}
