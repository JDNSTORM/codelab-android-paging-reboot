package com.example.android.codelabs.paging.db

import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.android.codelabs.paging.model.Repo

class RepoLocalDataSource(
    private val db: RepoDatabase
) {
    private val reposDao = db.reposDao()
    private val remoteKeysDao = db.remoteKeysDao()

    suspend fun insertPagedRepos(
        repos: List<Repo>,
        keys: List<RemoteKeys>,
        refreshData: Boolean
    ) = db.withTransaction {
        if (refreshData) {
            remoteKeysDao.clearRemoteKeys()
            reposDao.clearRepos()
        }
        remoteKeysDao.insertAll(keys)
        reposDao.insertAll(repos)
    }

    suspend fun getRemoteKeys(repoId: Long): RemoteKeys? = remoteKeysDao.remoteKeysRepoId(repoId)

    fun reposByNamePagingSource(queryString: String): PagingSource<Int, Repo> = reposDao.reposByName(queryString)
}