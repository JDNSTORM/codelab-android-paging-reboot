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

package com.example.android.codelabs.paging.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.codelabs.paging.core.models.Repo
import com.example.android.codelabs.paging.core.models.paging.PagedItems
import com.example.android.codelabs.paging.core.models.paging.PagingRemoteKeys

@Entity(tableName = "remote_keys")
data class RepoPagingRemoteKeys(
    @PrimaryKey val repoId: Long,
    override val refreshKey: Int?,
    override val prevKey: Int?,
    override val nextKey: Int?,
    val query: String
//    override val position: Int
): PagingRemoteKeys<Int> {
    companion object {
        /**
         * Creates a list of `RepoPagingRemoteKeys` for the current page of repositories.
         *
         * This function maps each repository in the current page's item list to a `RepoPagingRemoteKeys` object.
         * Each `RepoPagingRemoteKeys` object stores information about the repository's ID, the refresh key for the current dataset,
         * the previous and next page keys, and the position of the repository within the entire dataset. This information is
         * crucial for managing the paging process, particularly for loading data from a remote source and caching it locally
         * with associated metadata for efficient refresh and subsequent page loading.
         *
         * @param currentPosition The starting position of the current page's items within the overall dataset.
         * @return A list of `RepoPagingRemoteKeys` objects, each corresponding to a repository in the current page.
         */
        fun PagedItems<Int, Repo>.remoteKeys(
            query: String,
//            currentPosition: Int
        ): List<RepoPagingRemoteKeys> = items.map { repo ->
            RepoPagingRemoteKeys(
                repoId = repo.id,
                refreshKey = refreshKey,
                prevKey = prevKey,
                nextKey = nextKey,
                query = query
//                position = currentPosition + index
            )
        }
    }
}