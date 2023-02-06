package com.dandev.storyapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dandev.storyapp.BuildConfig
import com.dandev.storyapp.data.local.preference.AuthDataStoreManager
import com.dandev.storyapp.data.remote.service.AuthApiService
import com.dandev.storyapp.data.remote.service.StoryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideChucker(@ApplicationContext appContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(appContext).build()
    }

    @Provides
    @Singleton
    fun provideAuthDataStoreManager(@ApplicationContext context: Context): AuthDataStoreManager {
        return AuthDataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoryApiService(retrofit: Retrofit): StoryApiService {
        return retrofit.create(StoryApiService::class.java)
    }

}