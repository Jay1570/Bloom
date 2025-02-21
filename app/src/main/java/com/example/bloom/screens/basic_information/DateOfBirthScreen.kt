package com.example.bloom.screens.basic_information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DateOfBirthScreen(
    uiState: BasicInformationUiState,
    onDateChange: (String, String, String) -> Unit,
    onConfirmClick: () -> Unit,
    onDialogVisibilityChange: () -> Unit,
    onDialogConfirmClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "What's your date of birth?",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = uiState.day,
                onValueChange = {
                    if (it.length <= 2) onDateChange(
                        it,
                        uiState.month,
                        uiState.year
                    )
                },
                label = { Text(text = "DD") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.month,
                onValueChange = {
                    if (it.length <= 2) onDateChange(
                        uiState.day,
                        it,
                        uiState.year
                    )
                },
                label = { Text(text = "MM") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                singleLine = true
            )

            // Year Input
            OutlinedTextField(
                value = uiState.year,
                onValueChange = {
                    if (it.length <= 4) onDateChange(
                        uiState.day,
                        uiState.month,
                        it
                    )
                },
                label = { Text(text = "YYYY") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                keyboardController?.hide()
                onConfirmClick()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Confirm Age")
        }

        if (uiState.isDialogVisible) {
            AgeVerificationDialog(
                uiState = uiState,
                onConfirm = onDialogConfirmClick,
                onDismiss = onDialogVisibilityChange
            )
        }
    }
}

@Composable
fun AgeVerificationDialog(
    uiState: BasicInformationUiState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
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
                    text = "You're ${uiState.age} years old.",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Born ${uiState.day}/${uiState.month}/${uiState.year}",
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

                    Button(onClick = {
                        onDismiss()
                        onConfirm()
                    }
                    ) {
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
    DateOfBirthScreen(
        uiState = BasicInformationUiState(),
        onDateChange = { _, _, _ -> },
        onConfirmClick = {},
        onDialogConfirmClick = {},
        onDialogVisibilityChange = {}
    )
}