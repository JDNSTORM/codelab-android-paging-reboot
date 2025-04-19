/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.android.codelabs.paging.data.GithubRepository
import com.example.android.codelabs.paging.ui.models.UiAction
import com.example.android.codelabs.paging.ui.models.UiModel
import com.example.android.codelabs.paging.ui.models.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the [SearchRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class SearchRepositoriesViewModel(
    private val repository: GithubRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val searchQueryState =
        savedStateHandle.getStateFlow(LAST_SEARCH_QUERY, UiState.DEFAULT_QUERY)
    private val lastQueryScrolledState =
        savedStateHandle.getStateFlow(LAST_QUERY_SCROLLED, UiState.DEFAULT_QUERY)

    /**
     * Stream of immutable states representative of the UI.
     */
    val uiState: StateFlow<UiState> = combine(
        searchQueryState,
        lastQueryScrolledState
    ) { query, lastQueryScrolled ->
        UiState(
            query = query,
            lastQueryScrolled = lastQueryScrolled,
            // If the search query matches the scroll query, the user has scrolled
            hasNotScrolledForCurrentSearch = query != lastQueryScrolled
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    val pagingDataFlow: Flow<PagingData<UiModel>> = searchQueryState
        .flatMapLatest { searchRepo(queryString = it) }
        .cachedIn(viewModelScope)

    /**
     * Processor of side effects from the UI which in turn feedback into [uiState]
     */
    fun uiAction(action: UiAction) {
        when (action) {
            is UiAction.Scroll -> savedStateHandle[LAST_QUERY_SCROLLED] = action.currentQuery
            is UiAction.Search -> savedStateHandle[LAST_SEARCH_QUERY] = action.query.trim()
        }
    }

    private fun searchRepo(queryString: String): Flow<PagingData<UiModel>> =
        repository.getSearchResultStream(queryString)
            .map { pagingData ->
                pagingData
                    .map { UiModel.RepoItem(it) }
                    .insertSeparators { before, after ->
                        after  // we're at the end of the list
                            ?: return@insertSeparators null
                        before // we're at the beginning of the list
                            ?: return@insertSeparators UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        // check between 2 items
                        if (before.roundedStarCount > after.roundedStarCount) {
                            if (after.roundedStarCount >= 1) {
                                UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                            } else {
                                UiModel.SeparatorItem("< 10.000+ stars")
                            }
                        } else {
                            // no separator
                            null
                        }
                    }
            }

    companion object {
        private val UiModel.RepoItem.roundedStarCount: Int
            get() = this.repo.stars / 10_000

        private const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
    }
}

