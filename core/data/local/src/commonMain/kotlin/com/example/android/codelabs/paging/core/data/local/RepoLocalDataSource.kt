package com.example.android.codelabs.paging.core.data.local

import androidx.paging.PagingSource
import com.example.android.codelabs.paging.core.models.Repo
import com.example.android.codelabs.paging.core.models.paging.PagedItems

class RepoLocalDataSource(
    private val db: RepoDatabase
) {
    private val reposDao by lazy {
        db.reposDao()
    }
    private val remoteKeysDao by lazy {
        db.remoteKeysDao()
    }
    private val repoAndRemoteKeysDao by lazy {
        db.repoAndRemoteKeysDao()
    }

    suspend fun insertPagedRepos(
        queryString: String,
        pagedRepos: PagedItems<Int, Repo>,
        refreshData: Boolean
    ) = repoAndRemoteKeysDao.insertPagedRepos(queryString, pagedRepos, refreshData)

    suspend fun getRemoteKeys(repoId: Long): RepoPagingRemoteKeys? = remoteKeysDao.remoteKeysRepoId(repoId)

    fun reposPagingSource(queryString: String): PagingSource<Int, Repo> = reposDao.reposByQuery(queryString)

    /**
     * Creates missing pagination data for a given query string by fetching existing local data
     * and inserting it along with corresponding remote keys. This is used to populate the initial
     * data for a PagingSource when there's no remote key data for the query.
     *
     * @param queryString The query string for which to create missing data.
     */
    suspend fun createMissingDataForQuery(queryString: String) = repoAndRemoteKeysDao.createMissingDataForQuery(queryString)
}