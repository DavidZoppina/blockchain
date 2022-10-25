/*
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 26
    const val compileSdk = 32
    const val targetSdk = 31
    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
}

object Versions {
    const val kotlin = "1.6.20"
    const val build_gradle = "7.1.1"
    const val google_service = "4.3.10"
    const val ktlint = "9.2.1"
    const val ben_mane_gralde = "0.28.0"

    const val androidx_leanback = "1.1.0-alpha05"    //from 3-5  (version 6-dataRefreshFlow / dataRefreshListener APIs have been removed)
    const val androidx_core = "1.6.0"
    const val androidx_fragment = "1.2.2"
    const val androidx_lifecycle = "2.3.1"
    const val androidx_recommendation = "1.0.0"
    const val androidx_tvprovider = "1.0.0"
    const val androidx_room = "2.2.5"
    const val androidx_work = "2.3.2"

    const val dagger = "2.41"
    const val dagger_compile = "2.41"
    const val retrofit = "2.9.0"
    const val retrofit_converter_gson = "2.9.0"
    const val okhttp = "4.9.1"
    const val logging_interceptor = "4.9.3"
    const val gson = "2.9.0"
    const val retrofit2_rxjava2_adapter = "1.0.0"
    const val timber = "4.7.1"
    const val firebase = "20.1.0"
    const val glide = "4.11.0"
    const val lottie = "3.4.0"
    const val rxandroid = "2.1.1"
    const val junit = "4.13"
    const val mockito_inline = "3.3.3"
    const val mockito_core = "3.3.3"
    const val mockito_kotlin = "2.1.0"
    const val jetbrains ="1.6.20"
    const val exoplayer = "2.17.1"
    const val multidex = "1.0.3"
    const val lifecycle_process = "2.2.0"
    const val google_play_service = "20.2.0"
    const val facebook_login = "4.5"
    const val kotlin_coroutines_version =  "1.3.5"
    const val facebook_sdk = "latest.release"
    const val twitter_coroutines = "3.3.0"

}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val build_gradle = "com.android.tools.build:gradle:${Versions.build_gradle}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val google_services = "com.google.gms:google-services:${Versions.google_service}"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    const val ben_mane_gralde = "com.github.ben-manes:gradle-versions-plugin:${Versions.ben_mane_gralde}"


    const val androidx_leanback = "androidx.leanback:leanback:${Versions.androidx_leanback}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:${Versions.androidx_fragment}"
    const val androidx_lifecycle = "androidx.lifecycle:lifecycle-viewmodel:${Versions.androidx_lifecycle}"
    const val androidx_recommendation = "androidx.recommendation:recommendation:${Versions.androidx_recommendation}"
    const val androidx_tvprovider = "androidx.tvprovider:tvprovider:${Versions.androidx_tvprovider}"
    const val androidx_room = "androidx.room:room-runtime:${Versions.androidx_room}"
    const val androidx_room_rxjava = "androidx.room:room-rxjava2:${Versions.androidx_room}"
    const val androidx_room_ktx = "androidx.room:room-ktx:${Versions.androidx_room}"
    const val androidx_room_compiler = "androidx.room:room-compiler:${Versions.androidx_room}"
    const val androidx_work = "androidx.work:work-runtime-ktx:${Versions.androidx_work}"
    const val androidx_work_rxjava2 = "androidx.work:work-rxjava2:${Versions.androidx_work}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_compile = "com.google.dagger:dagger-compiler:${Versions.dagger_compile}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit2_rxjava2_adapter = "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.retrofit2_rxjava2_adapter}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val firebase = "com.google.firebase:firebase-analytics:${Versions.firebase}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito_inline}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
    const val jetbrains = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val exoplayer_hls = "com.google.android.exoplayer:exoplayer-hls:${Versions.exoplayer}"
    const val exoplayer_core = "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayer}"
    const val exoplayer_dash = "com.google.android.exoplayer:exoplayer-dash:${Versions.exoplayer}"
    const val exoplayer_ui = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayer}"
    const val multidex = "com.android.support:multidex:${Versions.multidex}"
    const val lifecycle_process = "androidx.lifecycle:lifecycle-process:lifecycle_process${Versions.lifecycle_process}"
    const val google_signin = "com.google.android.gms:play-services-auth:${Versions.google_play_service}"
   // const val facebook_signin = "com.facebook.android:facebook-login:${Versions.facebook_login}"
//    const val twitter_signin = "com.twitter.sdk.android:twitter:${Versions.twitter_coroutines}"
//    const val twitter_mopub = "com.twitter.sdk.android:twitter-mopub:${Versions.twitter_coroutines}"
//    const val twitter_ui = "com.twitter.sdk.android:twitter-ui:${Versions.twitter_coroutines}"
//    const val twitter_coroutines = "com.twitter.sdk.android:twitter-core:${Versions.twitter_coroutines}"

    const val facebook_sdk="com.facebook.android:facebook-android-sdk:${Versions.facebook_sdk}"
}