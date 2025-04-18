package com.example.bloom.screens.connection

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.AppViewModelProvider
import com.example.bloom.model.Chat
import com.example.bloom.ui.theme.orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navigateBack: () -> Unit,
    viewModel: ChatViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    LaunchedEffect(uiState.chats) {
        viewModel.markAllAsRead()
        if (uiState.chats.isNotEmpty()) {
            listState.scrollToItem(uiState.chats.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppBar(onNavigateBack = navigateBack, name = uiState.name)
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                val groupedChats: Map<String, List<Chat>> = uiState.groupedChats()
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    state = listState,
                    reverseLayout = false
                ) {
                    groupedChats.forEach { (date, chats) ->
                        item {
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        items(chats) { chat ->
                            ChatMessageItem(
                                chat = chat,
                                isFromMe = chat.senderId == uiState.currentUser
                            )
                        }
                    }
                }

                ChatInput(
                    value = uiState.message,
                    onValueChange = viewModel::onMessageChange,
                    onSend = viewModel::onSend,
                )
            }
        }
    }
}

@Composable
fun ChatInput(
    value: String,
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
            value = value,
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
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = name,
                    fontSize = 22.sp,
                    modifier = Modifier.wrapContentWidth(),
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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                )
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