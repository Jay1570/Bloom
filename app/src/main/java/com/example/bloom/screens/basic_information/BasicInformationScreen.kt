package com.example.bloom.screens.basic_information

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun BasicInformationScreen(
    navigateToNextScreen: () -> Unit,
    viewModel: BasicInformationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(
        R.drawable.outline_person_24,
        R.drawable.outline_heart_broken_24,
        R.drawable.outline_person_24
    )
    Scaffold(
        floatingActionButton = {
            Row {
                if (selectedTab > 0) {
                    IconButton(
                        onClick = { if (selectedTab > 0) selectedTab-- },
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
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { if (selectedTab < tabTitles.size - 1) selectedTab++ else navigateToNextScreen() },
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
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .wrapContentSize()
                ) {
                    tabTitles.forEachIndexed { index, icon ->
                        Icon(
                            painter = if (selectedTab == index) painterResource(icon) else painterResource(
                                R.drawable.inactive_dot
                            ),
                            contentDescription = "",
                            tint = if (selectedTab >= index) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .clip(CircleShape)
                                .padding(horizontal = 5.dp)
                                .size(if (selectedTab == index) 40.dp else 10.dp)
                        )
                    }
                }
                AnimatedContent(
                    targetState = selectedTab,
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
                        0 -> NameScreen(
                            uiState = uiState,
                            onFirstNameChange = viewModel::onFirstNameChange,
                            onLastNameChange = viewModel::onLastNameChange
                        )

                        1 -> DateOfBirthScreen(
                            uiState = uiState,
                            onDateChange = viewModel::onDateChange,
                            onConfirmClick = viewModel::onConfirmClick,
                            onDialogVisibilityChange = viewModel::onDialogVisibilityChange,
                            navigateToNextScreen = navigateToNextScreen
                        )

                        2 -> NotificationScreen()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ConnectionsPreview() {
    BloomTheme {
        BasicInformationScreen(navigateToNextScreen = {})
    }
}