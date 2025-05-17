package com.example.android.codelabs.paging.core.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.scope.Scope

internal actual fun Scope.koinPlatformDatabaseBuilder(): RoomDatabase.Builder<RepoDatabase> {
    return Room.databaseBuilder(
        context = get(),
        klass = RepoDatabase::class.java,
        name = RepoDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration(true)
}