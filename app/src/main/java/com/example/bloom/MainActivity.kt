package com.example.bloom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bloom.screens.home.ChatScreen
import com.example.bloom.screens.home.HomeScreen
import com.example.bloom.screens.intro.IntroScreen
import com.example.bloom.screens.login.LoginScreen
import com.example.bloom.screens.register.RegistrationScreen
import com.example.bloom.ui.theme.BloomTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BloomTheme {
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
                        startDestination = Routes.Auth,
                    ) {
                        navigation<Routes.Auth>(startDestination = Routes.Intro) {
                            composable<Routes.Intro> {
                                IntroScreen(
                                    navigateToLogin = {
                                        navController.navigate(Routes.Login)
                                    }
                                )
                            }

                            composable<Routes.Login> {
                                LoginScreen(
                                    navigateToHome = {
                                        navController.navigate(Routes.Home) {
                                            launchSingleTop = true
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                    navigateToRegister = {
                                        navController.navigate(Routes.Registration)
                                    }
                                )
                            }

                            composable<Routes.Registration> {
                                RegistrationScreen(
                                    navigateBack = {
                                        navController.popBackStack()
                                    },
                                    navigateToHome = {
                                        navController.navigate(Routes.Home) {
                                            this.launchSingleTop = true
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                        composable<Routes.Home> {
                            HomeScreen(
                                navControllerMain = navController,
                                navControllerBottomBar = rememberNavController()
                            )
                        }

                        composable<Routes.Chat> {
                            val args = it.toRoute<Routes.Chat>()

                            ChatScreen(
                                name = args.name,
                                id = args.connectionId,
                                navigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}