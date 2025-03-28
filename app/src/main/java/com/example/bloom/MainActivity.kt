package com.example.bloom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bloom.screens.advanced_info.AdvancedInformationScreen
import com.example.bloom.screens.advanced_info.IntermediateScreen2
import com.example.bloom.screens.auth.EmailVerificationScreen
import com.example.bloom.screens.auth.IntroScreen
import com.example.bloom.screens.auth.LoginScreen
import com.example.bloom.screens.auth.RegistrationScreen
import com.example.bloom.screens.basic_information.BasicInformationScreen
import com.example.bloom.screens.connection.ChatScreen
import com.example.bloom.screens.home.HomeScreen
import com.example.bloom.screens.information.InformationScreen
import com.example.bloom.screens.information.IntermediateScreen1
import com.example.bloom.screens.registration_completion.RegistrationCompleteScreen
import com.example.bloom.screens.registration_completion.SuggestSubscriptionScreen
import com.example.bloom.screens.settings.SettingsScreen
import com.example.bloom.ui.theme.BloomTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme by BloomApplication.themePreference.theme.collectAsState()
            val darkTheme: Boolean = when (theme) {
                Theme.LIGHT -> false
                Theme.DARK -> true
                Theme.SYSTEM -> isSystemInDarkTheme()
            }
            BloomTheme(darkTheme = darkTheme) {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                ObserveAsEvents(flow = SnackbarManager.events, snackbarHostState) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Short
                        )

                        if (result == SnackbarResult.ActionPerformed) event.action?.action?.invoke()
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize()
                ) { _ ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Auth,
                    ) {
                        navigation<Auth>(startDestination = Intro) {
                            composable<Intro> {
                                IntroScreen(
                                    navigateToLogin = {
                                        navController.navigate(Login)
                                    }
                                )
                            }

                            composable<Login> {
                                LoginScreen(
                                    navigateToVerification = {
                                        navController.navigate(EmailVerification("login",it)) {
                                            launchSingleTop = true
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                )
                            }

                            composable<Registration> {
                                RegistrationScreen(
                                    navigateBack = {
                                        navController.popBackStack()
                                    },
                                    navigateToVerification = {
                                        navController.navigate(EmailVerification("register",""))
                                    }
                                )
                            }
                        }

                        composable<EmailVerification> {
                            EmailVerificationScreen(
                                navigateToNextScreen = {
                                    navController.navigate(it){
                                    launchSingleTop = true
                                    popUpTo(0) { inclusive = true
                                    }
                                } }
                            )
                        }

                        composable<BasicInformation> {
                            BasicInformationScreen(
                                navigateToNextScreen = {
                                    navController.navigate(Intermediate1) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Intermediate1> {
                            IntermediateScreen1(
                                navigateToNextScreen = {
                                    navController.navigate(Information) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Intermediate2> {
                            IntermediateScreen2(
                                navigateToNextScreen = {
                                    navController.navigate(AdvancedInformation) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<Information> {
                            InformationScreen(
                                navigateToNextScreen = {
                                    navController.navigate(Intermediate2) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable<AdvancedInformation> {
                            AdvancedInformationScreen(
                                navigateToNextScreen = {
                                    navController.navigate(RegistrationComplete) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable<SuggestSubscription> {
                            SuggestSubscriptionScreen(
                                navigateToPayment = { /*TODO: Navigate To Payment Screen*/ },
                                navigateToHome = {
                                    navController.navigate(RegistrationComplete) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable<RegistrationComplete> {
                            RegistrationCompleteScreen(
                                navigateToNextScreen = {
                                    navController.navigate(Home) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }



                        composable<Home> {
                            HomeScreen(
                                navControllerMain = navController,
                                navControllerBottomBar = rememberNavController()
                            )
                        }

                        composable<ChatScreen> {
                            val args = it.toRoute<ChatScreen>()
                            ChatScreen(

                                navigateBack = { navController.popBackStack() }
                            )
                        }

                        composable<Settings> {
                            SettingsScreen(
                                navigateToIntro = {
                                    navController.navigate(Intro) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onDeleteAccountClick = {
                                    navController.navigate(Intro) {
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                navigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}