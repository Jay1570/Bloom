package com.example.bloom.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun MainScreen() {
    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleButton(size = 50.dp, 0.4f, iconResId = R.drawable.cross)
                CircleButton(size = 70.dp, 0.8f, iconResId = R.drawable.staricon)
                CircleButton(size = 50.dp, 0.5f, iconResId = R.drawable.heart)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserCardView(
                userName = "Charlie, 29",
                imageResId = R.drawable.horse_rider,
                activeStatus = "ACTIVE FROM 5PM TO 11PM EST",
                location = "SURAT, INDIA"
            )

            UserInformationCard(
                title = "WHO AM I?",
                attributes = listOf(
                    R.drawable.ac_1_date to "22",
                    R.drawable.ac_2_heightscale to "6'2 ft",
                    R.drawable.ac_2_sexuality to "Straight",
                    R.drawable.ac_2_pronoun_person to "Male",
                    R.drawable.ac_2_location to "Surat,India",
                    R.drawable.ac_2_work to "Android Developer",
                    R.drawable.ac_2_studylevel to "BCA",
                ),
                description = "I love exploring new places, trying out different cuisines, and spending time with animals."
            )

            UserCardView(
                userName = "",
                imageResId = R.drawable.man,
                activeStatus = "",
                location = ""
            )

            CompatibilityCard()

            UserInformationCard(
                title = "WHAT I WANT?",
                attributes = listOf(),
                description = "❤\uFE0F Long Time Relationship"
            )

            UserCardView(
                userName = "",
                imageResId = R.drawable.horse_rider2,
                activeStatus = "",
                location = ""
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
                imageResId = R.drawable.horse_rider3,
                activeStatus = "",
                location = ""
            )

            ReportAndShareButtons()
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
                            modifier = Modifier.padding(start = 16.dp)
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

@Composable
private fun CircleButton(size: Dp, iconSize: Float, iconResId: Int) {
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            tint = Color.Unspecified,
            modifier = Modifier.size(size * iconSize),
            contentDescription = null
        )
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
private fun ReportAndShareButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 100.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4F1F1),
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = "Report or Block",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4F1F1),
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = "Share Profile",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

data class User(
    val name: String,
    val imageResId: Int,
    val activeStatus: String,
    val location: String
)

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    BloomTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentDarkPreview() {
    BloomTheme(darkTheme = true) {
        MainScreen()
    }
}