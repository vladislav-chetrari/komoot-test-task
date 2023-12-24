package com.komoot.vchetrari.challenge.data.source.interceptor

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ImageSearchClientInterceptorTest {

    private lateinit var interceptor: ImageSearchClientInterceptor

    @Before
    fun setUp() {
        interceptor = ImageSearchClientInterceptor(API_KEY)
    }

    @Test
    fun intercept() {
        val chain = mockk<Interceptor.Chain>()
        val originalRequest = mockk<Request>()
        val urlBuilder = mockk<HttpUrl.Builder>()
        val newUrl = mockk<HttpUrl>()
        val newRequest = mockk<Request>()
        val response = mockk<Response>()
        every { chain.request() } returns originalRequest
        every { urlBuilder.addQueryParameter(any(), any()) } returns urlBuilder
        every { urlBuilder.build() } returns newUrl
        every { originalRequest.url.newBuilder() } returns urlBuilder
        every { originalRequest.newBuilder().url(newUrl).build() } returns newRequest
        every { chain.proceed(newRequest) } returns response

        assertEquals(response, interceptor.intercept(chain))
        verify {
            urlBuilder.addQueryParameter("method", "flickr.photos.search")
            urlBuilder.addQueryParameter("api_key", API_KEY)
            urlBuilder.addQueryParameter("media", "photos")
            urlBuilder.addQueryParameter("has_geo", "1")
            urlBuilder.addQueryParameter("format", "json")
            urlBuilder.addQueryParameter("nojsoncallback", "1")
        }
    }

    private companion object {
        const val API_KEY = "api_key"
    }
}