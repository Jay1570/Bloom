package com.example.bloom.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun LikedYouScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Liked You Screen")
    }
}

@Composable
fun ProfileCard() {
    Box(
        modifier = Modifier
            .size(height = 175.dp, width = 125.dp)
            .clip(RoundedCornerShape(10)),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(R.drawable.user),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            contentDescription = ""
        )
        Row {
            Text("Name, Age", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.weight(1f))
            Image(imageVector = Icons.Default.Games, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    BloomTheme {
        ProfileCard()
    }
}
