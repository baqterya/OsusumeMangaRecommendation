package com.baqterya.mangarecommendation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.baqterya.mangarecommendation.ui.theme.MangaRecommendationTheme
import com.baqterya.mangarecommendation.ui.mainmenu.MainMenuScreen
import com.baqterya.mangarecommendation.ui.mangadetail.MangaDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangaRecommendationTheme(dynamicColor = false) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "manga_detail_screen",
                ) {
                    composable("main_menu_screen") {
                        MainMenuScreen(navController = navController)
                    }
                    composable("manga_detail_screen") {
                        MangaDetailScreen(navController = navController)
                    }
                }
            }
        }
    }
}
