package com.example.android.codelabs.paging.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.core.designsystem.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun LoadStateFooterItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(8.dp),
    state: LoadState,
    retry: () -> Unit
) {
    AnimatedContent(
        modifier = modifier
            .animateContentSize(),
        targetState = state,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) { loadState ->
        if (loadState is LoadState.NotLoading) return@AnimatedContent
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            when(loadState){
                is LoadState.Error -> {
                    Text(
                        text = loadState.error.localizedMessage ?: loadState.error.message ?: "Error occurred",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Button(
                        onClick = retry,
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Text(
                            text = stringResource(R.string.retry).uppercase()
                        )
                    }
                }
                LoadState.Loading -> CircularProgressIndicator()
                else -> Unit
            }
        }
    }
}

@PreviewLightDark
@Composable
fun LoadStateFooterItemPreview() {
    val errorState = remember {
        LoadState.Error(
            Exception("Request Timeout")
        )
    }
    var isLoading by remember { 
        mutableStateOf(true)
    }
    LaunchedEffect(isLoading) {
        if (!isLoading) return@LaunchedEffect
        delay(3000)
        isLoading = false
    }
    val loadState by remember {
        derivedStateOf {
            if(isLoading) LoadState.Loading
            else errorState
        }
    }
    AppTheme {
        Surface {
            Column {
                LoadStateFooterItem(
                    state = loadState
                ) {
                    isLoading = true
                }
                LoadStateFooterItem(
                    state = LoadState.Loading
                ) { }
                LoadStateFooterItem(
                    state = errorState
                ) { }
            }
        }
    }
}