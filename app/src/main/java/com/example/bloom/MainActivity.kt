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
import com.example.bloom.screens.settings.ChangeEmailScreen
import com.example.bloom.screens.settings.ChangePasswordScreen
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
                                    },
                                    navigateToHome = {
                                        navController.navigate(Home) {
                                            launchSingleTop = true
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                )
                            }

                            composable<Login> {
                                LoginScreen(
                                    navigateToVerification = {
                                        navController.navigate(EmailVerification("login"))
                                    },
                                    navigateToRegister = {
                                        navController.navigate(Registration)
                                    }
                                )
                            }

                            composable<Registration> {
                                RegistrationScreen(
                                    navigateBack = {
                                        navController.popBackStack()
                                    },
                                    navigateToVerification = {
                                        navController.navigate(EmailVerification("register"))
                                    }
                                )
                            }
                        }

                        composable<EmailVerification> {
                            val origin = it.toRoute<EmailVerification>().origin

                            val navigateToNext: () -> Unit = {
                                when (origin) {
                                    "login" -> {
                                        navController.navigate(Home) {
                                            launchSingleTop = true
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }

                                    "change" -> {
                                        navController.popBackStack(
                                            route = Settings,
                                            inclusive = false
                                        )
                                    }

                                    else -> {
                                        navController.navigate(BasicInformation)
                                    }
                                }
                            }
                            EmailVerificationScreen(
                                navigateBack = {
                                    navController.popBackStack()
                                },
                                navigateToNextScreen = navigateToNext
                            )
                        }

                        composable<BasicInformation> {
                            BasicInformationScreen(
                                navigateToNextScreen = {
                                    navController.navigate(Intermediate1)
                                }
                            )
                        }

                        composable<Intermediate1> {
                            IntermediateScreen1(
                                navigateToNextScreen = {
                                    navController.navigate(Information)
                                }
                            )
                        }

                        composable<Intermediate2> {
                            IntermediateScreen2(
                                navigateToNextScreen = {
                                    navController.navigate(AdvancedInformation)
                                }
                            )
                        }

                        composable<Information> {
                            InformationScreen(
                                navigateToNextScreen = {
                                    navController.navigate(Intermediate2)
                                }
                            )
                        }

                        composable<AdvancedInformation> {
                            AdvancedInformationScreen(
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

                        composable<Chat> {
                            val args = it.toRoute<Chat>()
                            ChatScreen(
                                name = args.name,
                                id = args.connectionId,
                                navigateBack = { navController.popBackStack() }
                            )
                        }

                        composable<Settings> {
                            SettingsScreen(
                                navigateToChangeEmail = {
                                    navController.navigate(ChangeEmail)
                                },
                                navigateToChangePassword = {
                                    navController.navigate(ChangePassword)
                                },
                                navigateToIntro = {
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

                        composable<ChangeEmail> {
                            ChangeEmailScreen(
                                navigateBack = { navController.popBackStack() },
                                navigateToVerification = {
                                    navController.navigate(EmailVerification("change"))
                                }
                            )
                        }

                        composable<ChangePassword> {
                            ChangePasswordScreen(
                                navigateBack = { navController.popBackStack() },
                                navigateToVerification = {
                                    navController.navigate(EmailVerification("change"))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}