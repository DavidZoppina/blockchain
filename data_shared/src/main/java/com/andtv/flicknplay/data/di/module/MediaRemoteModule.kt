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

package com.andtv.flicknplay.data.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.andtv.flicknplay.data.BuildConfig
import com.andtv.flicknplay.data.Constants
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 */
private const val TIME_OUT_SEC = 60L

@Module
class MediaRemoteModule {
    @Provides
    @Singleton
    @Named("flickbaseurl")
    fun getClient(): Retrofit {
        return getClient(BuildConfig.FLICK_BASE_URL, null, null)
    }

    fun getClientBasic(baseUrl: String?, accessToken: String?): Retrofit = getClient(
        baseUrl,
        accessToken,
        "Basic"
    )

    fun getClientBearer(baseUrl: String?, accessToken: String?): Retrofit = getClient(
        baseUrl,
        accessToken,
        "Bearer"
    )

    private fun getClient(
        baseUrl: String?,
        accessToken: String?,
        authenticationType: String?
    ): Retrofit {
        val accessTokenParam = if (authenticationType == null || authenticationType == "Bearer")
            "Bearer $accessToken"
        else "Basic $accessToken"
        val client = OkHttpClient.Builder()
            .connectTimeout(360, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val ongoing = chain.request().newBuilder()
                ongoing.addHeader("Accept", "application/json;versions=1")
                if (authenticationType != null) {
                    ongoing.addHeader("Authorization", accessTokenParam)
                }
                chain.proceed(ongoing.build())
            }

            .build()
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl!!)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("whoIsWatching")
    fun provideWhoIsWatching(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.BUILD_TYPE == "debug") {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            addInterceptor {
                it.proceed(it.request().newBuilder().addHeader("Authorization", "Bearer "+Constants.ACCESS_TOKEN).build())
            }

            readTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.FLICKNPLAY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    @Named("tmdbRetrofit")
    fun provideTmdbRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.BUILD_TYPE == "debug") {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            readTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

//    password&quot;:&quot;Holloway1&quot;,&quot;rememberMe&quot;:true,&quot;username&quot;:&quot;christopher.j.holloway@outlook.com

    @Provides
    @Singleton
    @Named("flicknplayRetrofit")
    fun provideFlicknplayRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.BUILD_TYPE == "debug") {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                })
            }
            addInterceptor {
                it.proceed(it.request().newBuilder().addHeader("Authorization", "Bearer "+Constants.ACCESS_TOKEN)
                    .addHeader("USER-AGENT", "PostmanRuntime/7.29.0")
                                .build())
            }
//            addInterceptor (UserAgentInterceptor() )

            readTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
        }
//            .addInterceptor(UserAgentInterceptor() )
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.FLICKNPLAY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("tmdbApiKey")
    fun provideTmdbApiKey() =
            BuildConfig.TMDB_API_KEY

    @Provides
    @Singleton
    @Named("tmdbFilterLanguage")
    fun provideTmdbFilterLanguage() =
        BuildConfig.TMDB_FILTER_LANGUAGE

    @Provides
    @Singleton
    @Named("flicknplayFilterLanguage")
    fun provideFlicknplayFilterLanguage() =
        BuildConfig.FLICKNPLAY_FILTER_LANGUAGE

    @Provides
    @Singleton
    @Named("flicknplayFilterCountry")
    fun provideFlicknplayFilterCountry() =
        BuildConfig.FLICKNPLAY_FILTER_COUNTRY
}
