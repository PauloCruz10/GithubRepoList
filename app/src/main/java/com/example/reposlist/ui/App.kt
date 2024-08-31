package com.example.reposlist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reposlist.ui.ui.detailscreen.DetailsScreen
import com.example.reposlist.ui.ui.homescreen.HomeScreen

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "/") {
        composable("/") {
            HomeScreen(
                onAppSelected = { id, name ->
                    navController.navigate("/repo/${id}?name=${name}")
                }
            )
        }
        composable(
            route = "/repo/{id}?name={name}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                },
                navArgument("name") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailsScreen(
                backAction = {
                    navController.popBackStack()
                }
            )
        }
    }
}
