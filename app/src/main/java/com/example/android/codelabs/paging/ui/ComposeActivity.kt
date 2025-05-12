package com.example.android.codelabs.paging.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                KoinContext {
                    val viewModel = koinViewModel<SearchRepositoriesViewModel>()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    val pagingModels = viewModel.pagingDataFlow.collectAsLazyPagingItems()
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
}