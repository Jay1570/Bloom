package com.example.bloom.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bloom.AppViewModelProvider
import com.example.bloom.R
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun MainScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val showMatchScreen = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ActionButtons(
                onNext = { viewModel.onNextClicked() },
                onLike = {
                    viewModel.onlikeClicked()
                    uiState.currentUserProfile?.let {
                        showMatchScreen.value = true
                    }
                },
                showMatchScreen.value
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            showMatchScreen.value && uiState.currentUserProfile != null -> {
                MatchScreen(
                    onDismiss = {
                        showMatchScreen.value = false
                        viewModel.selectRandomUser()
                    },
                    userID = uiState.currentUserProfile!!.userID,
                    onMatchConfirmed = {
                        viewModel.onMatchConfirmed(it)
                    }
                )
            }

            uiState.currentUserProfile != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val profile = uiState.currentUserProfile

                    UserCardView(
                        userName = profile?.name,
                        imageResId = profile!!.imageResId[0],
                        activeStatus = profile.activeStatus,
                        location = profile.location
                    )

                    UserInformationCard(
                        title = "WHO AM I?",
                        attributes = profile.details,
                        description = profile.aboutMe
                    )
                    if (profile.imageResId.size > 1) {
                        UserCardView(
                            userName = "",
                            imageResId = profile.imageResId[1],
                            activeStatus = "",
                            location = ""
                        )
                    }

                    CompatibilityCard()

                    UserInformationCard(
                        title = "WHAT I WANT?",
                        attributes = listOf(),
                        description = "❤ Long Time Relationship"
                    )

                    UserInformationCard(
                        title = "LIFESTYLE",
                        attributes = listOf(
                            R.drawable.ac_2_drink to "Sometimes",
                            R.drawable.ac_2_weed to "Nope",
                            R.drawable.ac_2_somoke to "Sometimes",
                            R.drawable.gym_svgrepo_com to "Exercise",
                            R.drawable.language_svgrepo_com to "English, Hindi, Gujarati",
                        ),
                        description = ""
                    )

                    UserInformationCard(
                        title = "MY INTEREST",
                        attributes = listOf(
                            R.drawable.hiking_svgrepo_com to "Hiking",
                            R.drawable.travel_svgrepo_com to "Travelling",
                            R.drawable.swimming_svgrepo_com to "Swimming",
                            R.drawable.design_education_painting_svgrepo_com to "Fine Art",
                            R.drawable.stand_up_horse_with_jockey_svgrepo_com to "Horse Riding",
                            R.drawable.gamepad_svgrepo_com to "Gamer",
                        ),
                        description = ""
                    )
                    if(profile.imageResId.size > 2) {
                        UserCardView(
                            userName = "",
                            imageResId = profile.imageResId[2],
                            activeStatus = "",
                            location = ""
                        )
                    }
                    Spacer(modifier = Modifier.height(120.dp))

                }
            }

            uiState.noMoreProfiles -> {
                EmptyStateScreen()
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun UserCardView(
    userName: String?,
    imageResId: String,
    activeStatus: String?,
    location: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                GlideImage(
                    model = imageResId,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    if (!userName.isNullOrEmpty()) {
                        InfoCard(
                            text = userName,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 15.dp, top = 15.dp).wrapContentSize()
                        )
                    }

                    if (!activeStatus.isNullOrEmpty()) {
                        InfoCard(text = activeStatus)
                    }

                    if (!location.isNullOrEmpty()) {
                        InfoCard(text = location)
                    }
                }
            }
        }
    }
}


@Composable
private fun InfoCard(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    modifier: Modifier = Modifier.padding(start = 16.dp, top = 5.dp).wrapContentSize()
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = style.copy(color = MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UserInformationCard(
    title: String,
    attributes: List<Pair<Int, String>>,
    description: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp, start = 3.dp)
            )

            if (attributes.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    attributes.forEach { attribute ->
                        AttributeItem(iconRes = attribute.first, label = attribute.second)
                    }
                }
            }

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun AttributeItem(iconRes: Int, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(18.dp)
                .padding(end = 6.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground),
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun CompatibilityCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy((-20).dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.horse_rider),
                        contentDescription = "User Image 1",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.man),
                        contentDescription = "User Image 2",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(3.dp, Color(0xFF4CAF50), CircleShape),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "83%",
                        style = TextStyle(
                            color = Color(0xFF4CAF50),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Ethan and you are the perfect couple",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Based on your profiles, you are 83% compatible",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ActionButtons(onNext: () -> Unit, onLike: () -> Unit, showMatchScreen: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showMatchScreen == false) {
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = onNext
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = "Next",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(25.dp))
            Button(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = {
                    onLike()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Like",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

data class UserProfile(
    val userID: String,
    val name: String,
    val imageResId: List<String>,
    val activeStatus: String,
    val location: String,
    val details: List<Pair<Int, String>>,
    val aboutMe: String
)

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    BloomTheme {
        MainScreen()
    }
}

@Composable
fun EmptyStateScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No more profiles available.",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray
        )
    }
}


@Composable
fun MatchScreen(onDismiss: () -> Unit, userID: String, onMatchConfirmed: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "It's a Match!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onMatchConfirmed(userID)
                onDismiss()
            }) {
                Text("Continue")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeContentDarkPreview() {
    BloomTheme(darkTheme = true) {
        MainScreen()
    }
}