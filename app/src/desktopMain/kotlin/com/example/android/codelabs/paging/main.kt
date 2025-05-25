package com.example.android.codelabs.paging

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.android.codelabs.paging.ui.ComposeApp
import org.koin.compose.KoinApplication

fun main() { //FIXME: CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`
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
                LocalViewModelStoreOwner
                ComposeApp()
            }
        }
    }
}
