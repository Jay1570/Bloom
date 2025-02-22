package com.example.bloom.screens.advanced_info

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TextPromptScreen(
    uiState: AdvancedInformationUiState,
    togglePromptList: () -> Unit,
    toggleTextField: () -> Unit,
    addPrompt: (Int, String, String) -> Unit,
    removePrompt: (Int) -> Unit
) {
    val prompts = listOf(
        "Saying \"Hi!\" in all languages I know",
        "A quick rant about",
        "My self-care routine is",
        "How to pronounce my name",
        "We'll get along if",
        "My greatest strength",
        "I wish more people knew",
        "My most irrational fear",
        "A boundary of mine is",
        "The one thing you should know about me is",
        "Green flags I look out for",
        "Something that's non-negotiable for me is",
        "This year, I really want to"
    )

    var selectedIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedPrompt by rememberSaveable { mutableStateOf("") }
    var answer by rememberSaveable { mutableStateOf("") }
    val selectedPrompts = uiState.selectedTextPrompts
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isTextPromptListVisible) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(prompts) { prompt ->
                    PromptItem(
                        prompt = prompt,
                        onClick = {
                            selectedPrompt = prompt
                            togglePromptList()
                            toggleTextField()
                        }
                    )
                }
            }
        } else if (uiState.isTextFieldVisible) {
            Text(
                text = selectedPrompt,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                placeholder = { Text("Write your answer") }
            )
            Button(
                onClick = {
                    addPrompt(selectedIndex!!, selectedPrompt, answer)
                    toggleTextField()
                    selectedIndex = null
                    selectedPrompt = ""
                    answer = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Done")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(selectedPrompts.size) { index ->
                    SelectedPromptsItem(
                        selectedPrompt = selectedPrompts[index],
                        index = index,
                        togglePromptList = {
                            selectedIndex = index
                            togglePromptList()
                        },
                        removePrompt = removePrompt
                    )
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
private fun SelectedPromptsItem(
    selectedPrompt: Pair<String, String>?,
    index: Int,
    togglePromptList: () -> Unit,
    removePrompt: (Int) -> Unit,
) {
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
}

@Composable
private fun PromptItem(
    prompt: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = prompt
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
            contentDescription = null,
            Modifier.size(10.dp)
        )
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
}