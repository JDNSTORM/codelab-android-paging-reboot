package com.example.android.codelabs.paging.core.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object RepoDatabaseConstructor: RoomDatabaseConstructor<RepoDatabase>{
    override fun initialize(): RepoDatabase
}