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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme
import kotlin.math.roundToInt

@Composable
fun HeightSelectionScreen(
    uiState: InformationUiState,
    changeSelectedHeightInCm: (Int) -> Unit
) {
    var isFtSelected by rememberSaveable { mutableStateOf(true) }
    var isVisibleOnProfile by remember { mutableStateOf(false) }

    val heightList = if (isFtSelected) {
        (4 .. 7).flatMap { feet ->
            (0 .. 11).map { inches -> "$feet' $inches" }
        }
    } else {
        (121 .. 241).map { "$it cm" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "How tall are you?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Takes most of the space to push FT/CM to the bottom
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .padding(bottom = 150.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            // Box to Center the LazyColumn
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    items(heightList) { height ->
                        val isSelected =
                            height == getHeightString(uiState.selectedHeightInCm, isFtSelected)
                        Text(
                            text = height,
                            fontSize = if (isSelected) 22.sp else 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Gray,
                            modifier = Modifier
                                .fillMaxSize(1f)
                                .wrapContentHeight(Alignment.CenterVertically)
                                .padding(8.dp)
                                .clickable {
                                    val heightInCm =
                                        if (isFtSelected) convertFeetToCm(height) else height.replace(
                                            " cm",
                                            ""
                                        ).toInt()
                                    changeSelectedHeightInCm(heightInCm)
                                },
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Toggle between FT and CM - Now placed at the bottom
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
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
                            .background(
                                if (isFtSelected) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .clickable { isFtSelected = true }
                    )

                    Text(
                        text = "CM",
                        fontWeight = FontWeight.Bold,
                        color = if (!isFtSelected) Color.Black else Color.Gray,
                        modifier = Modifier
                            .background(
                                if (!isFtSelected) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .clickable { isFtSelected = false }
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

fun getHeightString(heightCm: Int, isFt: Boolean): String {
    return if (isFt) {
        val totalInches = (heightCm * 0.393701).roundToInt()
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
        HeightSelectionScreen(
            uiState = InformationUiState(),
            changeSelectedHeightInCm = {}
        )
    }
}
