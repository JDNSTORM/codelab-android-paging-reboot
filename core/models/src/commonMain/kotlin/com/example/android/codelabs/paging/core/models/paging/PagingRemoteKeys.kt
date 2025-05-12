package com.example.android.codelabs.paging.core.models.paging

/**
 * An interface for an `Entity` representing the remote keys for a paginated data set.
 * Remote keys are used to keep track of the previous, current, and next pages of data
 * that can be fetched from a remote source (e.g., an API).
 *
 * This interface is typically used within a database table that stores remote keys
 * associated with items in a paginated list. Each item or a group of items (depending on your
 * pagination strategy) in the list would likely have a corresponding entry in the remote keys table.
 * This allows efficient retrieval of the previous and next pages of data, as well as
 * maintaining consistency with the order of the paginated data.
 *
 * @param Key The type of the pagination key, typically representing a page number,
 *            an offset, or a cursor. Must be serializable.
 * @property refreshKey The key used to refresh the entire dataset. This is often the initial key
 *                     or a specific key that represents the starting point for a full refresh.
 * @property nextKey The key for the next page of data.  Null if there is no
 *                   next page (e.g., the last page has been reached).
 * @property position An identifier in which item associated with these keys are positioned.
 *                    This is used to maintain consistency with the order of the paginated data.
 */
interface PagingRemoteKeys<Key: Any>{
    val refreshKey: Key?
    val prevKey: Key?
    val nextKey: Key?
//    val position: Int
}