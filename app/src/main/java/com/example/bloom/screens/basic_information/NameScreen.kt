package com.example.bloom.screens.basic_information

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun NameScreen(
    uiState: BasicInformationUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit
) {

    var isVisibleOnProfile by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Text(
            text = "What's your name?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        )
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = { onFirstNameChange(it) },
            label = { Text("First  name (required)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Last Name EditText
        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = { onLastNameChange(it) },
            label = { Text("Last Name ") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text // Correct keyboard type
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Last name is optional, and only shared with",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "matches.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Why?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF68275F)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
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
}

@Preview(showBackground = true)
@Composable
fun NameFragmentPreview() {
    BloomTheme {
        NameScreen(
            uiState = BasicInformationUiState(),
            onFirstNameChange = {},
            onLastNameChange = {}
        )
    }
}