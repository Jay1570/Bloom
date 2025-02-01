package com.example.bloom.screens.basic_information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        // Question Text
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

        // First Name EditText
//        OutlinedTextField(
//            value = "",
//            onValueChange = {},
//            label = { Text("First name (required)") },
//            singleLine = true,
//            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
//            modifier = Modifier.fillMaxWidth()
//        )
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = { onFirstNameChange(it) },
            label = { Text("First Name(required)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
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
            label = { Text("Last Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Description Text
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
                color = Color(0xFF68275F) // Custom purple color
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer Section
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 40.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.outline_remove_red_eye_24),
//                contentDescription = "Eye Icon",
//                modifier = Modifier.padding(5.dp)
//            )
//            Spacer(modifier = Modifier.width(10.dp))
//            Text(
//                text = "Always visible on profile",
//                fontSize = 16.sp
//            )
//        }
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