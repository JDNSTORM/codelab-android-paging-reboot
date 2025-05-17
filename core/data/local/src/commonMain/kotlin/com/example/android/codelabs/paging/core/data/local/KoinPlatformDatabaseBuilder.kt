package com.example.android.codelabs.paging.core.data.local

import androidx.room.RoomDatabase
import org.koin.core.scope.Scope

internal expect fun Scope.koinPlatformDatabaseBuilder(): RoomDatabase.Builder<RepoDatabase>