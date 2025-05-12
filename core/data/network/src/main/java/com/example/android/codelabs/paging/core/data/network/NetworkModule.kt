package com.example.android.codelabs.paging.core.data.network

import org.koin.dsl.module

val networkModule = module {
    single {
        GithubService.create()
    }
}