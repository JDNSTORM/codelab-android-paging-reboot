package com.example.android.codelabs.paging.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.ui.theme.AppTheme

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
            val uiState by viewModel.state.collectAsStateWithLifecycle()
            val pagingModels = viewModel.pagingDataFlow.collectAsLazyPagingItems()
            AppTheme {
                Surface {
                    SearchRepositoriesScreen(
                        uiState = uiState,
                        pagingModels = pagingModels,
                        uiAction = viewModel.accept
                    )
                }
            }
        }
    }
}