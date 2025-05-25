package com.example.android.codelabs.paging

import com.example.android.codelabs.paging.ui.SearchRepositoriesViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    viewModelOf(::SearchRepositoriesViewModel)
}