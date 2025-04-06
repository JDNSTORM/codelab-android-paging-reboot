/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

//buildscript {
//    ext.kotlin_version = '1.6.0'
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath 'com.android.tools.build:gradle:7.0.4'
//        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}

//ext {
//    compileSdkVersion = 31
//    minSdkVersion = 15
//    targetSdkVersion = 31
//    supportLibVersion = "1.4.0"
//    coreVersion = "1.7.0"
//    recyclerViewVersion = "1.2.1"
//    constraintLayoutVersion = "2.1.2"
//    materialVersion = "1.4.0"
//    lifecycleVersion = "2.4.0"
//    roomVersion = "2.3.0"
//    pagingVersion = "3.1.0"
//    retrofitVersion = "2.9.0"
//    okhttpLoggingInterceptorVersion = "4.9.0"
//    coroutines = "1.5.2"
//}
