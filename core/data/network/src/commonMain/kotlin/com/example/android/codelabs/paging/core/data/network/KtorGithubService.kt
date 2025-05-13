package com.example.android.codelabs.paging.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class KtorGithubService(
    private val client: HttpClient
): GithubService {
    override suspend fun searchRepos(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): RepoSearchResponse = client.get("search/repositories?sort=stars"){
        url {
            parameters.apply {
                append("q", query + IN_QUALIFIER)
                append("page", page.toString())
                append("per_page", itemsPerPage.toString())
            }
        }
    }.body()
}