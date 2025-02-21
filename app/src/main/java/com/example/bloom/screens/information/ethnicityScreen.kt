package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun EthnicityScreenSelection() {

    var blackafricanChecked by remember { mutableStateOf(false) }
    var EastAsianChecked by remember { mutableStateOf(false) }
    var lationChecked by remember { mutableStateOf(false) }
    var middleEastChecked by remember { mutableStateOf(false) }
    var NativeAmericanChecked by remember { mutableStateOf(false) }
    var PacificChecked by remember { mutableStateOf(false) }
    var SouthAsianChecked by remember { mutableStateOf(false) }
    var SouthEastAsianChecked by remember { mutableStateOf(false) }
    var WhiteChecked by remember { mutableStateOf(false) }
    var otherChecked by remember { mutableStateOf(false) }
    var opentoallChecked by remember { mutableStateOf(false) }

    val region = listOf(
        "Black/African Descent" to blackafricanChecked,
        "East Asian" to EastAsianChecked,
        "Hispanic/Lation" to lationChecked,
        "Middle Eastern" to middleEastChecked,
        "Native American" to NativeAmericanChecked,
        "Pacific Islander" to PacificChecked,
        "South Asian" to SouthAsianChecked,
        "Southeast Asian" to SouthEastAsianChecked,
        "White/Caucasian" to WhiteChecked,
        "Other" to otherChecked,
        "Open to all" to opentoallChecked
    )

    val onCheckedChange: (String, Boolean) -> Unit = { label, isChecked ->
        when (label) {
            "Black/African Descent" -> blackafricanChecked = isChecked
            "East Asian" -> EastAsianChecked = isChecked
            "Hispanic/Lation" -> lationChecked = isChecked
            "Middle Eastern" -> middleEastChecked = isChecked
            "Native American" -> NativeAmericanChecked = isChecked
            "Pacific Islander" -> PacificChecked = isChecked
            "South Asian" -> SouthAsianChecked = isChecked
            "Southeast Asian" -> SouthEastAsianChecked = isChecked
            "White/Caucasian" -> WhiteChecked = isChecked
            "Other" -> otherChecked = isChecked
            "Open to all" -> opentoallChecked = isChecked
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, bottom = 80.dp, top = 20.dp)
    ) {
        Text(
            text = "What's Your ethnicity?",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(region) { (label, isChecked) ->
                Regionitem(label, isChecked) { onCheckedChange(label, it) }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun Regionitem(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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
fun RegionScreenPreview() {
    BloomTheme {
        EthnicityScreenSelection()
    }
}
