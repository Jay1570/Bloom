package com.example.bloom.screens.information

import android.Manifest
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bloom.R
import com.example.bloom.ui.theme.orange
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun InformationScreen(
    navigateToNextScreen: () -> Unit,
) {
    RequestLocationPermissionDialog()
    InformationContent(navigateToNextScreen = navigateToNextScreen)
}

@Composable
fun InformationContent(navigateToNextScreen: () -> Unit) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabTitles = listOf(
        R.drawable.ac_2_location,
        R.drawable.ac_2_pronoun_person,
        R.drawable.ac_2_pronoun_person,
        R.drawable.ac_2_sexuality,
        R.drawable.ac_2_likedate,
        R.drawable.datingintaion_24,
        R.drawable.ac_2_relationshiptype,
        R.drawable.ac_2_heightscale,
        R.drawable.ac_2_location,
        R.drawable.ac_2_childfriendly,
        R.drawable.ac_2_childfriendly,
        R.drawable.ac_2_hometown,
        R.drawable.ac_2_location,
        R.drawable.ac_2_work,
        R.drawable.ac_2_studylevel,
        R.drawable.ac_2_religiousbeliefs,
        R.drawable.ac_2_location,
        R.drawable.ac_2_drink,
        R.drawable.ac_2_somoke,
        R.drawable.ac_2_weed,
        R.drawable.ac_2_drugs
    )

    Scaffold(
        floatingActionButton = {
            Row {
                IconButton(
                    onClick = { if (selectedTab > 0) selectedTab-- },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                        contentDescription = "",
                        modifier = Modifier.rotate(180f)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { if (selectedTab < tabTitles.size - 1) selectedTab++ else navigateToNextScreen() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
                        contentDescription = ""
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .wrapContentSize()
                ) {
//                    val skip = if (selectedTab > 0) selectedTab - 1 else 0
                    val skip = selectedTab
                    tabTitles.drop(skip).forEachIndexed { index, icon ->
                        val actualIndex = index + skip
                        Icon(
                            painter = if (selectedTab == actualIndex) painterResource(icon) else painterResource(
                                R.drawable.inactive_dot
                            ),
                            contentDescription = "",
                            tint = if (selectedTab >= actualIndex) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .clip(CircleShape)
                                .padding(horizontal = 5.dp)
                                .size(if (selectedTab == actualIndex) 40.dp else 10.dp)
                        )
                    }

                }
                AnimatedContent(
                    targetState = selectedTab,
                    label = "animated content",
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInHorizontally { width -> width } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> -width } + fadeOut()
                        } else {
                            slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> width } + fadeOut()
                        }.using(SizeTransform(clip = false))
                    }
                ) { targetTab ->
                    when (targetTab) {
                        1 -> PronounsSelectionScreen()
                        2 -> GenderSelectionScreen()
                        3 -> SexualitySelectionScreen()
                        4 -> DatingPreferenceScreen()
                        5 -> DatingintationScreen()
                        6 -> RelationshipTypeScreen()
                        7 -> HeightSelector()
                        8 -> EthnicityScreenSelection()
                        9 -> ChildrenScreen()
                        10 -> FamilyPlanScreen()
                        11 -> WorkplaceScreen()
                        12 -> School_CollegeSelectionScreen()
                        13 -> WorkplaceSelectionScreen()
                        14 -> StudySelectionScreen()
                        15 -> ReligiousBeliefsScreen()
                        16 -> PoliticalBeliefsScreen()
                        17 -> DrinkSelectionScreen()
                        18 -> TobaccoSelectionScreen()
                        19 -> WeedSelectionScreen()
                        20 -> DrugsSelectionScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog(onRequestPermission = { permissionState.launchPermissionRequest() })
        else PermissionDialog(onRequestPermission = { permissionState.launchPermissionRequest() })
    }
}

@Composable
fun RationaleDialog(onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            title = { Text(text = "give location permission") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWarningDialog = false
                        onRequestPermission()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orange,
                        contentColor = Color.White
                    )
                ) { Text(text = "ok") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
fun PermissionDialog(onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            title = { Text(text = "give location permission") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWarningDialog = false
                        onRequestPermission()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = orange,
                        contentColor = Color.White
                    )
                ) { Text(text = "ok") }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
