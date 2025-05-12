/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.android.codelabs.paging.core.models.Repo
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)

    @Deprecated("Use reposByQuery instead", ReplaceWith("reposByQuery(queryString)"))
    @Query(
        "SELECT * FROM repos WHERE " +
            "name LIKE '%'||:queryString||'%' OR description LIKE '%'||:queryString||'%' " +
            "ORDER BY stars DESC, name ASC"
    )
    fun reposByName(queryString: String): PagingSource<Int, Repo>

    @Transaction
    @Query("SELECT * FROM repos " +
            "INNER JOIN remote_keys ON repos.id = remote_keys.repoId " +
            "WHERE remote_keys.`query` = :queryString " +
            "ORDER BY stars DESC, name ASC")
    fun reposByQuery(queryString: String): PagingSource<Int, Repo>

    @Query(
        "SELECT * FROM repos WHERE " +
            "name LIKE '%'||:queryString||'%' OR description LIKE '%'||:queryString||'%' " +
            "ORDER BY stars DESC, name ASC"
    )
    fun readReposByName(queryString: String): Flow<List<Repo>>

    @Query("DELETE FROM repos")
    suspend fun clearRepos()
}
