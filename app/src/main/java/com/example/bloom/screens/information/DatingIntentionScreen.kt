package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.screens.RadioButtonListItem
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun DatingIntentionScreen(
    uiState: InformationUiState,
    changeIntention: (String) -> Unit
) {
    val options = listOf(
        "Life pater",
        "Long-term relationship, open to short",
        "Short-term relationship, open to long",
        "Short-term relationship",
        "Figuring out my dating goals",
        "prefer not to say"
    )

    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What's Your dating intention?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(options) { option ->
                RadioButtonListItem(
                    label = option,
                    isSelected = uiState.selectedDatingIntention == option,
                    onClick = {
                        changeIntention(option)
                    }
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Card(
            modifier = Modifier.clickable(
                onClick = {
                    isVisibleOnProfile = !isVisibleOnProfile
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

@Preview(showBackground = true)
@Composable
fun DatingIntentionScreenPreview() {
    BloomTheme {
        DatingIntentionScreen(
            uiState = InformationUiState(),
            changeIntention = {}
        )
    }
}