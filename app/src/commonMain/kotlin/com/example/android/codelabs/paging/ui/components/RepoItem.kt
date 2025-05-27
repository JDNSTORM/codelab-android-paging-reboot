package com.example.android.codelabs.paging.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.codelabs.paging.core.models.Repo
import githubrepoviewer.app.generated.resources.Res
import githubrepoviewer.app.generated.resources.ic_git_branch
import githubrepoviewer.app.generated.resources.ic_star
import githubrepoviewer.app.generated.resources.language
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RepoItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(12.dp),
    repo: Repo
) {
    val description by remember(repo) {
        derivedStateOf {
            repo.description?.take(1000)
        }
    }
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
        description?.let {
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
                    text = stringResource(Res.string.language, it),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(
                modifier = Modifier.weight(1f),
            )
            Icon(
                painter = painterResource(Res.drawable.ic_star),
                contentDescription = null
            )
            Text(
                text = repo.stars.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Icon(
                painter = painterResource(Res.drawable.ic_git_branch),
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

@Preview
@Composable
expect fun RepoItemPreview()