package com.fastread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fastread.data.SettingsRepository
import com.fastread.ui.home.HomeScreen
import com.fastread.ui.reader.ReaderScreen
import com.fastread.ui.settings.SettingsScreen
import com.fastread.ui.theme.FastReadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsRepo = SettingsRepository.get(this)
            val settings by settingsRepo.state
            FastReadTheme(themeMode = settings.themeMode) {
                AppNav()
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun AppNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onOpenBook = { book -> navController.navigate("reader/${book.id}") },
                onOpenSettings = { navController.navigate("settings") },
            )
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable("reader/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId").orEmpty()
            ReaderScreen(
                bookId = bookId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
