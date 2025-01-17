package com.example.bloom.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.screens.TopBar
import com.example.bloom.ui.theme.BloomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerificationScreen(
    navigateBack: () -> Unit,
    navigateToBasicInformation: () -> Unit,
    viewModel: VerificationViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopBar(
                title = "Email Verification",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "We’ve sent a verification code to your email.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .padding(bottom = 60.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = uiState.code,
                    onValueChange = { viewModel.onCodeChange(it) },
                    label = { Text("Enter Verification Code") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number // Correct keyboard type
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Don't Receive a code?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Resend Code",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Button(
                    onClick = { viewModel.verifyEmail(navigateTo = navigateToBasicInformation) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Verify", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmailVerificationPreview() {
    BloomTheme {
        EmailVerificationScreen(navigateBack = {}, navigateToBasicInformation = {})
    }
}