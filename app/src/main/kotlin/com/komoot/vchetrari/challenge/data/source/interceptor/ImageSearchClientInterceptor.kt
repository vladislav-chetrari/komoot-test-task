package com.komoot.vchetrari.challenge.data.source.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ImageSearchClientInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter(PARAM_NAME_METHOD, PARAM_VALUE_IMAGE_SEARCH)
            .addQueryParameter(PARAM_NAME_API_KEY, apiKey)
            .addQueryParameter(PARAM_NAME_MEDIA, PARAM_VALUE_PHOTOS)
            .addQueryParameter(PARAM_NAME_HAS_GEO, PARAM_VALUE_INT_TRUE)
            .addQueryParameter(PARAM_NAME_FORMAT, PARAM_VALUE_JSON)
            .addQueryParameter(PARAM_NAME_NO_JSON_CALLBACK, PARAM_VALUE_INT_TRUE)
            .build()
        val request = originalRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

    private companion object {
        const val PARAM_NAME_METHOD = "method"
        const val PARAM_NAME_API_KEY = "api_key"
        const val PARAM_NAME_FORMAT = "format"
        const val PARAM_NAME_MEDIA = "media"
        const val PARAM_NAME_NO_JSON_CALLBACK = "nojsoncallback"
        const val PARAM_NAME_HAS_GEO = "has_geo"
        const val PARAM_VALUE_IMAGE_SEARCH = "flickr.photos.search"
        const val PARAM_VALUE_JSON = "json"
        const val PARAM_VALUE_INT_TRUE = "1"
        const val PARAM_VALUE_PHOTOS = "photos"
    }
}