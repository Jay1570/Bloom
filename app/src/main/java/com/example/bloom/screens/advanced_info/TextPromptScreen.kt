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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
        "This year, I really want to",
        "Guess the song",
        "Biggest risk I've taken",
        "I feel proudest of who i am when",
        "What i order for the table",
        "My cru-in-the-car song is",
        "A life goal of mine",
        "I'm looking for",
        "Best travel story",
        "Change my mind about",
        "My best celebrity impression",
        "My biggest date fail",
        "Unusual skills",
        "Let's debate this topic",
        "My Happy Place",
        "My best dad joke",
        "Tow truths and a lie",
        "I wish i could tell the younger version of myself",
        "My friends ask me for advice about",
        "A random fact i love is",
        "My last journal entry was about",
        "The first time i knew i was gay was",
        "Typical Sunday",
        "One thing i'll never do again",
        "Gender euphoria looks like",
        "To me, relaxation is",
        "The best way to ask me about is by",
        "My favourite line from a film",
        "You should *not* go out with me if",
        "The way to win me over is",
        "I'll give you the set-up;you guess the punchline",
        "Do You agree or disagree that",
        "Don't hate me if i",
        "We're the same type of weird if",
        "i'll brag about you to my friend if",
        "I feel most supported when",
        "My BFF's reasons for why you should date me",
        "You should leave a comment if",
        "A thought i recently had in the shower",
        "I hype myself up by",
        "I'm weirdly attracted to",
        "Let's make sure we're on the same page about",
        "I recently discovered that",
        "If loving this is wrong,i don't want to be right",
        "First round is on me if",
        "Give me travel trips for",
        "All i ask is that you",
        "My Love Language is",
        "The dorkiest thing about me is",
        "I want someone who",
        "I unwind by",
        "What if i told you that",
        "I'd fall for you if",
        "When i need advice,i go to",
        "There recently taught me",
        "Teach me something about",
        "Try to guess this about me",
        "The one thing i'd love to know about you is",
        "My therapist would say i",
        "Worst idea i've ever had",
        "I go crazy for",
        "Apparently,my life's soundtrack is",
        "I won't shut up about",
        "My simple pleasures",
        "I geek out on",
        "Weirdest gift l've given or received",
        "The key to my heart is",
        "I beat my blues by",
        "Together,we could",
        "I know the best spot in town for",
        "The hallmark of a good relationship is",
        "Most spontaneous thing l've done",
        "I bet you can't",
        "Proof i have musical talent",
        "Dating me is like",
        "The last time i cried happy tears was",
        "Never have i ever",
        "I'll pick the topic if you start the conversation"
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }
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
                modifier = Modifier.padding(bottom = 10.dp)

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
                    if (selectedIndex != -1) {
                        addPrompt(selectedIndex, selectedPrompt, answer)
                        toggleTextField()
                        selectedIndex = -1
                        selectedPrompt = ""
                        answer = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
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
            .padding(vertical = 20.dp),
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