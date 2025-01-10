package com.example.bloom.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.ui.theme.BloomTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionsScreen(
    onConnectionClick: (Int, String) -> Unit
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf("Connections", "Pending")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MESSAGES",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                },
                actions = {
                    Card(modifier = Modifier.clip(RoundedCornerShape(100))) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Circle,
                                contentDescription = "Active",
                                tint = Color.Green,
                                modifier = Modifier.size(8.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Active",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(Icons.Default.FilterAlt, contentDescription = "Filter")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding())
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
                    onConnectionClick = onConnectionClick
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