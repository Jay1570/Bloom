package com.example.bloom.screens.information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun WorkplaceScreen() {
    var workplace by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Where's  your home Town ?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(60.dp))
        OutlinedTextField(
            value = workplace,
            onValueChange = { workplace = it },
            label = { Text("Home Town ", fontSize = 20.sp, fontWeight = FontWeight.Medium) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text // Correct keyboard type
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.Bottom) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isChecked = !isChecked
                }
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Visible on profile")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeTownScreenPreview() {
    BloomTheme {
        WorkplaceScreen()
    }
}