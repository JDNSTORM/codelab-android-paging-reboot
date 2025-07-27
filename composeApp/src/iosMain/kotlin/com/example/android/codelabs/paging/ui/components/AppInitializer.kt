package com.example.android.codelabs.paging.ui.components

import com.example.android.codelabs.paging.appModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
fun initializeApp(){
    startKoin {
        if (Platform.isDebugBinary) printLogger(Level.DEBUG)
        modules(appModule)
    }
}