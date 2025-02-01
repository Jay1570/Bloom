package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PronounsSelectionScreen() {
    val pronouns = listOf(
        "she",
        "her",
        "hers",
        "he",
        "him",
        "his",
        "They",
        "Them",
        "this",
        "xe",
        "xem",
        "xyrs",
        "zir",
        "zirs",
        "Not listed"
    )
    val selectedPronouns = remember { mutableStateListOf<String>() }
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What are your pronouns?",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display selected pronouns as chips
        if (selectedPronouns.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedPronouns) { pronoun ->
                    Chip(
                        text = pronoun,
                        onClose = { selectedPronouns.remove(pronoun) }
                    )
                }
            }
        }
        Text(
            text = "Select up to 4",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // ContextualFlowRow for pronoun options
        ContextualFlowRow(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxWidth(1f)
                .padding(vertical = 16.dp)
                .wrapContentHeight(align = Alignment.Top)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            itemCount = pronouns.size
        ) { index ->
            Card(
                modifier = Modifier.clickable(
                    onClick = {
                        if (selectedPronouns.contains(pronouns[index])) {
                            selectedPronouns.remove(pronouns[index])
                        } else if (selectedPronouns.size < 4) {
                            selectedPronouns.add(pronouns[index])
                        }
                    }
                )
            ) {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = pronouns[index],
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Checkbox(
                        checked = selectedPronouns.contains(pronouns[index]),
                        onCheckedChange = {
                            if (selectedPronouns.contains(pronouns[index])) {
                                selectedPronouns.remove(pronouns[index])
                            } else if (selectedPronouns.size < 4) {
                                selectedPronouns.add(pronouns[index])
                            }
                        }
                    )
                }
            }
        }
        Card(
            modifier = Modifier.clickable(
                onClick = {
                    isVisibleOnProfile = ! isVisibleOnProfile
                }
            )
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isVisibleOnProfile,
                    onCheckedChange = { isVisibleOnProfile = it }
                )
                Text(
                    text = "Visible on profile",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun Chip(text: String, onClose: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(end = 4.dp)
            )
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipPreview() {
    BloomTheme {
        Chip(
            text = "he",
            onClose = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PronounsSelectionPreview() {
    BloomTheme {
        PronounsSelectionScreen()
    }
}
