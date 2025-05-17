package com.example.android.codelabs.paging.core.data.local

import androidx.room.Dao
import androidx.room.Transaction
import com.example.android.codelabs.paging.core.data.local.RepoPagingRemoteKeys.Companion.remoteKeys
import com.example.android.codelabs.paging.core.models.Repo
import com.example.android.codelabs.paging.core.models.paging.PagedItems
import kotlinx.coroutines.flow.first

@Dao
abstract class RepoAndRemoteKeysDao(db: RepoDatabase) {
    private val reposDao by lazy {
        db.reposDao()
    }
    private val remoteKeysDao by lazy {
        db.remoteKeysDao()
    }

    @Transaction
    open suspend fun insertPagedRepos(
        queryString: String,
        pagedRepos: PagedItems<Int, Repo>,
        refreshData: Boolean
    ) {
        if (refreshData) {
            remoteKeysDao.clearRemoteKeys()
            reposDao.clearRepos()
        }
        remoteKeysDao.insertAll(
            pagedRepos.remoteKeys(query = queryString)
        )
        reposDao.insertAll(pagedRepos.items)
    }

    /**
     * Creates missing pagination data for a given query string by fetching existing local data
     * and inserting it along with corresponding remote keys. This is used to populate the initial
     * data for a PagingSource when there's no remote key data for the query.
     *
     * @param queryString The query string for which to create missing data.
     */
    @Transaction
    open suspend fun createMissingDataForQuery(queryString: String) {
        if (remoteKeysDao.remoteKeyQueryExists(queryString)) return
        val repos = reposDao.readReposByName(queryString.replace(' ', '%')).first()

        if (repos.isEmpty()) return
        insertPagedRepos(
            queryString = queryString,
            pagedRepos = PagedItems(
                refreshKey = null,
                prevKey = null,
                nextKey = null,
                items = repos
            ),
            refreshData = false
        )
    }
}