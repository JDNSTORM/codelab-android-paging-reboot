package com.example.android.codelabs.paging.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.paging.LoadState
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme
import kotlinx.coroutines.delay

@PreviewLightDark
@Composable
actual fun LoadStateFooterItemPreview() {
    val errorState = remember {
        LoadState.Error(
            Exception("Request Timeout")
        )
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(isLoading) {
        if (!isLoading) return@LaunchedEffect
        delay(3000)
        isLoading = false
    }
    val loadState by remember {
        derivedStateOf {
            if(isLoading) LoadState.Loading
            else errorState
        }
    }
    AppTheme {
        Surface {
            Column {
                LoadStateFooterItem(
                    state = loadState
                ) {
                    isLoading = true
                }
                LoadStateFooterItem(
                    state = LoadState.Loading
                ) { }
                LoadStateFooterItem(
                    state = errorState
                ) { }
            }
        }
    }
}