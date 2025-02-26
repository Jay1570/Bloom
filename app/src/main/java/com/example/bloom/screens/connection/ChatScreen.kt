package com.example.bloom.screens.connection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.ui.theme.orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    id: Int,
    name: String,
    navigateBack: () -> Unit
) {

    val chats = listOf(
        Chat("Hey, wanna go on a hike sometime?", true),
        Chat("I love hiking but I have an injury. Cant hike for 2 more weeks", false),
        Chat("Hi", true)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppBar(onNavigateBack = navigateBack, name = name)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    reverseLayout = true
                ) {
                    items(chats) {
                        ChatMessageItem(it, it.isFromMe)
                    }
                }

                ChatInput(
                    onValueChange = {},
                    onSend = {},
                )
            }
        }
    }
}

@Composable
fun ChatInput(
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "",
            minLines = 1,
            maxLines = 3,
            onValueChange = onValueChange,
            placeholder = { Text(text = "Message") },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterVertically),
        )
        IconButton(
            onClick = onSend,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(OutlinedTextFieldDefaults.MinHeight),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    onNavigateBack: () -> Unit,
    name: String
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ProfileImage(40.dp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = name,
                    modifier = Modifier.padding(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onNavigateBack)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "",
                    modifier = Modifier.rotate(180f)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(Icons.Outlined.Videocam, contentDescription = "Video Call")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        }
    )
}

@Composable
fun ChatMessageItem(chat: Chat, isFromMe: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = when (isFromMe) {
            true -> Alignment.End
            false -> Alignment.Start
        },
    ) {
        Card(
            modifier = Modifier
                .widthIn(
                    max = (0.7f * LocalConfiguration.current.screenWidthDp.dp.value).dp,
                    min = 40.dp
                )
                .align(if (isFromMe) Alignment.End else Alignment.Start),
            shape = RoundedCornerShape(
                topStart = 30f,
                topEnd = 30f,
                bottomStart = if (isFromMe) 30f else 0f,
                bottomEnd = if (isFromMe) 0f else 30f
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isFromMe) orange else MaterialTheme.colorScheme.surfaceContainer,
                contentColor = if (isFromMe) Color.White else MaterialTheme.colorScheme.onSurface,
            ),
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = chat.message,
            )
        }
    }
}

//TODO remove this class
data class Chat(
    val message: String,
    val isFromMe: Boolean,
)

@Preview
@Composable
fun ChatScreenPreview() {
    BloomTheme {
        ChatScreen(name = "Charlie", id = 0, navigateBack = {})
    }
}