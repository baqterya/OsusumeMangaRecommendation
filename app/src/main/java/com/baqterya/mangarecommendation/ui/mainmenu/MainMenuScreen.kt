package com.baqterya.mangarecommendation.ui.mainmenu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.baqterya.mangarecommendation.R
import coil.compose.SubcomposeAsyncImage
import com.baqterya.mangarecommendation.data.remote.responses.Manga
import com.baqterya.mangarecommendation.util.Resource
import com.baqterya.mangarecommendation.util.calcDominantColor

@Composable
fun MainMenuScreen(
    navController: NavController,
    viewModel: MainMenuViewModel = hiltViewModel()
) {
    val weeklyChallengeManga = produceState<Resource<Manga>>(initialValue = Resource.Loading()) {
        value = viewModel.getMangaById(1)
    }.value
    val personalChallengeManga = produceState<Resource<Manga>>(initialValue = Resource.Loading()) {
        value = viewModel.getMangaById(3)
    }.value

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Logo()
            //weekly
            ChallengeTileWrapper(
                tileTitle = "Weekly Challenge",
                mangaInfo = weeklyChallengeManga,
                currentChapter = 33,
                navController = navController
            )
            //personal
            ChallengeTileWrapper(
                tileTitle = "Personal Challenge",
                mangaInfo = personalChallengeManga,
                currentChapter = 311,
                navController = navController
            )
            RecommendationsContainer()
        }
    }
}

@Composable
fun Logo() {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_no_background),
                contentDescription = "Osusume Logo",
                contentScale = ContentScale.Fit
            )

            val dummyText = "13"
            Text(
                text = dummyText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 35.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.fire),
                contentDescription = "Streak Icon",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 5.dp, bottom = 5.dp)
            )

        }
    }
}

@Composable
fun ChallengeTileWrapper(
    tileTitle: String,
    mangaInfo: Resource<Manga>,
    currentChapter: Int,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    navController: NavController,
) {
    when(mangaInfo) {
        is Resource.Success -> {
            ChallengeTile(
                tileTitle,
                mangaInfo.data!!,
                currentChapter,
                navController,
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
fun ChallengeTile(
    tileTitle: String,
    manga: Manga,
    currentChapter: Int,
    navController: NavController,
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surfaceVariant
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    ConstraintLayout(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        defaultDominantColor,
                        dominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "manga_detail_screen/${dominantColor.toArgb()}/${manga.data.mal_id}"
                )
            }
    ) {
        val (progressBar, coverImage, topText, chapterCounterText) = createRefs()

        Text(
            text = tileTitle,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(topText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        SubcomposeAsyncImage(
            model = manga.data.images.jpg.image_url,
            contentDescription = manga.data.title,
            modifier = Modifier
                .height(170.dp)
                .alpha(0.8f)
                .constrainAs(coverImage) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            onSuccess = {
                calcDominantColor(it.result.drawable) { color ->
                    dominantColor = color
                }
            },
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.5f)
                )
            },
        )
        Text(
            text = "${currentChapter}/${manga.data.chapters}",
            fontSize = 17.sp,
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(chapterCounterText) {
                    bottom.linkTo(progressBar.top)
                    start.linkTo(parent.start)
                }
        )
        ReadingProgress(
            currentChapter = currentChapter,
            totalChapters = manga.data.chapters,
            modifier = Modifier
                .padding(end = 10.dp)
                .height(12.dp)
                .constrainAs(progressBar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(coverImage.start)
                    width = Dimension.fillToConstraints
                }
        )
    }
}


@Composable
fun ReadingProgress(
    modifier: Modifier,
    currentChapter: Int,
    totalChapters: Int
) {
    if (totalChapters == 0) return
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) currentChapter/totalChapters.toFloat() else 0f,
        animationSpec = tween(1000, 0),
        label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(currentPercent.value)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ){}
    }
}

@Composable
fun RecommendationsContainer() {
    Text(text = "More Recommendations", fontSize = 22.sp, modifier = Modifier.padding(start=20.dp, top=20.dp))
    LazyRow(
        contentPadding = PaddingValues(15.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val testList = listOf(
            listOf("Rave", "https://cdn.myanimelist.net/images/manga/3/255624.jpg"),
            listOf("Ranma ½", "https://cdn.myanimelist.net/images/manga/1/156534.jpg"),
            listOf("Pita-Ten", "https://cdn.myanimelist.net/images/manga/3/191236.jpg"),
            listOf("Le Chevalier d'Eon", "https://cdn.myanimelist.net/images/manga/1/1068l.jpg"),
            listOf("Gakuen Heaven", "https://cdn.myanimelist.net/images/manga/3/5199.jpg"),
        )
        items(5) {
            RecommendationTile(testList[it][0], testList[it][1])
        }
    }
}

@Composable
fun RecommendationTile(mangaTitle: String, coverArt: Any) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(160.dp)
            .clickable { }
    ) {
        SubcomposeAsyncImage(
            model = coverArt,
            contentDescription = mangaTitle,
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.scale(0.5f)
                )
            },
        )
    }
}
