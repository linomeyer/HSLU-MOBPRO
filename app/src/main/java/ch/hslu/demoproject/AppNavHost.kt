package ch.hslu.demoproject

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ch.hslu.demoproject.ui.detail.DetailScreen
import ch.hslu.demoproject.ui.electronics.ElectronicsScreen
import ch.hslu.demoproject.ui.home.HomeScreen
import ch.hslu.demoproject.ui.musicplayer.MusicPlayerScreen
import ch.hslu.demoproject.ui.user.UserScreen

class AppNavHost {
    @Composable
    fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.name,
            modifier = modifier,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(500)
                )
            }
        ) {
            composable(route = Screen.Home.name) {
                HomeScreen().Show(navController)
            }
            composable(
                route = "${Screen.Detail.name}/{senderText}/{senderNumber}",
                arguments = listOf(
                    navArgument("senderText") {
                        type = NavType.StringType
                    },
                    navArgument("senderNumber") {
                        type = NavType.IntType
                    }
                )
            ) { navEntry ->
                DetailScreen().Show(
                    navEntry.arguments?.getString("senderText") ?: "error",
                    navEntry.arguments?.getInt("senderNumber") ?: -1,
                    navController
                )
            }
            composable(route = Screen.Electronics.name) {
                ElectronicsScreen().Show(navController)
            }
            composable(route = Screen.User.name) {
                UserScreen().Show(navController)
            }
            composable(route = Screen.MusicPlayer.name) {
                MusicPlayerScreen().Show(navController)
            }
        }
    }

}