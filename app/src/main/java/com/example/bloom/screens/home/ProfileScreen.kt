package com.example.bloom.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.bloom.R
import com.example.bloom.screens.TopBar
import com.example.bloom.screens.advanced_info.TextPromptScreen
import com.example.bloom.screens.advanced_info.deleteFile
import com.example.bloom.screens.information.PronounsSelectionScreen
import com.example.bloom.screens.information.WorkplaceSelectionScreen
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.ui.theme.orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateToSettings: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val visibilityState by viewModel.visibilityState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopBar(
                title = "PROFILE",
                canNavigateBack = false,
                actions = {
                    IconButton(
                        onClick = navigateToSettings,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .nestedScroll(rememberNestedScrollInteropConnection()),
            horizontalAlignment = Alignment.Start
        ) {
            if (visibilityState.selectedPronouns) {
                PronounsSelectionScreen(
                    uiState = uiState.informationUiState,
                    addOrRemovePronoun = { viewModel.addOrRemovePronoun(it) }
                )
            } else if(visibilityState.workPlace) {
                WorkplaceSelectionScreen(
                    uiState = uiState.informationUiState,
                    onWorkPlaceChange = { viewModel.onWorkPlaceChange(it) }
                )
            } else {
                HeadingText("Images")

                HeadingText("Written Prompts")
                TextPrompts(
                    selectedPrompts = uiState.selectedTextPrompts,
                    isTextPromptListVisible = visibilityState.textPromptList,
                    isTextFieldVisible = visibilityState.textPromptTextField,
                    togglePromptList = { viewModel.toggleTextPromptList() },
                    toggleTextField = { viewModel.toggleTextField() },
                    removePrompt = { viewModel.removeTextPrompt(it) },
                )
            }

        }
    }
}

@Composable
fun ImageSelection(
    images: List<String>,
    onClick: (Int)
) {

}

@Composable
fun TextPrompts(
    selectedPrompts: List<Pair<String, String>?>,
    isTextPromptListVisible: Boolean,
    isTextFieldVisible: Boolean,
    removePrompt: (Int) -> Unit,
    togglePromptList: () -> Unit,
    toggleTextField: () -> Unit
) {
    selectedPrompts.forEachIndexed { index, selectedPrompt ->
        Box(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 70.dp)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    if (selectedPrompt == null) {
                        togglePromptList()
                    } else {
                        removePrompt(index)
                    }
                }
                .padding(8.dp)
        ) {
            if (selectedPrompt == null) {
                Text(
                    text = "Select a Prompt and Write your own answer",
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = selectedPrompt.first,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = selectedPrompt.second,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun HeadingText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    BloomTheme {
        ProfileScreen(navigateToSettings = {})
    }
}