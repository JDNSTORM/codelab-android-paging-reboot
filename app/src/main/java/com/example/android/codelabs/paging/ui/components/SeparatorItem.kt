package com.example.android.codelabs.paging.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.android.codelabs.paging.ui.theme.AppTheme

@Composable
fun SeparatorItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(12.dp),
    description: String,
) {
    Text (
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(padding),
        text = description,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@PreviewLightDark
@Composable
fun SeparatorItemPreview() {
    AppTheme {
        SeparatorItem(
            description = "10000+ stars"
        )
    }
}