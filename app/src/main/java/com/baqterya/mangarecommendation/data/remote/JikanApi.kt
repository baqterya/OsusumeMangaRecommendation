package com.baqterya.mangarecommendation.data.remote

import com.baqterya.mangarecommendation.data.remote.responses.Manga
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {

    @GET("manga/{id}")
    suspend fun getMangaById(
        @Path("id") id: Int
    ): Manga

}