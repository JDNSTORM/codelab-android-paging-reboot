package com.example.android.codelabs.paging.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.ui.models.UiModel
import kotlin.random.Random
import kotlin.random.nextInt

class PagingUiModelsPreviewParameterProvider: PreviewParameterProvider<PagingData<UiModel>> {
    private val uiModels: List<UiModel> = List(100){
        UiModel.RepoItem(
            repo = Repo(
                id = it.toLong(),
                name = "Repo Item $it",
                fullName = "Repo Item $it",
                description = "Repo Description $it",
                url = "",
                stars = Random.nextInt(0..Int.MAX_VALUE),
                forks = Random.nextInt(0..Int.MAX_VALUE),
                language = null
            )
        )
    }.sortedByDescending { it.repo.stars }

    override val values: Sequence<PagingData<UiModel>>
        get() = sequenceOf(
            PagingData.empty(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            ),
            PagingData.empty(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            ),
            PagingData.empty(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(
                        Exception("Cannot Load List")
                    ),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            ),
            PagingData.from(
                data = uiModels,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            ),
            PagingData.from(
                data = uiModels,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            ),
            PagingData.from(
                data = uiModels,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(
                        Exception("Cannot Load List")
                    ),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            ),
        )
}