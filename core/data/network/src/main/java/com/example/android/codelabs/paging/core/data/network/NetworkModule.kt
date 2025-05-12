package com.example.android.codelabs.paging.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android){
            expectSuccess = true
            install(ContentNegotiation){
                json(
                    Json {
                        encodeDefaults = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                url(GithubService.BASE_URL)
            }
        }
    }
    single<GithubService> {
        KtorGithubService(
            client = get()
        )
    }
}