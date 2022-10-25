package com.andtv.flicknplay.data.apiAgents

import com.andtv.flicknplay.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val USER_AGENT = "User-Agent"

class UserAgentInterceptor : Interceptor {

    private val  userAgent = "Android"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithUserAgent = request.newBuilder()
            .url(BuildConfig.TMDB_BASE_URL)
            .header(USER_AGENT, userAgent)
            .build()

        return chain.proceed(requestWithUserAgent)
    }
}