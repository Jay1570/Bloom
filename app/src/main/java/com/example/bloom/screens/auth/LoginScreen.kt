package com.example.bloom.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.AppViewModelProvider
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.ui.theme.outlineLight

@Composable
fun LoginScreen(
    navigateToVerification: () -> Unit,
    viewModel: LoginViewModel =  viewModel(factory = AppViewModelProvider .factory)
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        LoginScreenContent(
            uiState = uiState,
            onUsernameChange = {viewModel.onUsernameChange(it)},
            onLoginClick = { viewModel.onLoginClick(navigateToVerification) },
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .imePadding()
        )
    }
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onLoginClick: () -> Unit,
    onUsernameChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val enabled = !uiState.inProcess
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            Icon(
                painter = painterResource(id = R.drawable.phone_logo), // Placeholder icon
                contentDescription = "phone Icon",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "What's your phone number?",
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier
                .fillMaxWidth()) {
                Text(text = "🇮🇳 +91", fontSize = 22.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top =15.dp))

                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = { onUsernameChange(it) },
                    placeholder = { Text("Enter your phone number") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    ),
                    enabled = enabled
                )
            }
            Spacer(modifier= Modifier.height(15.dp))
            Text(text = "Bloom will send you a text with a verification code. Message and data rates may apply.", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier= Modifier.weight(2.2f))
            Button(onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Sned OTP",
                    style = MaterialTheme.typography.bodyMedium)
            }


        }
        }
        if (uiState.inProcess) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    BloomTheme {
        LoginScreenContent(
            uiState = LoginUiState(
                username = "123456789"
            ),
            onLoginClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginDarkPreview() {
    BloomTheme(darkTheme = true) {
        LoginScreen(
            navigateToVerification = {}
        )
    }
}
