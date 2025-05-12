package com.example.android.codelabs.paging.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(
            this, Injection.provideViewModelFactory(
                context = this,
                owner = this
            )
        )[SearchRepositoriesViewModel::class.java]
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val pagingModels = viewModel.pagingDataFlow.collectAsLazyPagingItems()
            AppTheme {
                Surface {
                    SearchRepositoriesScreen(
                        uiState = uiState,
                        pagingModels = pagingModels,
                        uiAction = viewModel::uiAction
                    )
                }
            }
        }
    }
}