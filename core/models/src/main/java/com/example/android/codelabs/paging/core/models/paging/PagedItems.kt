package com.example.android.codelabs.paging.core.models.paging

/**
 * Represents a page of items, along with keys for navigating to previous and next pages, and a key for refreshing the data.
 *
 * @param Key The type of the paging keys.  Must be non-nullable.
 * @param Entity The type of the items in the page.
 * @property refreshKey The key used to refresh the current page of data.  This might be the same as `prevKey` or `nextKey` in some implementations.  Null if refreshing is not supported or if this is the initial page load.
 * @property prevKey The key for the previous page.  Use this key to load the preceding page of data.  Null if there is no previous page or if this is the first page.
 * @property nextKey The key for the next page. Use this key to load the subsequent page of data.  Null if there is no next page or if this is the last page.
 * @property items The list of items in the current page.  This list may be empty.
 */
data class PagedItems<Key: Any, Entity>(
    val refreshKey: Key?,
    val prevKey: Key?,
    val nextKey: Key?,
    val items: List<Entity>,
)
