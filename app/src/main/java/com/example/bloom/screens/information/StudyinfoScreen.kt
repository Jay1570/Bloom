package com.example.bloom.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.screens.RadioButtonListItem
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun StudySelectionScreen(
    uiState: InformationUiState,
    changeSelectedEducation: (String) -> Unit
) {

    val educationLevel = listOf("Secondary school", "Undergrad", "Postgrad", "Prefer not to say")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What's the highest level of you attained?",
            fontSize = 28.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(educationLevel) { education ->
                RadioButtonListItem(
                    label = education,
                    isSelected = uiState.selectedEducation == education,
                    onClick = {
                        changeSelectedEducation(education)
                    }
                )
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun StudySelectionScreenPreview() {
    BloomTheme {
        StudySelectionScreen(
            uiState = InformationUiState(),
            changeSelectedEducation = {}
        )
    }
}