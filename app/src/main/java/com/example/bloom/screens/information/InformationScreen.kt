package com.example.bloom.screens.information

import android.Manifest
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloom.R
import com.example.bloom.screens.RequestPermissionDialog

@Composable
fun InformationScreen(
    navigateToNextScreen: () -> Unit,
    viewModel: InformationViewModel = viewModel()
) {
    RequestPermissionDialog(
        title = "give location permission",
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    InformationContent(
        navigateToNextScreen = navigateToNextScreen,
        viewModel = viewModel
    )
}

@Composable
fun InformationContent(
    navigateToNextScreen: () -> Unit,
    viewModel: InformationViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tabTitles = listOf(
        R.drawable.ac_2_location,
        R.drawable.ac_2_pronoun_person,
        R.drawable.ac_2_pronoun_person,
        R.drawable.ac_2_sexuality,
        R.drawable.ac_2_likedate,
        R.drawable.datingintaion_24,
        R.drawable.ac_2_relationshiptype,
        R.drawable.ac_2_heightscale,
        R.drawable.ac_2_ethnicity,
        R.drawable.ac_2_childfriendly,
        R.drawable.ac_2_childfriendly,
        R.drawable.ac_2_hometown,
        R.drawable.ac_2_school,
        R.drawable.ac_2_work,
        R.drawable.ac_2_studylevel,
        R.drawable.ac_2_religiousbeliefs,
        R.drawable.ac_2_parliament,
        R.drawable.ac_2_drink,
        R.drawable.ac_2_somoke,
        R.drawable.ac_2_weed,
        R.drawable.ac_2_drugs
    )
    Scaffold(
        floatingActionButton = {
            Row {
                if (uiState.currentTab > 0) {
                    IconButton(
                        onClick = { viewModel.goToPrevious() },
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
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { viewModel.goToNext(navigateToNextScreen) },
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .wrapContentSize()
                ) {
                    val skip = uiState.currentTab
                    tabTitles.drop(skip).forEachIndexed { index, icon ->
                        val actualIndex = index + skip
                        Icon(
                            painter = if (uiState.currentTab == actualIndex) painterResource(icon) else painterResource(
                                R.drawable.inactive_dot
                            ),
                            contentDescription = "",
                            tint = if (uiState.currentTab >= actualIndex) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .clip(CircleShape)
                                .padding(horizontal = 5.dp)
                                .size(if (uiState.currentTab == actualIndex) 40.dp else 10.dp)
                        )
                    }
                }
                AnimatedContent(
                    targetState = uiState.currentTab,
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
                        /*0 -> CurrentLocationScreen(
                            uiState = uiState,
                            onLocationChange = viewModel::onLocationChange
                        )*/
                        1 -> PronounsSelectionScreen(
                            uiState = uiState,
                            addOrRemovePronoun = viewModel::addOrRemovePronoun
                        )

                        2 -> GenderSelectionScreen(
                            uiState = uiState,
                            changeSelectedGender = viewModel::changeSelectedGender
                        )

                        3 -> SexualitySelectionScreen(
                            uiState = uiState,
                            changeSelectedSexuality = viewModel::changeSelectedSexuality
                        )

                        4 -> DatingPreferenceScreen(
                            uiState = uiState,
                            addOrRemoveDatingPreference = viewModel::addOrRemoveDatingPreference
                        )

                        5 -> DatingIntentionScreen(
                            uiState = uiState,
                            changeIntention = viewModel::changeDatingIntention
                        )

                        6 -> RelationshipTypeScreen(
                            uiState = uiState,
                            addOrRemoveRelationshipType = viewModel::addOrRemoveRelationshipType
                        )

                        7 -> HeightSelectionScreen(
                            uiState = uiState,
                            changeSelectedHeightInCm = viewModel::changeSelectedHeightInCm
                        )

                        8 -> EthnicitySelectionScreen(
                            uiState = uiState,
                            addOrRemoveEthnicity = viewModel::addOrRemoveEthnicity
                        )

                        9 -> ChildrenScreen(
                            uiState = uiState,
                            changeValue = viewModel::changeDoYouHaveChildren
                        )

                        10 -> FamilyPlanScreen(
                            uiState = uiState,
                            changeFamilyPlan = viewModel::changeFamilyPlan
                        )

                        11 -> HomeTownScreen(
                            uiState = uiState,
                            onHomeTownChange = viewModel::onHomeTownChange
                        )

                        12 -> SchoolCollegeSelectionScreen(
                            uiState = uiState,
                            onSchoolOrCollegeChange = viewModel::onSchoolOrCollegeChange
                        )

                        13 -> WorkplaceSelectionScreen(
                            uiState = uiState,
                            onWorkPlaceChange = viewModel::onWorkPlaceChange
                        )

                        14 -> StudySelectionScreen(
                            uiState = uiState,
                            changeSelectedEducation = viewModel::changeSelectedEducation
                        )

                        15 -> ReligiousBeliefsScreen(
                            uiState = uiState,
                            changeSelectedReligiousBelief = viewModel::changeSelectedReligiousBelief
                        )

                        16 -> PoliticalBeliefsScreen(
                            uiState = uiState,
                            changeSelectedPoliticalBelief = viewModel::changeSelectedPoliticalBelief
                        )

                        17 -> DrinkSelectionScreen(
                            uiState = uiState,
                            changeSelectedDrinkOption = viewModel::changeSelectedDrinkOption
                        )

                        18 -> TobaccoSelectionScreen(
                            uiState = uiState,
                            changeSelectedTobaccoOption = viewModel::changeSelectedTobaccoOption
                        )

                        19 -> WeedSelectionScreen(
                            uiState = uiState,
                            changeSelectedWeedOption = viewModel::changeSelectedWeedOption
                        )

                        20 -> DrugsSelectionScreen(
                            uiState = uiState,
                            changeSelectedDrugOption = viewModel::changeSelectedDrugOption
                        )
                    }
                }
            }
        }
    }
}