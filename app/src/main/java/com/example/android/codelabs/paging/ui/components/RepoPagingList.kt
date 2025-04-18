package com.example.android.codelabs.paging.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.ui.models.UiModel
import java.util.UUID

@Composable
fun RepoPagingList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    pagingModels: LazyPagingItems<UiModel>,
    lazyListState: LazyListState = rememberLazyListState(),
    onRepoClick: (Repo) -> Unit
) {
    val isAppending by remember(pagingModels.loadState.append) {
        derivedStateOf {
            pagingModels.loadState.append !is LoadState.NotLoading
        }
    }
    val appendingKey = rememberSaveable {
        UUID.randomUUID().toString()
    }
    LazyColumn (
        modifier = modifier,
        state = lazyListState,
        contentPadding = contentPadding
    ){
        pagingModels.itemSnapshotList.forEach { model ->
            when(model){
                is UiModel.RepoItem -> item(
                    key = model.repo.id
                ) {
                    Column (
                        modifier = Modifier.animateItem()
                    ){
                        RepoItem(
                            modifier = Modifier
                                .clickable {
                                    onRepoClick(model.repo)
                                },
                            repo = model.repo
                        )
                        HorizontalDivider()
                    }
                }
                is UiModel.SeparatorItem -> stickyHeader(
                    key = model.description
                ) {
                    SeparatorItem(
                        modifier = Modifier.animateItem(),
                        description = model.description
                    )
                }
                null -> item {
                    RepoItem(repo = repoPlaceholder)
                }
            }
        }
        if (isAppending) item(
            key = appendingKey
        ) {
            LoadStateFooterItem(
                modifier = Modifier.animateItem(),
                state = pagingModels.loadState.append,
                retry = pagingModels::retry
            )
        }
        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)
            )
        }
    }
}

private val repoPlaceholder = Repo(
    id = 0,
    name = "...",
    fullName = "",
    description = null,
    url = "",
    stars = 0,
    forks = 0,
    language = null
)