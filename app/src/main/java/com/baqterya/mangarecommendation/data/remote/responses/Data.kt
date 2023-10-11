package com.baqterya.mangarecommendation.data.remote.responses

data class Data(
    val approved: Boolean,
    val authors: List<Author>,
    val background: String,
    val chapters: Int,
    val demographics: List<Demographic>,
    val explicitGenres: List<ExplicitGenre>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val malId: Int,
    val members: Int,
    val popularity: Int,
    val published: Published,
    val publishing: Boolean,
    val rank: Int,
    val score: Int,
    val scoredBy: Int,
    val serializations: List<Serialization>,
    val status: String,
    val synopsis: String,
    val themes: List<Theme>,
    val title: String,
    val titleEnglish: String,
    val titleJapanese: String,
    val titles: List<Title>,
    val type: String,
    val url: String,
    val volumes: Int
)