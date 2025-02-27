package com.example.bloom.screens.information

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.screens.CheckBoxListItem

@Composable
fun DatingPreferenceScreen(
    uiState: InformationUiState,
    addOrRemoveDatingPreference: (String) -> Unit
) {
    
    val preferences = listOf(
        "Man",
        "Woman",
        "Non-binary people",
        "Everyone"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Who would you like to date?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Select all the people you're open to meeting",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 10.dp, bottom = 16.dp),
            style = MaterialTheme.typography.labelLarge
        )

        LazyColumn {
            items(preferences) { preference ->
                CheckBoxListItem(
                    label = preference,
                    isChecked = uiState.selectedDatingPreferences.contains(preference),
                    onCheckedChange = {
                        addOrRemoveDatingPreference(preference)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDatingPreferenceScreen() {
    DatingPreferenceScreen(
        uiState = InformationUiState(),
        addOrRemoveDatingPreference = {}
    )
}
