package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun PronounsSelectionScreen(
    uiState: InformationUiState,
    addOrRemovePronoun: (String) -> Unit
) {
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
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "What are your pronouns?",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display selected pronouns as chips
        if (uiState.selectedPronouns.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(uiState.selectedPronouns) { pronoun ->
                    Chip(
                        text = pronoun,
                        onClose = { addOrRemovePronoun(pronoun) }
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
                .fillMaxWidth(1f)
                .padding(vertical = 16.dp)
                .wrapContentHeight(align = Alignment.Top),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            itemCount = pronouns.size
        ) { index ->
            Card(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            addOrRemovePronoun(pronouns[index])
                        }
                    )
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = pronouns[index],
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Checkbox(
                        checked = uiState.selectedPronouns.contains(pronouns[index]),
                        onCheckedChange = {
                            addOrRemovePronoun(pronouns[index])
                        }
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
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
        PronounsSelectionScreen(
            uiState = InformationUiState(),
            addOrRemovePronoun = {}
        )
    }
}
