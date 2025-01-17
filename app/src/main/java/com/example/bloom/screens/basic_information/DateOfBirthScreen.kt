package com.example.bloom.screens.basic_information

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DateOfBirthScreen() {
    var date by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Title Text
        Text(
            text = "What's your date of birth?",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Row for Day, Month, Year
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Day Input
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text(text = "DD") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Correct keyboard type
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                singleLine = true
            )

            // Month Input
            OutlinedTextField(
                value = month,
                onValueChange = { month = it },
                label = { Text(text = "MM") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Correct keyboard type
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                singleLine = true
            )

            // Year Input
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(text = "YYYY") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number // Correct keyboard type
                ),
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button to Trigger the Dialog
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Confirm Age")
        }

        // Show Dialog when triggered
        if (showDialog) {
            AgeVerificationDialog(onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun AgeVerificationDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You're 19",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Born 13 February 2005",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Confirm your age is correct. Let's keep our community authentic.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Edit")
                    }

                    Button(onClick = { /* Confirm Logic */ onDismiss() }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateOfBirthScreen() {
    DateOfBirthScreen()
}
