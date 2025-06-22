package com.example.android.codelabs.paging.ui.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme
import com.example.android.codelabs.paging.core.models.Repo
import org.jetbrains.compose.ui.tooling.preview.Preview

@PreviewLightDark
@Composable
@Preview
actual fun RepoItemPreview() {
    val repo = remember {
        Repo(
            id = 0,
            name = "android-architecture",
            fullName = "android-architecture",
            description = "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.",
            url = "",
            stars = 30,
            forks = 30,
            language = "en-us"
        )
    }

    AppTheme {
        Surface {
            RepoItem(
                repo = repo
            )
        }
    }
}