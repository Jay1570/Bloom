package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatingPreferenceScreen() {
    var menChecked by remember { mutableStateOf(false) }
    var womenChecked by remember { mutableStateOf(false) }
    var nonBinaryChecked by remember { mutableStateOf(false) }
    var everyoneChecked by remember { mutableStateOf(false) }

    val preferences = listOf(
        "Men" to menChecked,
        "Women" to womenChecked,
        "Non-binary people" to nonBinaryChecked,
        "Everyone" to everyoneChecked
    )
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    val onCheckedChange: (String, Boolean) -> Unit = { label, isChecked ->
        when (label) {
            "Men" -> menChecked = isChecked
            "Women" -> womenChecked = isChecked
            "Non-binary people" -> nonBinaryChecked = isChecked
            "Everyone" -> everyoneChecked = isChecked
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Who would you like to date?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
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
            items(preferences) { (label, isChecked) ->
                PreferenceItem(label, isChecked) { onCheckedChange(label, it) }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }

        }
    }
}

@Composable
fun PreferenceItem(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCheckedChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF7B3F8F), // Adjust color as needed
                uncheckedColor = Color.LightGray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDatingPreferenceScreen() {
    DatingPreferenceScreen()
}
