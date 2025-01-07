package com.example.bloom.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.viewmodels.FavouriteInterestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteInterestScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavouriteInterestViewModel = viewModel()
) {
    var selectedInterest by remember { mutableStateOf("Gym") }
    val interests = listOf(
        "Gym",
        "Volleyball",
        "Cafe-hopping",
        "Interior Designer",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Favourite Interests") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Column(
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Which one of your interests is your favourite?",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "It'll stand out on your profile, and you can change it any time.",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp)
                ) {
                    interests.forEach { interest ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .height(55.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .selectable(
                                        onClick = { selectedInterest = interest },
                                        selected = (selectedInterest == interest)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = interest,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(30.dp),
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(Modifier.padding(8.dp))
                                Text(
                                    text = interest,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(
                                    selected = (selectedInterest == interest),
                                    onClick = { selectedInterest = interest },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }
                        }
                    }
                }
                Column {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Text(text = "Save", fontSize = 20.sp)
                    }
                    OutlinedButton(
                        onClick = navigateToHome,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Text(text = "Remove", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    BloomTheme(darkTheme = false) {
        FavouriteInterestScreen(
            navigateToHome = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DarkPreview() {
    BloomTheme(darkTheme = true) {
        FavouriteInterestScreen(
            navigateToHome = {}
        )
    }
}