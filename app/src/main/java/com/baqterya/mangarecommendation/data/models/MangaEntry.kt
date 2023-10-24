package com.baqterya.mangarecommendation.data.models

import com.baqterya.mangarecommendation.data.remote.responses.Author
import com.baqterya.mangarecommendation.data.remote.responses.Demographic
import com.baqterya.mangarecommendation.data.remote.responses.Genre
import com.baqterya.mangarecommendation.data.remote.responses.Theme

data class MangaEntry (
    val mangaTitle: String,
    val mangaCoverUrl: String,
    val mangaScore: Float,
    val mangaAuthors: List<Author>,
    val mangaChapters: Int = 0,
    val mangaRank: Int,
    val mangaStatus: String,
    val synopsis: String,
    val mangaGenres: List<Genre>,
    val themes: List<Theme>,
    val demographics: List<Demographic>,
)