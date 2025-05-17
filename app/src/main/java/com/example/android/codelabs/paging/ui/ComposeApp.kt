package com.example.android.codelabs.paging.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeApp() {
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