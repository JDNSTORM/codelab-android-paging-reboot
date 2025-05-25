package com.example.android.codelabs.paging

import com.example.android.codelabs.paging.core.repositories.repositoriesModule
import com.example.android.codelabs.paging.ui.SearchRepositoriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(
        repositoriesModule,
        viewModelModule
    )
}