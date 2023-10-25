package com.baqterya.mangarecommendation.repository

import com.baqterya.mangarecommendation.data.remote.JikanApi
import com.baqterya.mangarecommendation.data.remote.responses.Manga
import com.baqterya.mangarecommendation.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MangaRepository @Inject constructor(
    private val api: JikanApi
) {

    suspend fun getMangaById(mangaId: Int): Resource<Manga> {
        val response = try {
            api.getMangaById(mangaId)
        } catch (e: Exception) {
            return Resource.Error(message="An unknown error occurred while fetching data.")
        }
        return Resource.Success(response)
    }

}