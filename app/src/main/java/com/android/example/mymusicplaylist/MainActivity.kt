package com.android.example.mymusicplaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.example.mymusicplaylist.ui.add_playlist.AddPlaylistScreen
import com.android.example.mymusicplaylist.ui.playlist.PlaylistScreen
import com.android.example.mymusicplaylist.ui.song_list.SongListScreen
import com.android.example.mymusicplaylist.ui.song_selection.SongSelectionScreen
import com.android.example.mymusicplaylist.ui.theme.MyMusicPlaylistTheme
import com.android.example.mymusicplaylist.util.Routes
import com.android.example.mymusicplaylist.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMusicPlaylistTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.PLAYLIST_SCREEN) {
                    composable(route = Routes.PLAYLIST_SCREEN) {
                        PlaylistScreen(
                            onNavigate = { event ->
                                when (event) {
                                    is UiEvent.Navigate -> navController.navigate(event.route)
                                    else -> Unit
                                }
                            }
                        )
                    }
                    composable(route = Routes.ADD_PLAYLIST_SCREEN) {
                        AddPlaylistScreen(
                            {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        route = Routes.SONG_LIST_SCREEN + "?playlistId={playlistId}&playlistName={playlistName}",
                        arguments = listOf(
                            navArgument(name = "playlistId") {
                                type = NavType.IntType
                            }, navArgument(name = "playlistName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        SongListScreen(onNavigate = { event ->
                            when (event) {
                                is UiEvent.Navigate -> navController.navigate(event.route)
                                is UiEvent.PopBackstack -> navController.popBackStack()
                                else -> Unit
                            }
                        })
                    }

                    composable(
                        route = Routes.SONG_SELECTION_SCREEN + "?playlistId={playlistId}",
                        arguments = listOf(
                            navArgument(name = "playlistId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        SongSelectionScreen(onNavigate = { event ->
                            when (event) {
                                is UiEvent.Navigate -> navController.navigate(event.route)
                                is UiEvent.PopBackstack -> navController.popBackStack()
                                else -> Unit
                            }
                        })
                    }
                }
            }
        }
    }
}