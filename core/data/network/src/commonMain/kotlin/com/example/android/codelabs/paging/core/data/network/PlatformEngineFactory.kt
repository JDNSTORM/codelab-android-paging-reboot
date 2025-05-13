package com.example.android.codelabs.paging.core.data.network

import io.ktor.client.engine.HttpClientEngineFactory

internal expect val PlatformEngineFactory: HttpClientEngineFactory<*>