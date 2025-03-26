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
fun SexualitySelectionScreen(
    uiState: InformationUiState,
    changeSelectedSexuality: (String) -> Unit
) {
    val options = listOf(
        "Prefer not to say",
        "Straight",
        "Gay",
        "Lesbian",
        "Bisexual",
        "Allosexual",
        "Androsexual",
        "Asexual",
        "Autosexual",
        "Bicurious",
        "Demisexuual",
        "Fluid",
        "Greysexual",
        "Gynesexual",
        "Monosexual",
        "Omisexual",
        "Pansexual",
        "Polysexual",
        "Queer",
        "Questioning",
        "Skoliosexual",
        "Spectrasexual",
        "not listed"
    )

    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What's your sexuality?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(options) { option ->
                RadioButtonListItem(
                    label = option,
                    isSelected = uiState.selectedSexuality == option,
                    onClick = {
                        changeSelectedSexuality(option)
                    }
                )
            }
        }
        Spacer(Modifier.weight(1f))

    }
}

@Preview(showBackground = true)
@Composable
fun SexualitySelectionScreenPreview() {
    BloomTheme {
        SexualitySelectionScreen(
            uiState = InformationUiState(),
            changeSelectedSexuality = {}
        )
    }
}