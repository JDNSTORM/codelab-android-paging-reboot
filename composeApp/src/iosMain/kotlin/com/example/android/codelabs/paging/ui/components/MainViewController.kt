package com.example.android.codelabs.paging.ui.components

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.codelabs.paging.ui.ComposeApp

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    }
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