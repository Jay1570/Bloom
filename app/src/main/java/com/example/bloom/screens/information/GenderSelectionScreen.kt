package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun GenderSelectionScreen() {
    var selectedGender by remember { mutableStateOf("Man") }
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
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We match daters using three broad gender groups.\nYou can add more about your gender after.",
            fontSize = 14.sp,
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Gender Options
        GenderOption("Man", selectedGender) { selectedGender = it }
//        Text(
//            text = "Add your gender identity > ",
//            fontSize = 14.sp,
//            color = Color(0xFF6A1B9A), // Purple color
//            modifier = Modifier
//                .padding(start = 48.dp, top = 4.dp),
//            style = MaterialTheme.typography.titleLarge,
//        )
        Spacer(modifier = Modifier.height(8.dp))
        GenderOption("Woman", selectedGender) { selectedGender = it }
        GenderOption("Non-binary", selectedGender) { selectedGender = it }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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

@Composable
fun GenderOption(label: String, selectedGender: String, onSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected(label) }
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
        RadioButton(
            selected = selectedGender == label,
            onClick = { onSelected(label) },
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6A1B9A)) // Purple color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenderSelectionScreen() {
    GenderSelectionScreen()
}
