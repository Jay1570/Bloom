package com.example.bloom.screens.information

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun HeightSelector() {
    var isFtSelected by remember { mutableStateOf(true) }
    var selectedHeight by remember { mutableStateOf(165) } // Default in cm
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    val heightList = if (isFtSelected) {
        listOf(5, 6, 7).flatMap { feet ->
            listOf("$feet' 0", "$feet' 1", "$feet' 2", "$feet' 3", "$feet' 4", "$feet' 5", "$feet' 6", "$feet' 7", "$feet' 8", "$feet' 9", "$feet' 10", "$feet' 11")
        }
    } else {
        (140..210).map { "$it cm" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How tall are you?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Takes most of the space to push FT/CM to the bottom
        Column(
            modifier = Modifier.weight(1f).padding(bottom = 150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Box to Center the LazyColumn
            Box(
                modifier = Modifier
                    .height(150.dp) // Adjust height if needed
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    items(heightList) { height ->
                        Text(
                            text = height,
                            fontSize = if (height == getHeightString(selectedHeight, isFtSelected)) 22.sp else 16.sp,
                            fontWeight = if (height == getHeightString(selectedHeight, isFtSelected)) FontWeight.Bold else FontWeight.Normal,
                            color = if (height == getHeightString(selectedHeight, isFtSelected)) Color.Black else Color.Gray,
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .wrapContentHeight(Alignment.CenterVertically) // Center text vertically
                                .padding(8.dp)
                                .clickable {
                                    selectedHeight = if (isFtSelected) {
                                        convertFeetToCm(height)
                                    } else {
                                        height.replace(" cm", "").toInt()
                                    }
                                },
                            textAlign = TextAlign.Center // Center text horizontally
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Toggle between FT and CM - Now placed at the bottom
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd // Align to the right side
            ) {
                Row(
                    modifier = Modifier
                        .background(Color.LightGray, shape = RoundedCornerShape(50))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "FT",
                        fontWeight = FontWeight.Bold,
                        color = if (isFtSelected) Color.Black else Color.Gray,
                        modifier = Modifier
                            .background(if (isFtSelected) Color.White else Color.Transparent, shape = RoundedCornerShape(50))
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .clickable { isFtSelected = true }
                    )

                    Text(
                        text = "CM",
                        fontWeight = FontWeight.Bold,
                        color = if (!isFtSelected) Color.Black else Color.Gray,
                        modifier = Modifier
                            .background(if (!isFtSelected) Color.White else Color.Transparent, shape = RoundedCornerShape(50))
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .clickable { isFtSelected = false }
                    )
                }
            }
        }
    }

    // visible
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

fun getHeightString(heightCm: Int, isFt: Boolean): String {
    return if (isFt) {
        val totalInches = (heightCm * 0.393701).toInt()
        val feet = totalInches / 12
        val inches = totalInches % 12
        "$feet' $inches"
    } else {
        "$heightCm cm"
    }
}

fun convertFeetToCm(height: String): Int {
    val parts = height.split("' ")
    return if (parts.size == 2) {
        val feet = parts[0].toInt()
        val inches = parts[1].toInt()
        ((feet * 12 + inches) * 2.54).toInt()
    } else {
        165 // Default height if parsing fails
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHeightSelector() {
    BloomTheme {
        HeightSelector()
    }
}
