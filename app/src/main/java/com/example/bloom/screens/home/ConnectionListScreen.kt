package com.example.bloom.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun ConnectionListScreen() {
    UserItem(
        openScreen = { /*TODO*/ },
        unreadCount = 1
    )
}

@Composable
fun UserItem(
    chatId: String = "",
    openScreen: (String) -> Unit,
    unreadCount: Int = 0,
    onUserClick: (String, (String) -> Unit) -> Unit = { _, _ -> },
    onChatClick: (String, (String) -> Unit) -> Unit = { _, _ -> },
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { /*TODO*/ }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(size = 56.dp)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "Charlie", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Hi", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "03-12-2024", color = Color(0xFFDB571E), style = MaterialTheme.typography.bodySmall)
            if (unreadCount > 0) {
                Text(
                    text = unreadCount.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(color = Color(0xFFDB571E), shape = CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileImage(size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.user),
        contentDescription = "Profile",
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun ConnectionListPreview() {
    BloomTheme {
        ConnectionListScreen()
    }
}