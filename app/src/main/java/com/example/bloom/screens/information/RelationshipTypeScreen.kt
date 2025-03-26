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
import com.example.bloom.screens.CheckBoxListItem
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun RelationshipTypeScreen(
    uiState: InformationUiState,
    addOrRemoveRelationshipType: (String) -> Unit
) {
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    val relation = listOf(
        "Monogamy",
        "Non-Monogamy",
        "Figuring out my relationship type"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "What is the type of relationship are you looking for?",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxHeight(0.9f)) {
            items(relation) { relation ->
                CheckBoxListItem(
                    label = relation,
                    isChecked = uiState.selectedRelationshipType.contains(relation),
                    onCheckedChange = {
                        addOrRemoveRelationshipType(relation)
                    }
                )
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun RelationshipTypeScreenPreview() {
    BloomTheme {
        RelationshipTypeScreen(
            uiState = InformationUiState(),
            addOrRemoveRelationshipType = {}
        )
    }
}