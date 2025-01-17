package com.example.bloom.screens.basic_information

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
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun BasicInformationScreen(navigateToNextScreen: () -> Unit) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(
        R.drawable.outline_person_24,
        R.drawable.outline_heart_broken_24,
        R.drawable.outline_person_24
    )
    Scaffold(
        floatingActionButton = {
            Row {
                IconButton(
                    onClick = { if (selectedTab > 0) selectedTab -- },
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
                    onClick = { if (selectedTab < tabTitles.size - 1) selectedTab ++ else navigateToNextScreen() },
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
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
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
                when (selectedTab) {
                    0 -> NameScreen()
                    1 -> DateOfBirthScreen()
                    2 -> NotificationScreen()
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