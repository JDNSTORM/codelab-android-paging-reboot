package com.example.android.codelabs.paging.core.data.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

internal actual val PlatformEngineFactory: HttpClientEngineFactory<*> = CIO