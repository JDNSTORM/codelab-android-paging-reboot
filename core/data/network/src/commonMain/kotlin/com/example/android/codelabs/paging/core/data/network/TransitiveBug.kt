package com.example.android.codelabs.paging.core.data.network

import com.example.android.codelabs.paging.core.common.CommonModule
import com.example.android.codelabs.paging.core.models.transitiveCommon
import kotlinx.datetime.Clock

fun transitiveBug(){
    transitiveCommon()
    CommonModule()
    Clock.System.now()
}