package com.example.android.codelabs.paging.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.CombinedLoadStates
import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import androidx.paging.compose.LazyPagingItems as AndroidLazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems as androidCollectAsLazyPagingItems

actual class LazyPagingItems<T : Any>(
    private val lazyPagingItems: AndroidLazyPagingItems<T>
) {
    /**
     * Contains the immutable [ItemSnapshotList] of currently presented items, including any
     * placeholders if they are enabled. Note that similarly to [peek] accessing the items in a list
     * will not trigger any loads. Use [get] to achieve such behavior.
     */
    actual val itemSnapshotList: ItemSnapshotList<T>
        get() = lazyPagingItems.itemSnapshotList
    actual val loadState: CombinedLoadStates
        get() = lazyPagingItems.loadState
    actual val itemCount: Int
        get() = lazyPagingItems.itemCount

    /**
     * Returns the presented item at the specified position, notifying Paging of the item access to
     * trigger any loads necessary to fulfill prefetchDistance.
     *
     * @see peek
     */
    actual operator fun get(index: Int): T? = lazyPagingItems.get(index)

    /**
     * Returns the presented item at the specified position, without notifying Paging of the item
     * access that would normally trigger page loads.
     *
     * @param index Index of the presented item to return, including placeholders.
     * @return The presented item at position [index], `null` if it is a placeholder
     */
    actual fun peek(index: Int): T? = lazyPagingItems.peek(index)

    /**
     * Retry any failed load requests that would result in a [LoadState.Error] update to this
     * [LazyPagingItems].
     *
     * Unlike [refresh], this does not invalidate [PagingSource], it only retries failed loads
     * within the same generation of [PagingData].
     *
     * [LoadState.Error] can be generated from two types of load requests:
     * * [PagingSource.load] returning [PagingSource.LoadResult.Error]
     * * [RemoteMediator.load] returning [RemoteMediator.MediatorResult.Error]
     */
    actual fun retry() = lazyPagingItems.retry()

    /**
     * Refresh the data presented by this [LazyPagingItems].
     *
     * [refresh] triggers the creation of a new [PagingData] with a new instance of [PagingSource]
     * to represent an updated snapshot of the backing dataset. If a [RemoteMediator] is set,
     * calling [refresh] will also trigger a call to [RemoteMediator.load] with [LoadType] [REFRESH]
     * to allow [RemoteMediator] to check for updates to the dataset backing [PagingSource].
     *
     * Note: This API is intended for UI-driven refresh signals, such as swipe-to-refresh.
     * Invalidation due repository-layer signals, such as DB-updates, should instead use
     * [PagingSource.invalidate].
     *
     * @see PagingSource.invalidate
     */
    actual fun refresh() = lazyPagingItems.refresh()

}

/**
 * Collects values from this [Flow] of [PagingData] and represents them inside a [LazyPagingItems]
 * instance. The [LazyPagingItems] instance can be used for lazy foundations such as
 * [LazyListScope.items] in order to display the data obtained from a [Flow] of [PagingData].
 *
 * @sample androidx.paging.compose.samples.PagingBackendSample
 * @param context the [CoroutineContext] to perform the collection of [PagingData] and
 *   [CombinedLoadStates].
 */
@Composable
actual fun <T : Any> Flow<PagingData<T>>.collectAsLazyPagingItems(
    context: CoroutineContext
): LazyPagingItems<T> {
    val androidLazyPagingItems = androidCollectAsLazyPagingItems(context)
    return remember(androidLazyPagingItems) {
        LazyPagingItems(androidLazyPagingItems)
    }
}