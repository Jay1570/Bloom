package com.example.bloom.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.screens.CheckBoxListItem
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun EthnicitySelectionScreen(
    uiState: InformationUiState,
    addOrRemoveEthnicity: (String) -> Unit
) {

    val region = listOf(
        "Black/African Descent",
        "East Asian",
        "Hispanic/Lation",
        "Middle Eastern",
        "Native American",
        "Pacific Islander",
        "South Asian",
        "Southeast Asian",
        "White/Caucasian",
        "Other",
        "Open to all"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What's Your ethnicity?",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(region) { ethnicity ->
                CheckBoxListItem(
                    label = ethnicity,
                    isChecked = uiState.selectedEthnicity.contains(ethnicity),
                    onCheckedChange = {
                        addOrRemoveEthnicity(ethnicity)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegionScreenPreview() {
    BloomTheme {
        EthnicitySelectionScreen(
            uiState = InformationUiState(),
            addOrRemoveEthnicity = {}
        )
    }
}
