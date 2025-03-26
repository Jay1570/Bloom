package com.example.bloom.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.R
import com.example.bloom.screens.TopBar
import com.example.bloom.ui.theme.BloomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerificationScreen(
    navigateToNextScreen: () -> Unit,
    viewModel: VerificationViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold{ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Icon(
                    painter = painterResource(id = R.drawable.shield_logo), // Placeholder icon
                    contentDescription = "phone Icon",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Enter the verification code",
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = uiState.code,
                    onValueChange = { viewModel.onCodeChange(it) },
                    label = { Text("Enter Verification Code") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                Text(text = "Bloom has sent you a text with a verification code. That verification code must be used here to verify the phone number.", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.weight(3f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                }

                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = { viewModel.verifyEmail(navigateTo = navigateToNextScreen) },
                    modifier = Modifier
                        .fillMaxWidth()
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
        EmailVerificationScreen( navigateToNextScreen = {})
    }
}