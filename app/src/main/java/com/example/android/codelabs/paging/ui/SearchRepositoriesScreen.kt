package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.ui.components.RepoPagingList
import com.example.android.codelabs.paging.ui.models.UiAction
import com.example.android.codelabs.paging.ui.models.UiModel
import com.example.android.codelabs.paging.ui.models.UiState
import com.example.android.codelabs.paging.ui.theme.AppTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRepositoriesScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    pagingModels: LazyPagingItems<UiModel>,
    uiAction: (UiAction) -> Unit
) {
    var query by remember(uiState.query) {
        mutableStateOf(uiState.query)
    }
    val refreshState by remember(pagingModels.loadState.refresh) {
        derivedStateOf {
            pagingModels.loadState.refresh
        }
    }
    val lazyListState = rememberSaveable(uiState.query, refreshState, saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.runningReduce { previousIndex, nextIndex ->
            val distance = previousIndex.minus(nextIndex).absoluteValue
            if (distance > 10) Log.d("SearchRepositoriesScreen", "List Jumped from $previousIndex to $nextIndex")
            nextIndex
        }.stateIn(this)
    }

    /**
     * FIXME: List doesn't reset to top on query changes. e.g. Android -> Java [scroll] -> Android
     *        This is likely caused by immediately emitting cached data instead of waiting for the
     *        refresh to finish.
     *        [LazyListState] is initialized every change in query to ensure position at 0
     */
    val shouldScrollToTop by remember(uiState.hasNotScrolledForCurrentSearch, refreshState) {
        derivedStateOf {
            refreshState is LoadState.NotLoading && uiState.hasNotScrolledForCurrentSearch
        }
    }
    LaunchedEffect(shouldScrollToTop) {
        Log.d("SearchRepositoriesScreen", "ShouldScrollToTop: $shouldScrollToTop")
        if (!shouldScrollToTop) return@LaunchedEffect
        lazyListState.scrollToItem(0)
    }
    val activity = LocalActivity.current
    val hasScrolled by remember(lazyListState) {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }
    val sendScrolledEvent by rememberUpdatedState {
        uiAction(
            UiAction.Scroll(uiState.query)
        )
    }
    LaunchedEffect(hasScrolled) {
        if (hasScrolled) sendScrolledEvent()
    }
    val isListEmpty by remember(pagingModels.itemCount) {
        derivedStateOf {
            pagingModels.itemCount == 0
        }
    }
    val isLoading by remember(refreshState) {
        derivedStateOf {
            refreshState is LoadState.Loading
        }
    }
    val isIdle by remember(refreshState) {
        derivedStateOf {
            refreshState !is LoadState.Loading
        }
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val errorState by remember(refreshState) {
        derivedStateOf {
            refreshState as? LoadState.Error
        }
    }
    val hasRefreshError by remember(isIdle, isListEmpty, errorState) {
        derivedStateOf {
            errorState?.takeIf {
                isIdle && isListEmpty
            } != null
        }
    }
    LaunchedEffect(errorState) {
        val errorMessage = errorState?.error?.let {
            it.localizedMessage ?: it.message
        }?: return@LaunchedEffect
        swipeToDismissBoxState.reset()
        snackbarHostState.showSnackbar(
            message = errorMessage
        )
    }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(
            WindowInsets.systemBars.only(
                WindowInsetsSides.Bottom
            )
        ),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    SwipeToDismissBox(
                        modifier = Modifier
                            .then(
                                if (!hasScrolled) Modifier.windowInsetsPadding(
                                    WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
                                )
                                else Modifier
                            ),
                        state = swipeToDismissBoxState,
                        backgroundContent = {},
                    ) {
                        Snackbar(data)
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
                ),
                visible = hasScrolled,
                enter = fadeIn() + scaleIn() + expandVertically(),
                exit = fadeOut() + scaleOut() + shrinkVertically()
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                }
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = query,
                onValueChange = {
                    query = it
                },
                label = {
                    Text(
                        text = stringResource(R.string.search_hint)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        uiAction(
                            UiAction.Search(query)
                        )
                        focusManager.clearFocus(true)
                    }
                )
            )
            PullToRefreshBox (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isRefreshing = isLoading,
                onRefresh = pagingModels::refresh,
                contentAlignment = Alignment.Center
            ){
//                AnimatedContent(
//                    targetState = refreshState,
//                    transitionSpec = {
//                        fadeIn() togetherWith fadeOut()
//                    }
//                ) { loadState ->
//                    Box (
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ){
//                        when(loadState){
//                            is LoadState.Error -> Button(
//                                onClick = pagingModels::retry,
//                                shape = MaterialTheme.shapes.extraSmall
//                            ) {
//                                Text(
//                                    text = stringResource(R.string.retry).uppercase()
//                                )
//                            }
//                            LoadState.Loading -> CircularProgressIndicator()
//                            is LoadState.NotLoading -> if (isListEmpty) {
//                                Text(
//                                    text = stringResource(R.string.no_results),
//                                    style = MaterialTheme.typography.headlineSmall
//                                )
//                            } else {
//                                RepoPagingList(
//                                    modifier = Modifier.fillMaxSize(),
//                                    pagingModels = pagingModels,
//                                    lazyListState = lazyListState,
//                                    onRepoClick = { repo ->
//                                        activity?.let {
//                                            val intent = Intent(Intent.ACTION_VIEW, repo.url.toUri())
//                                            it.startActivity(intent)
//                                        }
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
                RepoPagingList(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        bottom = 80.dp
                    ),
                    pagingModels = pagingModels,
                    lazyListState = lazyListState,
                    onRepoClick = { repo ->
                        activity?.let {
                            val intent = Intent(Intent.ACTION_VIEW, repo.url.toUri())
                            it.startActivity(intent)
                        }
                    }
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = isListEmpty && isIdle && !hasRefreshError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = stringResource(R.string.no_results),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = hasRefreshError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(
                        onClick = pagingModels::retry,
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Text(
                            text = stringResource(R.string.retry).uppercase()
                        )
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchRepositoriesScreenPreview(
    @PreviewParameter(PagingUiModelsPreviewParameterProvider::class)
    pagingData: PagingData<UiModel>
) {
    val uiState = remember {
        UiState()
    }
    val pagingModels = remember {
        flowOf(pagingData)
    }.collectAsLazyPagingItems()
    AppTheme {
        Surface {
            SearchRepositoriesScreen(
                uiState = uiState,
                pagingModels = pagingModels
            ) { }
        }
    }
}