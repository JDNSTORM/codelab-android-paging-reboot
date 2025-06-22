package com.example.android.codelabs.paging.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@PreviewLightDark
@Composable
actual fun SeparatorItemPreview() {
    AppTheme {
        SeparatorItem(
            description = "10000+ stars"
        )
    }
}