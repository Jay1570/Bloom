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
fun SexualitySelectionScreen() {
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

    var selectedOption by remember { mutableStateOf<String?>(null) }
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
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(options) { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedOption = option }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = option,
                        fontSize = 18.sp,
                    )
                    Spacer(Modifier.weight(1f))
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
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
fun SexualitySelectionScreenPreview() {
    BloomTheme {
        SexualitySelectionScreen()
    }
}