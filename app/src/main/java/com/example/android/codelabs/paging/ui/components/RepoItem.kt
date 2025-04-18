package com.example.android.codelabs.paging.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.ui.theme.AppTheme

@Composable
fun RepoItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(12.dp),
    repo: Repo
) {
    Column(
        modifier = modifier
            .padding(padding)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = repo.fullName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        repo.description?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = it,
                maxLines = 10,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Row (
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            repo.language?.let {
                Text(
                    text = stringResource(R.string.language, it),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(
                modifier = Modifier.weight(1f),
            )
            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null
            )
            Text(
                text = repo.stars.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(
                painter = painterResource(R.drawable.ic_git_branch),
                contentDescription = null
            )
            Text(
                text = repo.forks.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@PreviewLightDark
@Composable
fun RepoItemPreview() {
    val repo = remember {
        Repo(
            id = 0,
            name = "android-architecture",
            fullName = "android-architecture",
            description = "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.",
            url = "",
            stars = 30,
            forks = 30,
            language = "en-us"
        )
    }

    AppTheme {
        Surface {
            RepoItem(
                repo = repo
            )
        }
    }
}