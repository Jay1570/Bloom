package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun TobaccoSelectionScreen() {
    val tb = listOf("Yes", "Sometimes", "No", "Prefer not to say")

    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Do you smoke tobacco?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tb) { tb ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable {
                            selectedOption = tb
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tb,
                        fontSize = 18.sp,
                    )
                    Spacer(Modifier.weight(1f))
                    RadioButton(
                        selected = selectedOption == tb,
                        onClick = { selectedOption = tb }
                    )
                }
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 1.dp
                )
            }
        }
        Spacer(Modifier.height(16.dp))
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

@Preview(showBackground = true)
@Composable
fun TobaccoSelectionScreenPreview() {
    BloomTheme {
        TobaccoSelectionScreen()
    }
}