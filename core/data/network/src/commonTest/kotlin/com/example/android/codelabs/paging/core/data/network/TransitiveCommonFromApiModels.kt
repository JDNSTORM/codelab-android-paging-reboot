package com.example.android.codelabs.paging.core.data.network

import com.example.android.codelabs.paging.core.common.CommonModule
import com.example.android.codelabs.paging.core.models.transitiveCommon
import kotlinx.datetime.Clock

fun transitiveCommonFromApiModels(){
    transitiveCommon()
    CommonModule()
    Clock.System.now()
}