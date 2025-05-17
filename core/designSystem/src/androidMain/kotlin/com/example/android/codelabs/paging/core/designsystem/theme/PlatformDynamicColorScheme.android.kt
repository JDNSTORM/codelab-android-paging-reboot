package com.example.android.codelabs.paging.core.designsystem.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual val PLATFORM_SUPPORT_DYNAMIC_COLORS: Boolean
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@SuppressLint("NewApi")
@Composable
actual fun platformDynamicColorScheme(darkTheme: Boolean): ColorScheme {
    val context = LocalContext.current
    return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}
