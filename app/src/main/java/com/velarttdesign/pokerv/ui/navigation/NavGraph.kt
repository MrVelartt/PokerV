package com.velarttdesign.pokerv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.velarttdesign.pokerv.ui.screens.*

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.LOBBY) { LobbyScreen(navController) }
        composable(Routes.GAME) { GameScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
        composable(Routes.CLUB) { ClubScreen(navController) }
        composable(Routes.SETTINGS) { SettingsScreen(navController) }
    }
}
