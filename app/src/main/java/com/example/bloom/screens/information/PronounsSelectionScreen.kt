package com.example.bloom.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    var isVisibleOnProfile by remember { mutableStateOf(true) }

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
        // LazyColumn for pronoun options
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pronouns) { pronoun ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = pronoun,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Checkbox(
                        checked = pronoun in selectedPronouns,
                        onCheckedChange = { isChecked ->
                            if (isChecked && selectedPronouns.size < 4) {
                                selectedPronouns.add(pronoun)
                            } else if (! isChecked) {
                                selectedPronouns.remove(pronoun)
                            }
                        }
                    )
                }
            }
        }

        // Visible on profile checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isVisibleOnProfile,
                onCheckedChange = { isVisibleOnProfile = it }
            )
            Text(
                text = "Visible on profile",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun Chip(text: String, onClose: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                modifier = Modifier.padding(end = 4.dp)
            )
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.Black
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PronounsSelectionPreview() {
    BloomTheme {
        PronounsSelectionScreen()
    }
}
