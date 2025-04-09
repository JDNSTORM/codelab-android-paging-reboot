package com.example.android.codelabs.paging.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.android.codelabs.paging.model.paging.PagedItems
import com.example.android.codelabs.paging.model.paging.PagingRemoteKeys
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A [RemoteMediator] implementation for handling network paging with key-based pagination.
 *
 * This class manages the loading of data from a remote source (e.g., an API) into a local data store,
 * using a key-based pagination strategy.  It handles initial loading, refreshing, prepending, and
 * appending data based on the [LoadType] provided by the Paging library.
 * This mediator relies on keys (of type `Key`) to identify pages.
 * These keys are typically provided by the API in the response for each page.
 *
 * @param Key The type of the pagination key. Must implement `Any`. This can either be a `pageNumber`, `offset`, or `uuid`.
 * @param Model The type of data exposed to the UI (e.g., a domain model).  Must implement `Any`.
 * @param Entity The type of data retrieved from the network and stored locally (e.g., a database entity). Must implement `Any`.
 * @param initialKey The starting key for the first page of data. This can be `null` for cases using `uuid`.
 * @param getRemoteKeys A suspend function that retrieves pagination keys (previous and next key) associated with a given [Model].
 *                      Returns a [PagingRemoteKeys] object or `null` if no keys are found. The [PagingRemoteKeys] should hold the `prevKey` and `nextKey`.
 *                      **Note**: The `RemoteMediator` may `load` before stored items are emitted.
 *                      Make sure to check if this exists to determine `endOfPaginationReached`.
 * @param fetchList A suspend function that fetches a list of [Entity] objects from the network, given a `pageSize` and a `refreshKey` (pagination key).
 *                  The function should return a [PagedItems] object containing the fetched items and their associated pagination keys (`prevKey` and `nextKey`).
 * @param storeItems A suspend function that stores the fetched [Entity] objects into the local data store.  It receives a [PagedItems] object (containing the items and pagination keys) and a boolean flag indicating if the data store should be cleared before storing the new items (for refresh operations).
 */
@OptIn(ExperimentalPagingApi::class)
abstract class PagingRemoteMediator<Key: Any, Model : Any, Entity : Any>(
    private val initialKey: Key?,
    private val getRemoteKeys: suspend (Model) -> PagingRemoteKeys<Key>?,
    private val fetchList: suspend (pageSize: Int, refreshKey: Key?) -> PagedItems<Key, Entity>,
    private val storeItems: suspend (PagedItems<Key, Entity>, clearData: Boolean) -> Unit,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteMediator<Key, Model>() {

    final override suspend fun load(
        loadType: LoadType,
        state: PagingState<Key, Model>
    ): MediatorResult = withContext(dispatcher) {
        Log.d("PagingRemoteMediator", "Load Requested: $loadType")
        val refreshKey = when (loadType) {
            LoadType.REFRESH -> state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.let {
                    getRemoteKeys(it)?.refreshKey
                }
            }?: initialKey
            LoadType.PREPEND -> {
                /**
                 * If remoteKeys is null, that means the refresh result is not in the database yet.
                 * We can return Success with `endOfPaginationReached = false` because Paging
                 * will call this method again if RemoteKeys becomes non-null.
                 * If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                 * the end of pagination for prepend.
                 */
                val remoteKeys = state.firstItemOrNull()?.let { model ->
                    getRemoteKeys(model)
                }
                remoteKeys?.prevKey
                    ?: return@withContext MediatorResult.Success(remoteKeys != null)
            }
            LoadType.APPEND -> {
                /**
                 * If remoteKeys is null, that means the refresh result is not in the database yet.
                 * We can return Success with `endOfPaginationReached = false` because Paging
                 * will call this method again if RemoteKeys becomes non-null.
                 * If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                 * the end of pagination for append.
                 */
                val remoteKeys = state.lastItemOrNull()?.let { model ->
                    getRemoteKeys(model)
                }
                remoteKeys?.nextKey
                    ?: return@withContext MediatorResult.Success(remoteKeys != null)
            }
        }

        val isRefresh = loadType == LoadType.REFRESH
        val pageSize = if (isRefresh) state.config.initialLoadSize else state.config.pageSize
        Log.d("PagingRemoteMediator", "PageSize: $pageSize, PageKey: $refreshKey")

        try {
            val pagedItems = fetchList(pageSize, refreshKey)

            storeItems(
                pagedItems,
                isRefresh
            )

            val endOfPaginationReached = when(loadType){
                LoadType.REFRESH -> pagedItems.items.isEmpty()
                LoadType.PREPEND -> pagedItems.prevKey == null
                LoadType.APPEND -> pagedItems.nextKey == null
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }.also {
        when(it){
            is MediatorResult.Error -> Log.d("PagingRemoteMediator", "Error(${it.throwable.message})")
            is MediatorResult.Success -> Log.d("PagingRemoteMediator", "Success(${it.endOfPaginationReached})")
        }
    }
}