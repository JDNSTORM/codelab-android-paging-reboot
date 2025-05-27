package com.example.android.codelabs.paging

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.codelabs.paging.ui.ComposeApp
import org.koin.compose.KoinApplication

fun main() {
    application {
        KoinApplication(
            application = {
                modules(appModule)
            }
        ) {
            Window(
                onCloseRequest = ::exitApplication,
                title = "Paging"
            ) {
                NavHost( //Needed to provide `SAVED_STATE_REGISTRY_OWNER_KEY`
                    navController = rememberNavController(),
                    startDestination = "Main"
                ) {
                    composable("Main"){
                        ComposeApp()
                    }
                }
            }
        }
    }
}
