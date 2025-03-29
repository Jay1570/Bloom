package com.example.bloom.screens.connection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.screens.TopBar
import com.example.bloom.ui.theme.BloomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionsScreen(
    onConnectionClick: (String, String) -> Unit
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf("Connections", "Pending")

    Scaffold(
        topBar = {
            TopBar(
                title = "MESSAGES",
                canNavigateBack = false,
//                actions = {
//                    Icon(Icons.Default.FilterAlt, contentDescription = "Filter")
//                    Spacer(Modifier.padding(4.dp))
//                    Icon(Icons.Default.MoreVert, contentDescription = "More")
//                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 0.dp,
                indicator = {},
                divider = {},
                containerColor = Color.Transparent,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .wrapContentSize()
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Card(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .wrapContentSize()
                            .clip(CircleShape)
                            .clickable(onClick = {
                                selectedTab = index
                            }),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedTab == index) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = if (selectedTab == index) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
            when (selectedTab) {
                0 -> ConnectionListScreen(
                    navigateToChat = onConnectionClick
                )

                1 -> PendingListScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionsPreview() {
    BloomTheme {
        ConnectionsScreen(
            onConnectionClick = { _, _ -> },
        )
    }
}

