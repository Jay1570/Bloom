package com.example.bloom.screens.advanced_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun IntermediateScreen2(navigateToNextScreen: () -> Unit) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Show off the person behind the profile with pics, videos and prompts",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { navigateToNextScreen() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB571E))
                ) {
                    Text(text = "Add more details", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InformationScreenPreview() {
    BloomTheme {
        IntermediateScreen2(
            navigateToNextScreen = {}
        )
    }
}
