package com.example.bloom.screens.registration_completion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun SuggestSubscriptionScreen(
    navigateToPayment: () -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(24.dp))

                Icon(
                    painter = painterResource(R.drawable.heart_check_24px),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = "Subscribers go on 3x as many dates",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(30.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    FeatureItem(
                        Icons.Default.Star,
                        "Enhanced recommendations",
                        "Access to your type"
                    )
                    FeatureItem(
                        Icons.AutoMirrored.Default.ArrowForward,
                        "Skip the line",
                        "Get recommended to matches sooner"
                    )
                    FeatureItem(
                        Icons.Default.Favorite,
                        "Priority likes",
                        "Your likes stay at the top of their list"
                    )
                    FeatureItem(Icons.Default.AllInclusive, "Send unlimited likes & more ...", "")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = navigateToPayment,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        "Check it out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Maybe later",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(onClick = navigateToHome)
                        .fillMaxWidth()
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun FeatureItem(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle.isNotEmpty()) {
                Text(subtitle, fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestSubscriptionPreview() {
    BloomTheme {
        SuggestSubscriptionScreen(
            navigateToPayment = {},
            navigateToHome = {}
        )
    }
}