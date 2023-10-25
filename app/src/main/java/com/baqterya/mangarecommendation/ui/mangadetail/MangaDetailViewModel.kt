package com.baqterya.mangarecommendation.ui.mangadetail

import androidx.lifecycle.ViewModel
import com.baqterya.mangarecommendation.data.remote.responses.Manga
import com.baqterya.mangarecommendation.repository.MangaRepository
import com.baqterya.mangarecommendation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel  @Inject constructor(
    private val repository: MangaRepository
) : ViewModel() {

    suspend fun getMangaById(mangaId: Int) : Resource<Manga> {
        return repository.getMangaById(mangaId)
    }
}