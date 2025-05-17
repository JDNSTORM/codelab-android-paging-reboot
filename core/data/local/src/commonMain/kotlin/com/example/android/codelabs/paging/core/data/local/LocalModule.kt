package com.example.android.codelabs.paging.core.data.local

import org.koin.dsl.module

val localModule = module {
    single {
        koinPlatformDatabaseBuilder()
            .build()
    }
    single {
        val db = get<RepoDatabase>()
        db.reposDao()
    }
    single {
        val db = get<RepoDatabase>()
        db.remoteKeysDao()
    }
    single {
        RepoLocalDataSource(
            db = get()
        )
    }
}