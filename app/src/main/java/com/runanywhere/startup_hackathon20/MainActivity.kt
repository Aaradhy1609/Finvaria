package com.runanywhere.startup_hackathon20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.runanywhere.startup_hackathon20.ui.screens.*
import com.runanywhere.startup_hackathon20.ui.theme.AppTheme
import com.runanywhere.startup_hackathon20.ui.theme.Startup_hackathon20Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: YouthHubViewModel = viewModel()
            val preferences by viewModel.userPreferences.collectAsState()
            
            Startup_hackathon20Theme(
                darkTheme = preferences.darkMode,
                appTheme = when (preferences.theme) {
                    com.runanywhere.startup_hackathon20.data.ThemePreference.BLUE -> AppTheme.BLUE
                    com.runanywhere.startup_hackathon20.data.ThemePreference.PURPLE -> AppTheme.PURPLE
                    com.runanywhere.startup_hackathon20.data.ThemePreference.GREEN -> AppTheme.GREEN
                    else -> AppTheme.BLUE
                }
            ) {
                YouthHubApp(viewModel = viewModel)
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Finance : Screen("finance", "Finance", Icons.Default.AccountBalance)
    object Legal : Screen("legal", "Legal", Icons.Default.Gavel)
    object Education : Screen("education", "Education", Icons.Default.School)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouthHubApp(viewModel: YouthHubViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Finance,
        Screen.Legal,
        Screen.Education,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            AnimatedBottomBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                Screen.Home.route,
                enterTransition = { fadeIn() + slideInHorizontally() },
                exitTransition = { fadeOut() + slideOutHorizontally() }
            ) {
                HomeScreen(viewModel = viewModel, navController = navController)
            }
            composable(
                Screen.Finance.route,
                enterTransition = { fadeIn() + slideInHorizontally() },
                exitTransition = { fadeOut() + slideOutHorizontally() }
            ) {
                FinanceScreen(viewModel = viewModel)
            }
            composable(
                Screen.Legal.route,
                enterTransition = { fadeIn() + slideInHorizontally() },
                exitTransition = { fadeOut() + slideOutHorizontally() }
            ) {
                LegalScreen(viewModel = viewModel)
            }
            composable(
                Screen.Education.route,
                enterTransition = { fadeIn() + slideInHorizontally() },
                exitTransition = { fadeOut() + slideOutHorizontally() }
            ) {
                EducationScreen(viewModel = viewModel)
            }
            composable(
                Screen.Settings.route,
                enterTransition = { fadeIn() + slideInHorizontally() },
                exitTransition = { fadeOut() + slideOutHorizontally() }
            ) {
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AnimatedBottomBar(
    items: List<Screen>,
    currentRoute: String?,
    onItemClick: (Screen) -> Unit
) {
    NavigationBar(
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    AnimatedIcon(
                        icon = screen.icon,
                        selected = selected
                    )
                },
                label = { 
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelSmall
                    ) 
                },
                selected = selected,
                onClick = { onItemClick(screen) }
            )
        }
    }
}

@Composable
fun AnimatedIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "icon_scale"
    )
    
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.scale(scale)
    )
}