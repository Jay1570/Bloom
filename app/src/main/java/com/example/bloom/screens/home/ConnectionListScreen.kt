package com.example.bloom.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.ui.theme.orange

@Composable
fun ConnectionListScreen(
    onConnectionClick: (Int, String) -> Unit
) {

    val connections = listOf(
        Connection(1, "Charlie", 1, "Hi", "03-12-2024"),
        Connection(1, "Ethan", 0, "Hi", "03-12-2024"),
        Connection(1, "Magnus", 0, "Hi", "03-12-2024"),
        Connection(1, "Hikaru", 0, "Hi", "03-12-2024"),
        Connection(1, "Levy", 2, "Hi", "03-12-2024"),
        Connection(1, "Levon", 0, "Hi", "03-12-2024"),
        Connection(1, "Lex", 0, "Hi", "03-12-2024"),
        Connection(1, "Joe", 0, "Hi", "03-12-2024"),
        Connection(1, "Anish", 0, "Hi", "03-12-2024"),
        Connection(1, "Hans", 0, "Hi", "03-12-2024"),
    )

    LazyColumn {
        items(connections) {
            UserItem(
                chatId = it.id,
                name = it.name,
                lastMessage = it.lastMessage,
                lastMessageTime = it.lastMessageTime,
                unreadCount = it.unreadCount,
                onClick = { onConnectionClick(it.id, it.name) }
            )
        }
    }
}

@Composable
fun UserItem(
    chatId: Int,
    name: String,
    lastMessage: String,
    lastMessageTime: String,
    unreadCount: Int = 0,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(size = 56.dp)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = lastMessageTime,
                    color = if (unreadCount > 0) orange else MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                Text(text = lastMessage, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.weight(1f))
                if (unreadCount > 0) {
                    Text(
                        text = unreadCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(color = orange, shape = CircleShape)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

//TODO Remove this class
data class Connection(
    val id: Int,
    val name: String,
    val unreadCount: Int = 0,
    val lastMessage: String,
    val lastMessageTime: String
)

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
        ConnectionListScreen(onConnectionClick = { _, _ -> })
    }
}