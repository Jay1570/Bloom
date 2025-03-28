package com.example.bloom.screens.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.bloom.UserPreference
import com.example.bloom.model.Connections
import com.example.bloom.ui.theme.BloomTheme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MainScreen() {

    val showMatchScreen = remember { mutableStateOf(false) }
    val userProfiles = remember {
        mutableStateListOf( UserProfile(
            userID = "1234567890",
            name = "Charlie, 29",
            imageResId = listOf(R.drawable.horse_rider,R.drawable.horse_rider2,R.drawable.horse_rider3),
            activeStatus = "ACTIVE FROM 5PM TO 11PM EST",
            location = "SURAT, INDIA",
            details = listOf(
                R.drawable.ac_1_date to "22",
                R.drawable.ac_2_heightscale to "6'2 ft",
                R.drawable.ac_2_sexuality to "Straight",
                R.drawable.ac_2_pronoun_person to "Male",
                R.drawable.ac_2_location to "Surat, India",
                R.drawable.ac_2_work to "Android Developer",
                R.drawable.ac_2_studylevel to "BCA"
            ),
            aboutMe = "I love exploring new places, trying out different cuisines, and spending time with animals."
        ),
            UserProfile(
                userID = "9408335005",
                name = "Ethan, 25",
                imageResId = listOf(R.drawable.man,R.drawable.horse_rider2,R.drawable.horse_rider3),
                activeStatus = "ACTIVE FROM 3PM TO 10PM IST",
                location = "MUMBAI, INDIA",
                details = listOf(
                    R.drawable.ac_1_date to "25",
                    R.drawable.ac_2_heightscale to "5'11 ft",
                    R.drawable.ac_2_sexuality to "Straight",
                    R.drawable.ac_2_pronoun_person to "Male",
                    R.drawable.ac_2_location to "Mumbai, India",
                    R.drawable.ac_2_work to "Software Engineer",
                    R.drawable.ac_2_studylevel to "MCA"
                ),
                aboutMe = "Tech enthusiast who loves coding, traveling, and gaming."
            ),

            UserProfile(
                userID = "9876543210",
                name = "Charlie, 29",
                imageResId = listOf(R.drawable.horse_rider,R.drawable.horse_rider2,R.drawable.horse_rider3),
                activeStatus = "ACTIVE FROM 5PM TO 11PM EST",
                location = "SURAT, INDIA",
                details = listOf(
                    R.drawable.ac_1_date to "22",
                    R.drawable.ac_2_heightscale to "6'2 ft",
                    R.drawable.ac_2_sexuality to "Straight",
                    R.drawable.ac_2_pronoun_person to "Male",
                    R.drawable.ac_2_location to "Surat, India",
                    R.drawable.ac_2_work to "Android Developer",
                    R.drawable.ac_2_studylevel to "BCA"
                ),
                aboutMe = "I love exploring new places, trying out different cuisines, and spending time with animals."
            ),
            UserProfile(
                userID = "9537920140",
                name = "Doe, 29",
                imageResId = listOf(R.drawable.horse_rider,R.drawable.horse_rider2,R.drawable.horse_rider3),
                activeStatus = "ACTIVE FROM 5PM TO 11PM EST",
                location = "PUNE, INDIA",
                details = listOf(
                    R.drawable.ac_1_date to "22",
                    R.drawable.ac_2_heightscale to "4'11 ft",
                    R.drawable.ac_2_sexuality to "Straight",
                    R.drawable.ac_2_pronoun_person to "Male",
                    R.drawable.ac_2_location to "Surat, India",
                    R.drawable.ac_2_work to "Android Developer",
                    R.drawable.ac_2_studylevel to "BCA"
                ),
                aboutMe = "I love exploring new places, trying out different cuisines, and spending time with animals."
            ),
            UserProfile(
                userID = "9824384947",
                name = "John, 22",
                imageResId = listOf(R.drawable.horse_rider,R.drawable.horse_rider2,R.drawable.horse_rider3),
                activeStatus = "ACTIVE FROM 5PM TO 11PM EST",
                location = "NAVSARI, INDIA",
                details = listOf(
                    R.drawable.ac_1_date to "02",
                    R.drawable.ac_2_heightscale to "6'0 ft",
                    R.drawable.ac_2_sexuality to "Straight",
                    R.drawable.ac_2_pronoun_person to "Male",
                    R.drawable.ac_2_location to "Surat, India",
                    R.drawable.ac_2_work to "Android Developer",
                    R.drawable.ac_2_studylevel to "BCA"
                ),
                aboutMe = "I love exploring new places, trying out different cuisines, and spending time with animals."
            )
        )
    }
    val currentIndex = remember { mutableStateOf(0) }
    Scaffold(
        floatingActionButton = {
            ActionButtons(
                onNext = {
                    currentIndex.value = (currentIndex.value + 1) % userProfiles.size
                },
                onLike = {
                    if (userProfiles.isNotEmpty()) {
                        userProfiles.removeAt(currentIndex.value)
                        showMatchScreen.value = true
                    }
                },
                showMatchScreen.value
            )
        }
    ){
        paddingValues ->
        val profile = userProfiles[currentIndex.value]
        if (showMatchScreen.value) {
            MatchScreen(onDismiss = { showMatchScreen.value = false }, userID = profile.userID)
        }else if (userProfiles.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserCardView(
                    userName = profile.name,
                    imageResId = profile.imageResId.get(0),
                    activeStatus = profile.activeStatus,
                    location = profile.location
                )

                UserInformationCard(
                    title = "WHO AM I?",
                    attributes = profile.details,
                    description = profile.aboutMe
                )
                UserCardView(
                    userName = "",
                    imageResId = profile.imageResId.get(1),
                    activeStatus = "",
                    location = ""
                )

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

                UserCardView(
                    userName = "",
                    imageResId = profile.imageResId.get(2),
                    activeStatus = "",
                    location = ""
                )
            }
        }else {
            EmptyStateScreen()
        }
    }
}


@Composable
private fun UserCardView(
    userName: String?,
    imageResId: Int,
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
                Image(
                    painter = painterResource(id = imageResId),
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
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
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
private fun InfoCard(text: String) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 5.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                alpha = 0.3f
            )
        )
    ) {
        Box(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.background)
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
fun ActionButtons(onNext: () -> Unit, onLike: () -> Unit,showMatchScreen: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showMatchScreen == false){
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                modifier = Modifier.size(70.dp).clip(CircleShape),
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
                modifier = Modifier.size(70.dp).clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = onLike
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
    val imageResId: List<Int>,
    val activeStatus: String,
    val location: String,
    val details: List<Pair<Int, String>>, // List of icons and corresponding text
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
fun MatchScreen(onDismiss: () -> Unit,userID: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        val userPreference=UserPreference(LocalContext.current)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("It's a Match!", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick= {
                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("connections").add(
                    Connections(
                user1Id = userPreference.user.value,//current userid
                user2Id = userID//userid of connection
                )
                ).addOnSuccessListener {onDismiss()} //else use this two functions
                    .addOnFailureListener{e ->
                    Log.d("firebase_error",e.message.toString())}
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