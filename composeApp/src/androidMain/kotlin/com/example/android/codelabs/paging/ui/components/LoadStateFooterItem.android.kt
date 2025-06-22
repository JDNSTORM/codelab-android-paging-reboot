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
    LoadStateFooterItemPreviewWrapper()
}