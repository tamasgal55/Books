package com.books.android.booksapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.books.android.booksapp.ui.details.Details
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "Home") {
                composable("Home") { BooksMainScreen(navController) }
                composable("QueryScreen") { QueryScreen() }
                composable("Details/{bookId}", arguments = listOf(navArgument("bookId") { type = NavType.StringType })) {
                    backStackEntry ->
                        backStackEntry.arguments?.getString("bookId")?.let { Details(it) }
                }
            }
        }
    }
}
