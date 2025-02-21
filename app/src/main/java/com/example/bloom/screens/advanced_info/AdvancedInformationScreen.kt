package com.example.bloom.screens.advanced_info

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.R

@Composable
fun AdvancedInformationScreen(
    navigateToNextScreen: () -> Unit,
    viewModel: AdvancedInformationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tabTitles = listOf(
        R.drawable.baseline_location_pin_24,
        R.drawable.baseline_location_pin_24,
        R.drawable.baseline_location_pin_24
    )
    Scaffold(
        floatingActionButton = {
            Row {
                IconButton(
                    onClick = { if (uiState.currentTab > 0) viewModel.goToPrevious() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                        contentDescription = "",
                        modifier = Modifier.rotate(180f)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { if (uiState.currentTab < tabTitles.size - 1) viewModel.goToNext() else navigateToNextScreen() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .wrapContentSize()
                ) {
                    tabTitles.forEachIndexed { index, icon ->
                        Icon(
                            painter = if (uiState.currentTab == index) painterResource(icon) else painterResource(
                                R.drawable.inactive_dot
                            ),
                            contentDescription = "",
                            tint = if (uiState.currentTab >= index) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .clip(CircleShape)
                                .padding(horizontal = 5.dp)
                                .size(if (uiState.currentTab == index) 40.dp else 10.dp)
                        )
                    }
                }
                AnimatedContent(
                    targetState = uiState.currentTab,
                    label = "animated content",
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInHorizontally { width -> width } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> -width } + fadeOut()
                        } else {
                            slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> width } + fadeOut()
                        }.using(SizeTransform(clip = false))
                    }
                ) { targetTab ->
                    when (targetTab) {
                        0 -> ImageSelectionScreen(
                            images = uiState.images,
                            onAddImage = viewModel::addImage,
                            onRemoveImage = viewModel::removeImage
                        )
                    }
                }
            }
        }
    }
}