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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bloom.*
import com.example.bloom.Chat
import com.example.bloom.Connection
import com.example.bloom.ui.theme.BloomTheme

@Composable
fun HomeScreen(
    navControllerMain: NavHostController = rememberNavController(),
    navControllerBottomBar: NavHostController = rememberNavController()
) {
    val topLevelRoutes = listOf(
        NavBar("Explore", Explore, Icons.Default.Home),
        NavBar("Liked You", LikedYou, Icons.Default.Favorite),
        NavBar("Connections", Connection, Icons.Default.ChatBubble),
        NavBar("Profile", Profile, Icons.Default.Person2)
    )
    var selectedRoute by rememberSaveable { mutableStateOf("Explore") }
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                val navBackStackEntry by navControllerBottomBar.currentBackStackEntryAsState()
                val current = navBackStackEntry?.destination
                topLevelRoutes.forEach { route ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = route.icon,
                                contentDescription = route.name,
                            )
                        },
                        selected = current?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
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
            startDestination = Explore,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) {
            composable<Explore> {
                ExploreScreen()
            }
            composable<LikedYou> {
                LikedYouScreen()
            }
            composable<Connection> {
                ConnectionsScreen(
                    onConnectionClick = { id, name ->
                        navControllerMain.navigate(Chat(id, name)) {
                            restoreState = true
                        }
                    }
                )
            }
            composable<Profile> {
                ProfileScreen(
                    navigateToSettings = {
                        navControllerMain.navigate(Settings)
                    }
                )
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

data class NavBar(
    val name: String,
    val route: Routes,
    val icon: ImageVector
)