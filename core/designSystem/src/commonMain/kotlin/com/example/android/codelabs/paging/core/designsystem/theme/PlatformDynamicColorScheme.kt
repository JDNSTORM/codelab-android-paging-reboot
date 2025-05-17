package com.example.android.codelabs.paging.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

expect val PLATFORM_SUPPORT_DYNAMIC_COLORS: Boolean

@Composable
expect fun platformDynamicColorScheme(darkTheme: Boolean): ColorScheme