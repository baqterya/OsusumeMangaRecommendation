package com.baqterya.mangarecommendation.ui.mangadetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.baqterya.mangarecommendation.data.remote.responses.Data
import com.baqterya.mangarecommendation.data.remote.responses.Manga
import com.baqterya.mangarecommendation.ui.mainmenu.ReadingProgress
import com.baqterya.mangarecommendation.util.AutoResizeText
import com.baqterya.mangarecommendation.util.FontSizeRange
import com.baqterya.mangarecommendation.util.Resource
import com.baqterya.mangarecommendation.util.authorsToString
import com.baqterya.mangarecommendation.util.MangaTag


@Composable
fun MangaDetailScreen(
    navController: NavController,
    dominantColor: Color,
    mangaId: Int,
    viewModel: MangaDetailViewModel = hiltViewModel()
) {
    val mangaInfo = produceState<Resource<Manga>>(initialValue = Resource.Loading()) {
        value = viewModel.getMangaById(mangaId)
    }.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
    ) {
        MangaDetailStateWrapper(
            mangaInfo = mangaInfo,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            navController = navController,
        )
    }
}

@Composable
fun MangaDetailStateWrapper(
    mangaInfo: Resource<Manga>,
    modifier: Modifier,
    loadingModifier: Modifier,
    navController: NavController,
) {
    when(mangaInfo) {
        is Resource.Success -> {
            MangaDetailSection(
                mangaInfo.data!!,
                navController
            )
        }
        is Resource.Error -> {
            Text(
                text = mangaInfo.message.toString(),
                color = MaterialTheme.colorScheme.error,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun MangaDetailSection(
    manga: Manga,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MangaDetailTopSection(
            navController,
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter),
            manga.data.title,
        )
        ConstraintLayout(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 80.dp, start = 18.dp, end = 18.dp)
        ) {
            val (cover, params, progress, spacer, synopsis, tags) = createRefs()
            MangaDetailCover(
                imageUrl = manga.data.images.jpg.image_url,
                mangaTitle = manga.data.title,
                modifier = Modifier
                .constrainAs(cover) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )
            Spacer(modifier = Modifier
                .width(20.dp)
                .constrainAs(spacer) { start.linkTo(cover.end) })
            Box(modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .widthIn(0.dp, 220.dp)
                .heightIn(0.dp, 200.dp)
                .constrainAs(params) {
                    end.linkTo(parent.end)
                    bottom.linkTo(cover.bottom)
                    start.linkTo(spacer.end)
                }
            ) {
                MangaDetailParamSection(manga.data)
            }
            ReadingProgress(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(18.dp)
                    .constrainAs(progress) {
                        start.linkTo(cover.start)
                        end.linkTo(params.end)
                        top.linkTo(cover.bottom)
                        width = Dimension.fillToConstraints
                    },
                currentChapter = 43,
                totalChapters = 96
            )
            Synopsis(
                manga.data.synopsis,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .constrainAs(synopsis) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(progress.bottom)
                    }
            )
            TagChipContainer(
                manga.data,
                modifier = Modifier
                    .constrainAs(tags) {
                        start.linkTo(synopsis.start)
                        end.linkTo(synopsis.end)
                        top.linkTo(synopsis.bottom)
                    }
            )
        }
    }
}

@Composable
fun Synopsis(
    synopsisText: String,
    modifier: Modifier,
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .height(250.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = synopsisText,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun MangaDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
    mangaTitle: String
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(listOf(
                Color.Black,
                Color.Transparent,
            )))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        AutoResizeText(
            text = mangaTitle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSizeRange = FontSizeRange(14.sp, 22.sp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 21.dp)
        )
    }
}

@Composable
fun MangaDetailCover(
    imageUrl: String,
    mangaTitle: String,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .clickable { }
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = mangaTitle,
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillHeight,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.5f)
                )
            },
        )
    }
}

@Composable
fun MangaDetailParamSection(
    mangaData: Data
) {
    val defaultFontSize = 16.sp
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(5.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AutoResizeText(
            text = "Ranking: #${mangaData.rank}",
            maxLines = 2,
            fontSizeRange = FontSizeRange(11.sp, defaultFontSize),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
        AutoResizeText(
            text = "Score: ${mangaData.score}",
            maxLines = 2,
            fontSizeRange = FontSizeRange(11.sp, defaultFontSize),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
        AutoResizeText(
            text = "Author: ${authorsToString(mangaData.authors)}",
            maxLines = 2,
            fontSizeRange = FontSizeRange(11.sp, defaultFontSize),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
        AutoResizeText(
            text = "Chapters: ${mangaData.chapters}",
            maxLines = 2,
            fontSizeRange = FontSizeRange(11.sp, defaultFontSize),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
        AutoResizeText(
            text = "Status: ${mangaData.status}",
            maxLines = 2,
            fontSizeRange = FontSizeRange(11.sp, defaultFontSize),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun TagChipContainer(
    mangaData: Data,
    modifier: Modifier
) {
    val tagList = mutableListOf<MangaTag>()
    for (demographic in mangaData.demographics) {
        tagList.add(MangaTag(
            demographic.name,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        ))
    }
    for (genre in mangaData.genres) {
        tagList.add(MangaTag(
            genre.name,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.onSecondary
        ))
    }
    for (theme in mangaData.themes) {
        tagList.add(MangaTag(
            theme.name,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onTertiary
        ))
    }
    LazyRow(
        contentPadding = PaddingValues(15.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .height(180.dp)
    ) {
        items(tagList.size) {index ->
            TagChip(tagList[index].tagText,
                tagList[index].backgroundColor,
                tagList[index].textColor
            )
        }
    }
}


@Composable
fun TagChip(tag: String, typeColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .border(2.5.dp, Color.Black)
            .background(typeColor)
            .padding(10.dp)
    ){
        Text(text = tag, color = textColor)
    }
}
