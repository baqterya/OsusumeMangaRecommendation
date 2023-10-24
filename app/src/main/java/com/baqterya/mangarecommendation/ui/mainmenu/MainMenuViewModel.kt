package com.baqterya.mangarecommendation.ui.mainmenu

import androidx.lifecycle.ViewModel
import com.baqterya.mangarecommendation.data.remote.responses.Manga
import com.baqterya.mangarecommendation.repository.MangaRepository
import com.baqterya.mangarecommendation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val repository: MangaRepository
) : ViewModel() {

    suspend fun getManga(mangaId: Int) : Resource<Manga> {
        return repository.getManga(mangaId)
    }
}