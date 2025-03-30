package com.example.bloom.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bloom.AppViewModelProvider
import com.example.bloom.screens.TopBar
import com.example.bloom.screens.information.*
import com.example.bloom.ui.theme.BloomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateToSettings: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider .factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val visibilityState by viewModel.visibilityState.collectAsStateWithLifecycle()

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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (visibilityState.selectedPronouns) {
                PronounsSelectionScreen(
                    uiState = uiState.informationUiState,
                    addOrRemovePronoun = { viewModel.addOrRemovePronoun(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedPronouns()
                }
            } else if (visibilityState.selectedGender) {
                GenderSelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedGender = { viewModel.changeSelectedGender(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedGender()
                }
            } else if (visibilityState.selectedSexuality) {
                SexualitySelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedSexuality = { viewModel.changeSelectedSexuality(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedSexuality()
                }
            } else if (visibilityState.selectedDatingPreferences) {
                DatingPreferenceScreen (
                    uiState = uiState.informationUiState,
                    addOrRemoveDatingPreference = { viewModel.addOrRemoveDatingPreference(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedDatingPreferences()
                }
            } else if (visibilityState.selectedHeightInCm) {
                HeightSelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedHeightInCm = { viewModel.changeSelectedHeightInCm(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedHeightInCm()
                }
            } else if (visibilityState.selectedEthnicity) {
                EthnicitySelectionScreen(
                    uiState = uiState.informationUiState,
                    addOrRemoveEthnicity = { viewModel.addOrRemoveEthnicity(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedEthnicity()
                }
            } else if (visibilityState.doYouHaveChildren) {
                ChildrenScreen (
                    uiState = uiState.informationUiState,
                    changeValue = { viewModel.changeDoYouHaveChildren(it) }
                )
                BackHandler {
                    viewModel.toggleDoYouHaveChildren()
                }
            } else if (visibilityState.selectedFamilyPlan) {
                FamilyPlanScreen (
                    uiState = uiState.informationUiState,
                    changeFamilyPlan = { viewModel.changeFamilyPlan(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedFamilyPlan()
                }
            } else if (visibilityState.locality) {
                CurrentLocationScreen(
                    uiState = uiState.informationUiState,
                    onLocationChange = { viewModel.onLocationChnage(it) }
                )
                BackHandler {
                    viewModel.toggleLocality()
                }
            } else if (visibilityState.workPlace) {
                WorkplaceSelectionScreen(
                    uiState = uiState.informationUiState,
                    onWorkPlaceChange = { viewModel.onWorkPlaceChange(it) }
                )
                BackHandler {
                    viewModel.toggleWorkPlace()
                }
            } else if (visibilityState.schoolOrCollege) {
                SchoolCollegeSelectionScreen(
                    uiState = uiState.informationUiState,
                    onSchoolOrCollegeChange = { viewModel.onSchoolOrCollegeChange(it) }
                )
                BackHandler {
                    viewModel.toggleSchoolOrCollege()
                }
            } else if (visibilityState.selectedEducation) {
                StudySelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedEducation = { viewModel.changeSelectedEducation(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedEducation()
                }
            } else if (visibilityState.selectedReligiousBelief) {
                ReligiousBeliefsScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedReligiousBelief = { viewModel.changeSelectedReligiousBelief(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedReligiousBelief()
                }
            } else if (visibilityState.homeTown) {
                HomeTownScreen(
                    uiState = uiState.informationUiState,
                    onHomeTownChange = { viewModel.onHomeTownChange(it) }
                )
                BackHandler {
                    viewModel.toggleHomeTown()
                }
            } else if (visibilityState.selectedPoliticalBelief) {
                PoliticalBeliefsScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedPoliticalBelief = { viewModel.changeSelectedPoliticalBelief(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedPoliticalBelief()
                }
            } else if (visibilityState.selectedDatingIntention) {
                DatingIntentionScreen(
                    uiState = uiState.informationUiState,
                    changeIntention = { viewModel.changeDatingIntention(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedDatingIntention()
                }
            } else if (visibilityState.selectedRelationshipType) {
                RelationshipTypeScreen(
                    uiState = uiState.informationUiState,
                    addOrRemoveRelationshipType = { viewModel.addOrRemoveRelationshipType(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedRelationshipType()
                }
            } else if (visibilityState.selectedDrinkOption) {
                DrinkSelectionScreen (
                    uiState = uiState.informationUiState,
                    changeSelectedDrinkOption = { viewModel.changeSelectedDrinkOption(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedDrinkOption()
                }
            } else if (visibilityState.selectedTobaccoOption) {
                TobaccoSelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedTobaccoOption = { viewModel.changeSelectedTobaccoOption(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedTobaccoOption()
                }
            } else if (visibilityState.selectedDrugOption) {
                DrugsSelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedDrugOption = { viewModel.changeSelectedDrugOption(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedDrugOption()
                }
            } else if (visibilityState.selectedWeedOption) {
                WeedSelectionScreen(
                    uiState = uiState.informationUiState,
                    changeSelectedWeedOption = { viewModel.changeSelectedWeedOption(it) }
                )
                BackHandler {
                    viewModel.toggleSelectedWeedOption()
                }
            }
            else {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

                    ImageSelection(
                        images = uiState.images,
                        onRemove = {},
                        onAdd = {}
                    )
                    HeadingText("Written Prompts")
                    TextPrompts(
                        selectedPrompts = uiState.selectedTextPrompts,
                    )
                    IdentitySection(
                        uiState = uiState.informationUiState,
                        onDatingPreferencesClick = { viewModel.toggleSelectedDatingPreferences() },
                        onGenderClick = { viewModel.toggleSelectedGender() },
                        onSexualityClick = { viewModel.toggleSelectedSexuality() },
                        onPronounsClick = { viewModel.toggleSelectedPronouns() }
                    )
                    MyVitalsSection(
                        name = uiState.name,
                        age = uiState.age.toString(),
                        uiState = uiState.informationUiState,
                        onHeightClick = { viewModel.toggleSelectedHeightInCm() },
                        onEthnicityClick = { viewModel.toggleSelectedEthnicity() },
                        onChildrenClick = { viewModel.toggleDoYouHaveChildren() },
                        onFamilyPlansClick = { viewModel.toggleSelectedFamilyPlan() },
                        onLocationClick = { viewModel.toggleLocality() }
                    )
                    MyVirtuesSection(
                        uiState = uiState.informationUiState,
                        onWorkClick = { viewModel.toggleWorkPlace() },
                        onSchoolOrCollegeClick = { viewModel.toggleSchoolOrCollege() },
                        onEducationLevelClick = { viewModel.toggleSelectedEducation() },
                        onReligiousBeliefsClick = { viewModel.toggleSelectedReligiousBelief() },
                        onHomeTownClick = { viewModel.toggleHomeTown() },
                        onPoliticsClick = { viewModel.toggleSelectedPoliticalBelief() },
                        onDatingIntentionsClick = { viewModel.toggleSelectedDatingIntention() },
                        onRelationshipTypeClick = { viewModel.toggleSelectedRelationshipType() }
                    )
                    MyVicesSection(
                        uiState = uiState.informationUiState,
                        onDrinkingClick = { viewModel.toggleSelectedDrinkOption() },
                        onTobaccoClick = { viewModel.toggleSelectedTobaccoOption() },
                        onDrugClick = { viewModel.toggleSelectedDrugOption() },
                        onWeedClick = { viewModel.toggleSelectedWeedOption() },
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            viewModel.onEditClick()
                        }
                    ) {
                        Text(text = "Edit")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageSelection(
    images: List<String>,
    onRemove: (Int) -> Unit,
    onAdd: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HeadingText("Photos and videos")

        repeat(2) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { columnIndex ->
                    val index = rowIndex * 3 + columnIndex
                    if (index < images.size && images[index].isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .weight(1f)
                                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    if (images[index].isEmpty()) {
                                        onRemove(index)
                                    } else {
                                        onAdd(index)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            GlideImage(
                                model = images[index],
                                contentDescription = "Selected Image",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        onRemove(index)
                                    }
                            )
                        }
                    }
                }
            }
        }

        SubHeadingText("Tap to edit.")
    }
}

@Composable
fun TextPrompts(
    selectedPrompts: List<Pair<String, String>?>,
) {
    selectedPrompts.forEachIndexed { index, selectedPrompt ->
        if(selectedPrompt != null && selectedPrompt.first.isNotEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 70.dp)
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            text = selectedPrompt.first,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = selectedPrompt.second,
                            style = MaterialTheme.typography.bodyMedium
                        )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun IdentitySection(
    uiState: InformationUiState,
    onPronounsClick: () -> Unit,
    onGenderClick: () -> Unit,
    onSexualityClick: () -> Unit,
    onDatingPreferencesClick: () -> Unit,
) {
    HeadingText("Identity")
    var selectedPronouns = ""
    var selectedDatingPreferences = ""
    if (uiState.selectedPronouns.isNotEmpty()) {
        selectedPronouns = uiState.selectedPronouns.joinToString("/")
    }
    if (uiState.selectedDatingPreferences.isNotEmpty()) {
        selectedDatingPreferences = uiState.selectedDatingPreferences.joinToString(", ")
    }
    val aboutDetails = listOf(
        UserDetails("Pronouns", selectedPronouns, onPronounsClick),
        UserDetails("Gender", uiState.selectedGender, onGenderClick),
        UserDetails("Sexuality", uiState.selectedSexuality, onSexualityClick),
        UserDetails("I'm interested in", selectedDatingPreferences, onDatingPreferencesClick)
    )
    aboutDetails.forEach { detail ->
        RowInterface(detail.title, detail.value , detail.onClick)
    }
}

@Composable
fun MyVitalsSection(
    name: String,
    age: String,
    uiState: InformationUiState,
    onHeightClick: () -> Unit,
    onEthnicityClick: () -> Unit,
    onChildrenClick: () -> Unit,
    onFamilyPlansClick: () -> Unit,
    onLocationClick: () -> Unit
) {
    HeadingText("My Vitals")
    var selectedEthnicities = ""
    if (uiState.selectedEthnicity.isNotEmpty()) {
        selectedEthnicities = uiState.selectedEthnicity.joinToString(", ")
    }
    val aboutDetails = listOf(
        UserDetails("Name", name, onClick = {}) ,
        UserDetails("Age", age, onClick = {}),
        UserDetails("Height", uiState.selectedHeightInCm.toString(), onHeightClick),
        UserDetails("Location", uiState.locality, onLocationClick),
        UserDetails("Ethnicity", selectedEthnicities, onEthnicityClick),
        UserDetails("Children", uiState.doYouHaveChildren, onChildrenClick),
        UserDetails("Family Plans", uiState.selectedFamilyPlan, onFamilyPlansClick),
    )
    aboutDetails.forEach { detail ->
        RowInterface(detail.title, detail.value, detail.onClick)
    }
}

@Composable
fun MyVirtuesSection(
    uiState: InformationUiState,
    onWorkClick: () -> Unit,
    onSchoolOrCollegeClick: () -> Unit,
    onEducationLevelClick: () -> Unit,
    onReligiousBeliefsClick: () -> Unit,
    onHomeTownClick: () -> Unit,
    onPoliticsClick: () -> Unit,
    onDatingIntentionsClick: () -> Unit,
    onRelationshipTypeClick: () -> Unit,
) {
    HeadingText("My Virtues")
    var selectedRelationShipType = ""
    if (uiState.selectedRelationshipType.isNotEmpty()) {
        selectedRelationShipType = uiState.selectedRelationshipType.joinToString(", ")
    }
    val aboutDetails = listOf(
        UserDetails("Work", uiState.workPlace, onWorkClick),
        UserDetails("College or University", uiState.schoolOrCollege, onSchoolOrCollegeClick),
        UserDetails("Education level", uiState.selectedEducation, onEducationLevelClick),
        UserDetails("Religious beliefs", uiState.selectedReligiousBelief, onReligiousBeliefsClick),
        UserDetails("Home town", uiState.homeTown, onHomeTownClick),
        UserDetails("Politics", uiState.selectedPoliticalBelief, onPoliticsClick),
        UserDetails("Dating Intentions", uiState.selectedDatingIntention, onDatingIntentionsClick),
        UserDetails("Relationship type", selectedRelationShipType, onRelationshipTypeClick),
    )
    aboutDetails.forEach { detail ->
        RowInterface(detail.title, detail.value, detail.onClick)
    }
}

@Composable
fun MyVicesSection(
    uiState: InformationUiState,
    onDrinkingClick: () -> Unit,
    onTobaccoClick: () -> Unit,
    onDrugClick: () -> Unit,
    onWeedClick: () -> Unit
) {
    HeadingText("My Vices")
    val aboutDetails = listOf(
        UserDetails("Drinking", uiState.selectedDrinkOption, onDrinkingClick),
        UserDetails("Tobacco", uiState.selectedTobaccoOption, onTobaccoClick),
        UserDetails("Drug", uiState.selectedDrugOption, onDrugClick),
        UserDetails("Weed", uiState.selectedWeedOption, onWeedClick),
    )
    aboutDetails.forEach { detail ->
        RowInterface(detail.title, detail.value, detail.onClick)
    }
}

@Composable
fun RowInterface(title: String, value: String, onClick: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                Text(text = value, fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}


@Composable
fun HeadingText(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
    )
}

@Composable
fun SubHeadingText(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        modifier = Modifier.padding(top = 5.dp)
    )
}

data class UserDetails(
    val title: String,
    val value: String,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    BloomTheme {
        ProfileScreen(navigateToSettings = {})
    }
}