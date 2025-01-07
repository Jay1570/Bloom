package com.example.bloom.screens.home

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bloom.Home
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun HomeScreen() {
    val topLevelRoutes = listOf(
        NavBar("Explore", Home.Explore, Icons.Default.Home),
        NavBar("Liked You", Home.LikedYou, Icons.Default.Favorite),
        NavBar("Chat", Home.Chat, Icons.Default.ChatBubble),
        NavBar("Profile", Home.Profile, Icons.Default.Person2)
    )
    var selectedRoute by rememberSaveable { mutableStateOf("Explore") }
    val navController = rememberNavController()
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
                            navController.navigate(route.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
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
            navController = navController,
            startDestination = Home.Explore,
            modifier = Modifier.padding(it)
        ) {
            composable<Home.Explore> {
                ExploreScreen()
            }
            composable<Home.LikedYou> {
                LikedYouScreen()
            }
            composable<Home.Chat> {
                ChatScreen()
            }
            composable<Home.Profile> {
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