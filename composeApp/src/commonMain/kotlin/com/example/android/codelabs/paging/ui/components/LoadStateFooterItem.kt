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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import githubrepoviewer.composeapp.generated.resources.Res
import githubrepoviewer.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
                            text = stringResource(Res.string.retry).uppercase()
                        )
                    }
                }
                LoadState.Loading -> CircularProgressIndicator()
                else -> Unit
            }
        }
    }
}

@Preview
@Composable
expect fun LoadStateFooterItemPreview()