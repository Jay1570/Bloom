package com.example.bloom.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bloom.Routes
import com.example.bloom.ui.theme.BloomTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navControllerMain: NavHostController = rememberNavController(),
    navControllerBottomBar: NavHostController = rememberNavController()
) {
    val topLevelRoutes = listOf(
        NavBar("Explore", Routes.Explore, Icons.Default.Home),
        NavBar("Liked You", Routes.LikedYou, Icons.Default.Favorite),
        NavBar("Connections", Routes.Connection, Icons.Default.ChatBubble),
        NavBar("Profile", Routes.Profile, Icons.Default.Person2)
    )
    var selectedRoute by rememberSaveable { mutableStateOf("Explore") }
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                topLevelRoutes.forEach { route ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = route.icon,
                                contentDescription = route.name,
                            )
                        },
                        selected = selectedRoute == route.name,
                        onClick = {
                            selectedRoute = route.name
                            navControllerBottomBar.navigate(route.route) {
                                popUpTo(navControllerBottomBar.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.background,
                            unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                            selectedIconColor = MaterialTheme.colorScheme.inverseSurface
                        )
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navControllerBottomBar,
            startDestination = Routes.Explore,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            composable<Routes.Explore> {
                ExploreScreen()
            }
            composable<Routes.LikedYou> {
                LikedYouScreen()
            }
            composable<Routes.Connection> {
                ConnectionsScreen(
                    onConnectionClick = { id, name ->
                        navControllerMain.navigate(Routes.Chat(id, name)) {
                            restoreState = true
                        }
                    }
                )
            }
            composable<Routes.Profile> {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    BloomTheme {
        HomeScreen()
    }
}

data class NavBar<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector
)