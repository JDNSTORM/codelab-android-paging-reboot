package com.example.android.codelabs.paging.db

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.android.codelabs.paging.db.RepoPagingRemoteKeys.Companion.remoteKeys
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.model.paging.PagedItems
import kotlinx.coroutines.flow.first

class RepoLocalDataSource(
    private val db: RepoDatabase
) {
    private val reposDao by lazy {
        db.reposDao()
    }
    private val remoteKeysDao by lazy {
        db.remoteKeysDao()
    }

    @Deprecated("Use overload with PagedItems instead.")
    suspend fun insertPagedRepos(
        repos: List<Repo>,
        keys: List<RepoPagingRemoteKeys>,
        refreshData: Boolean
    ) = db.withTransaction {
        if (refreshData) {
            remoteKeysDao.clearRemoteKeys()
            reposDao.clearRepos()
        }
        remoteKeysDao.insertAll(keys)
        reposDao.insertAll(repos)
    }

    suspend fun insertPagedRepos(
        queryString: String,
        pagedRepos: PagedItems<Int, Repo>,
        refreshData: Boolean
    ) = db.withTransaction {
        if (refreshData) {
            remoteKeysDao.clearRemoteKeys()
            reposDao.clearRepos()
        }
        remoteKeysDao.insertAll(
            pagedRepos.remoteKeys(query = queryString)
        )
        reposDao.insertAll(pagedRepos.items)
    }

    suspend fun getRemoteKeys(repoId: Long): RepoPagingRemoteKeys? = remoteKeysDao.remoteKeysRepoId(repoId)

    fun reposByNamePagingSource(queryString: String): PagingSource<Int, Repo> = reposDao.reposByName(queryString)

    /**
     * Creates missing pagination data for a given query string by fetching existing local data
     * and inserting it along with corresponding remote keys. This is used to populate the initial
     * data for a PagingSource when there's no remote key data for the query.
     *
     * @param queryString The query string for which to create missing data.
     */
    suspend fun createMissingDataForQuery(queryString: String) = db.withTransaction {
        if (remoteKeysDao.remoteKeyQueryExists(queryString)) return@withTransaction
        val repos = reposDao.readReposByName(queryString.replace(' ', '%')).first()

        if (repos.isEmpty()) return@withTransaction
        insertPagedRepos(
            queryString = queryString,
            pagedRepos = PagedItems(
                refreshKey = null,
                prevKey = null,
                nextKey = null,
                items = repos
            ),
            refreshData = true
        )
    }
}