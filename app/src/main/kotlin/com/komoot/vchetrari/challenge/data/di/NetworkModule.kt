package com.komoot.vchetrari.challenge.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.komoot.vchetrari.challenge.BuildConfig
import com.komoot.vchetrari.challenge.data.source.interceptor.ImageSearchClientInterceptor
import com.komoot.vchetrari.challenge.data.source.PhotoSearchClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    internal fun photoSearchClient(
        @ApplicationContext context: Context
    ) = Retrofit.Builder()
        .baseUrl(BuildConfig.flickr_base_url)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(ImageSearchClientInterceptor(BuildConfig.flickr_api_key))
                .addInterceptor(ChuckerInterceptor(context))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<PhotoSearchClient>()
}