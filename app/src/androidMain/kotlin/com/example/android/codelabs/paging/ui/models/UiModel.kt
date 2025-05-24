package com.example.android.codelabs.paging.ui.models

import com.example.android.codelabs.paging.core.models.Repo

sealed class UiModel {
    data class RepoItem(val repo: Repo) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}