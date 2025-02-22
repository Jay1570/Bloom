package com.example.bloom.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloom.R
import com.example.bloom.screens.TopBar
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.ui.theme.orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateToSettings: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "PROFILE",
                canNavigateBack = false,
                actions = {
                    IconButton(
                        onClick = navigateToSettings,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
                CircularProgressIndicator(
                    progress = {
                        0.75f
                    },
                    strokeWidth = 8.dp,
                    color = orange,
                    modifier = Modifier
                        .size(120.dp)
                        .rotate(90f)
                )
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    IconButton(
                        onClick = { isBottomSheetVisible = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = orange
                        )
                    }
                }
            }

            Text(
                text = "CATHERINE, 25",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Left: 1",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FeatureCard(icon = Icons.Default.Star, label = "03", subLabel = "SUPERLIKES")
                FeatureCard(
                    icon = Icons.AutoMirrored.Filled.Chat,
                    label = "04",
                    subLabel = "REACHOUTS"
                )
                FeatureCard(icon = Icons.Default.Bolt, label = "12", subLabel = "AI PROMPTS")
            }

            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFE0B2), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bloom Premium",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Get the complete experience of Bloom and make more meaningful connections",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = { /* Handle premium subscription */ },
                    colors = ButtonDefaults.buttonColors(containerColor = orange)
                ) {
                    Text(text = "Get it for only 100\u20B9/month", color = Color.Black)
                }
            }
        }
        if (isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isBottomSheetVisible = false },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    onEditClick = { /* TODO: Handle Edit */ },
                    onConfirmClick = { isBottomSheetVisible = false },
                )
            }
        }
    }
}

@Composable
fun FeatureCard(icon: ImageVector, label: String, subLabel: String) {
    Card(
        modifier = Modifier
            .size(width = 100.dp, height = 150.dp)
            .padding(8.dp),

        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = subLabel,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BottomSheetContent(onEditClick: () -> Unit, onConfirmClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Does this all look right?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Make sure all of your vitals match your identity and preferences.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            InfoRow(icon = Icons.Default.Person, label = "he/his/him", visibility = "Visible")
            InfoRow(icon = Icons.Default.Person, label = "Man", visibility = "Visible")
            InfoRow(icon = Icons.Default.Favorite, label = "Straight", visibility = "Visible")
            InfoRow(
                icon = Icons.Default.FavoriteBorder,
                label = "Interested in: Women",
                visibility = "Hidden"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onEditClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = orange,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Edit my vitals", color = Color.White)
        }

        TextButton(onClick = onConfirmClick) {
            Text(text = "Looks right", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, visibility: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
        Text(text = visibility, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    BloomTheme {
        ProfileScreen(navigateToSettings = {})
    }
}