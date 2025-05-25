package com.example.android.codelabs.paging.core.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.scope.Scope
import java.io.File

internal actual fun Scope.koinPlatformDatabaseBuilder(): RoomDatabase.Builder<RepoDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), RepoDatabase.DATABASE_NAME)
    return Room.databaseBuilder<RepoDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
}