package com.example.android.codelabs.paging.data

import com.example.android.codelabs.paging.api.GithubService
import com.example.android.codelabs.paging.api.IN_QUALIFIER
import com.example.android.codelabs.paging.db.RepoLocalDataSource
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.model.paging.PagedItems
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RepoRemoteMediator(
    private val query: String,
    private val service: GithubService,
    private val localDataSource: RepoLocalDataSource,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): PagingRemoteMediator<Int, Repo, Repo>(
    initialKey = GITHUB_STARTING_PAGE_INDEX,
    getRemoteKeys = {
        localDataSource.getRemoteKeys(it.id)
    },
    fetchList = { pageSize, pageIndex ->
        val response = service.searchRepos(
            query = query + IN_QUALIFIER,
            page = pageIndex!!,
            itemsPerPage = pageSize
        )
        PagedItems(
            refreshKey = pageIndex,
            prevKey = pageIndex - 1,
            nextKey = response.nextPage,
            items = response.items
        )
    },
    storeItems = { pagedItems, clearData ->
        localDataSource.insertPagedRepos(
            pagedRepos = pagedItems,
            refreshData = clearData
        )
    },
    dispatcher = dispatcher
){
    companion object {
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }
}
