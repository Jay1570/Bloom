package com.example.bloom.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun IntroScreen(
    navigateToLogin: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.intro_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(300.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Bloom",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "END GHOSTING\nSTART CONNECTING",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.weight(2.5f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = navigateToLogin,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "Continue with Phone Number",
                            style = MaterialTheme.typography.bodyMedium,)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Data kept private, Connections kept real",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "By Signing Up, You Agree To Terms and Conditions.\n" +
                                    "\tLearn how we Use Your data according to our Privacy Policy",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 5.dp)
                        )

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    BloomTheme {
        IntroScreen(
            navigateToLogin = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenDarkPreview() {
    BloomTheme(darkTheme = true) {
        IntroScreen(
            navigateToLogin = {}
        )
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun IntroScreenLandscapeDarkPreview() {
    BloomTheme(darkTheme = true) {
        IntroScreen(
            navigateToLogin = {}
        )
    }
}
