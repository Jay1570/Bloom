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

@Composable
fun GenderSelectionScreen(
    uiState: InformationUiState,
    changeSelectedGender: (String) -> Unit
) {
    val genders = listOf("Man", "Woman", "Non-binary")
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Which gender best describes you?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We match daters using three broad gender groups.\nYou can add more about your gender after.",
            fontSize = 14.sp,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(genders) { gender ->
                RadioButtonListItem(
                    label = gender,
                    isSelected = uiState.selectedGender == gender,
                    onClick = {
                        changeSelectedGender(gender)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenderSelectionScreen() {
    GenderSelectionScreen(
        uiState = InformationUiState(),
        changeSelectedGender = {}
    )
}
