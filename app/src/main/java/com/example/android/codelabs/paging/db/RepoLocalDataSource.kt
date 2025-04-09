package com.example.android.codelabs.paging.db

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.android.codelabs.paging.db.RepoPagingRemoteKeys.Companion.remoteKeys
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.model.paging.PagedItems

class RepoLocalDataSource(
    private val db: RepoDatabase
) {
    private val reposDao by lazy {
        db.reposDao()
    }
    private val remoteKeysDao by lazy {
        db.remoteKeysDao()
    }

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
        pagedRepos: PagedItems<Int, Repo>,
        refreshData: Boolean
    ) = db.withTransaction {
        if (refreshData) {
            remoteKeysDao.clearRemoteKeys()
            reposDao.clearRepos()
        }
        remoteKeysDao.insertAll(
            pagedRepos.remoteKeys()
        )
        reposDao.insertAll(pagedRepos.items)
    }

    suspend fun getRemoteKeys(repoId: Long): RepoPagingRemoteKeys? = remoteKeysDao.remoteKeysRepoId(repoId)

    fun reposByNamePagingSource(queryString: String): PagingSource<Int, Repo> = reposDao.reposByName(queryString)
}