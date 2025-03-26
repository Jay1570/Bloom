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
fun FamilyPlanScreen(
    uiState: InformationUiState,
    changeFamilyPlan: (String) -> Unit
) {
    val childOptions = listOf(
        "Don't want children",
        "Want children",
        "Open to children",
        "Not sure yet",
        "Prefer not to say"
    )

    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Text(
            text = "What are your family plans?",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(childOptions) { option ->
                RadioButtonListItem(
                    label = option,
                    isSelected = uiState.selectedFamilyPlan == option,
                    onClick = {
                        changeFamilyPlan(option)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyPlanScreenPreview() {
    BloomTheme {
        FamilyPlanScreen(
            uiState = InformationUiState(),
            changeFamilyPlan = {}
        )
    }
}
