package com.baqterya.mangarecommendation.di

import com.baqterya.mangarecommendation.data.remote.JikanApi
import com.baqterya.mangarecommendation.repository.MangaRepository
import com.baqterya.mangarecommendation.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMangaRepository(
        api: JikanApi
    ) = MangaRepository(api)

    @Singleton
    @Provides
    fun provideJikanApi(): JikanApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(JikanApi::class.java)
    }
}