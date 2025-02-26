package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun StudySelectionScreen() {
    val StudyAt = listOf("Secondary school", "Undergrad", "Postgrad", "Prefer not to say")

    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What's the highest level of you attained?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(StudyAt) { StudyAt ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable {
                            selectedOption = StudyAt
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = StudyAt,
                        fontSize = 18.sp,
                    )
                    Spacer(Modifier.weight(1f))
                    RadioButton(
                        selected = selectedOption == StudyAt,
                        onClick = { selectedOption = StudyAt }
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 1.dp
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Card {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VisibilityOff,
                    contentDescription = "",
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
                )
                Text(
                    text = "Hidden on profile",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudySelectionScreenPreview() {
    BloomTheme {
        StudySelectionScreen()
    }
}