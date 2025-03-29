package com.example.bloom.screens.home

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom.PORT_8000
import com.example.bloom.PORT_8080
import com.example.bloom.PORT_8100
import com.example.bloom.PORT_8200
import com.example.bloom.R
import com.example.bloom.UserPreference
import com.example.bloom.model.*
import com.example.bloom.ui.theme.BloomTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@Composable
fun MainScreen() {

    var users by remember { mutableStateOf<List<insertinfo>?>(emptyList()) }
    var user_basic_info by remember { mutableStateOf<insertinfo?>(null) }
    var user_advance_info by remember { mutableStateOf<insertinformation?>(null) }
    var user_prompt by remember { mutableStateOf<List<responsePrompt?>>(emptyList()) }
    var user_url by remember { mutableStateOf<List<responsePhoto?>>(emptyList()) }
    val userlist = mutableListOf<Pair<Pair<insertinfo?, insertinformation?>,Pair<List<responsePrompt?>,List<responsePhoto?>>>>()
    val userPreference=UserPreference(LocalContext.current)
    LaunchedEffect(Unit) {
        fetchUsersByAge(userPreference.age.value.toString()) { result ->
            users = result
            Log.d("list",users.toString())
            if (users != null) {
                for (user in users) {
                    // Fetch additional data for each user by userID
                    if(user.userID!=userPreference.user.value){
                        Log.d("UserID",user.userID)
                        user_basic_info=user
                        fetchUsers_info (user.userID){ result->
                            if(result!=null){
                                user_advance_info=result
                                fetch_prompt(user.userID){result->
                                    if(result!=null){
                                        user_prompt=result
                                        fetch_url(user.userID){result->
                                            if(result!=null){
                                                user_url=result
                                                if(user_basic_info!=null && user_advance_info!=null && user_prompt.isNotEmpty() && user_url.isNotEmpty()){
                                                    userlist.add(Pair(Pair(user_basic_info,user_advance_info),Pair(user_prompt,user_url)))

                                                }
                                                else{
                                                    Log.d("user_info","null")
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
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
                        Log.d("final information",userlist.toString())
                    }
                },
                showMatchScreen.value
            )
        }
    ){
        paddingValues ->
        val profile = userProfiles[currentIndex.value]
//        val user = userlist[currentIndex.value]
//
//// Access the inner Pair for user information
//        val userInfo = user.first // This is the first Pair containing insertinfo and insertinformation
//        val insertInfo = userInfo.first // This is the insertinfo object
//        val insertInformation = userInfo.second // This is the insertinformation object
//
//// Access the Pair of lists for prompts and photos
//        val responses = user.second // This is the second Pair containing lists of responsePrompt and responsePhoto
//        val responsePrompts = responses.first // List of responsePrompt
//        val responsePhotos = responses.second // List of responsePhoto
//
//// Now you can access specific values within each object, for example:
//
//// Accessing a value from insertinfo (assuming insertinfo is a data class with a field like 'name')
//        val insertInfoID = insertInfo?.userID
//        val insertinfofirstnm=insertInfo?.firstname
//        val insertinfolastname=insertInfo?.lastname
//        val insertinfoage=insertInfo?.age// replace 'name' with the actual field name in insertinfo
//
//// Accessing a value from insertinformation (assuming insertinformation is a data class with a field like 'datePreference')
//        val insertInformationDate = insertInformation?.datePrefrence
//        val insertinformationgender=insertInformation?.gender
//        val insertinformationsex=insertInformation?.sexuality
//        val insertinformationpronous=insertInformation?.pronouns
//        // replace 'datePrefrence' with the actual field name in insertinformation
//
//// Accessing values from responsePrompt
//        val firstPrompt = responsePrompts?.get(0) // Access the first responsePrompt object if the list is not null
//        val firstPromptAnswer = firstPrompt?.answer // Replace 'answer' with the actual field name in responsePrompt
//
//// Accessing values from responsePhoto
//        val firstPhoto = responsePhotos?.get(0) // Access the first responsePhoto object if the list is not null
//        val firstPhotoUrl = firstPhoto?.url
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

fun fetchUsersByAge(age: String, callback: (List<insertinfo>?) -> Unit) {
    val client = OkHttpClient()
    val url = "http://${PORT_8080}/getinfo/$age"

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    Thread {
        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR", "Response Code: ${response.code}")
                callback(null)
                return@Thread
            }

            val responseBody = response.body?.string()
            Log.d("API_RESPONSE", responseBody ?: "No response body")

            responseBody?.let {
                val result = Json.decodeFromString<List<insertinfo>>(it)
                callback(result)
            } ?: callback(null)
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            callback(null)
        }
    }.start()
}

fun fetch_prompt(userID:String, callback: (List<responsePrompt>?) -> Unit) {
    val client = OkHttpClient()
    val url = "http://${PORT_8100}/getinfo/$userID"

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    Thread {
        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_prompt", "Response Code: ${response.code}")
                callback(null)
                return@Thread
            }

            val responseBody = response.body?.string()
            Log.d("API_RESPONSE", responseBody ?: "No response body")

            responseBody?.let {
                val result = Json.decodeFromString<List<responsePrompt>>(it)
                callback(result)
            } ?: callback(null)
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            callback(null)
        }
    }.start()
}


fun fetch_url(userID: String, callback: (List<responsePhoto>?) -> Unit) {
    val client = OkHttpClient()
    val url = "http://${PORT_8200}/getinfo/$userID"

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    Thread {
        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_url", "Response Code: ${response.code}")
                callback(null)
                return@Thread
            }

            val responseBody = response.body?.string()
            Log.d("API_RESPONSE", responseBody ?: "No response body")

            responseBody?.let {
                val result = Json.decodeFromString<List<responsePhoto>>(it)
                callback(result)
            } ?: callback(null)
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            callback(null)
        }
    }.start()
}


fun fetchUsers_info(userID: String, callback: (insertinformation?) -> Unit) {
    val client = OkHttpClient()
    val url = "http://${PORT_8000}/getinfo/$userID"

    val request = Request.Builder()
        .url(url)
        .get()
        .build()

    Thread {
        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_info", "Response Code: ${response.code}")
                callback(null)
                return@Thread
            }

            val responseBody = response.body?.string()
            Log.d("API_RESPONSE", responseBody ?: "No response body")

            responseBody?.let {
                val result = Json.decodeFromString<insertinformation>(it)
                callback(result)
            } ?: callback(null)
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            callback(null)
        }
    }.start()
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
            val userPreference=UserPreference(LocalContext.current)
            Button(
                modifier = Modifier.size(70.dp).clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick ={
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