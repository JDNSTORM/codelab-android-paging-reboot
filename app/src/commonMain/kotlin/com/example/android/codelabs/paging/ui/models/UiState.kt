package com.example.android.codelabs.paging.ui.models

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
){
    companion object{
        const val DEFAULT_QUERY = "Android"
    }
}