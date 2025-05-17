package com.example.android.codelabs.paging.core.repositories

import com.example.android.codelabs.paging.core.data.local.localModule
import com.example.android.codelabs.paging.core.data.network.networkModule
import org.koin.dsl.module

val repositoriesModule = module {
    includes(
        localModule,
        networkModule
    )
    single {
        GithubRepository(
            service = get(),
            localDataSource = get()
        )
    }
}