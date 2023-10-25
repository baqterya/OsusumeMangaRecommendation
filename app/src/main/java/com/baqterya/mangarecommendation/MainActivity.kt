package com.baqterya.mangarecommendation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.baqterya.mangarecommendation.ui.theme.MangaRecommendationTheme
import com.baqterya.mangarecommendation.ui.mainmenu.MainMenuScreen
import com.baqterya.mangarecommendation.ui.mangadetail.MangaDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangaRecommendationTheme(dynamicColor = false) {
                val navController = rememberNavController()
                val backgroundColor = MaterialTheme.colorScheme.background
                NavHost(
                    navController = navController,
                    startDestination = "main_menu_screen",
                ) {
                    composable("main_menu_screen") {
                        MainMenuScreen(navController = navController)
                    }
                    composable(
                        route = "manga_detail_screen/{dominantColor}/{mangaId}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("mangaId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: backgroundColor
                        }
                        val mangaId = remember {
                            it.arguments?.getInt("mangaId")
                        }
                        MangaDetailScreen(
                            navController = navController,
                            dominantColor = dominantColor,
                            mangaId = mangaId ?: 0
                        )
                    }
                }
            }
        }
    }
}
