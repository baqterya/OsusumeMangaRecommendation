package com.baqterya.mangarecommendation.ui.mangadetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.baqterya.mangarecommendation.R
import com.baqterya.mangarecommendation.ui.mainmenu.ReadingProgress
import com.baqterya.mangarecommendation.ui.mainmenu.RecommendationTile


@Composable
fun MangaDetailScreen(
    navController: NavController,
//    dominantColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF98d21c))
    ) {
        MangaDetailTopSection(navController, Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .align(Alignment.TopCenter)
        )
        ConstraintLayout(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 80.dp, start = 18.dp, end = 18.dp)
        ) {
            val (cover, params, progress, spacer, synopsis, tags) = createRefs()
            MangaDetailCover(modifier = Modifier
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
                .constrainAs(params) {
                    end.linkTo(parent.end)
                    bottom.linkTo(cover.bottom)
                    start.linkTo(spacer.end)
                }
            ) {
                MangaDetailParamSection()
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
            Synopsis(modifier = Modifier
                .padding(top = 25.dp)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(synopsis) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(progress.bottom)
                }
            )
            TagChipContainer(
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
fun Synopsis(modifier: Modifier) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .height(250.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "In the American Old West, the world's greatest race is about to begin. Thousands line up in San Diego to travel over six thousand kilometers for a chance to win the grand prize of fifty million dollars. With the era of the horse reaching its end, contestants are allowed to use any kind of vehicle they wish. Competitors will have to endure grueling conditions, traveling up to a hundred kilometers a day through uncharted wastelands. The Steel Ball Run is truly a one-of-a-kind event.\n" +
                "\n" +
                "The youthful Johnny Joestar, a crippled former horse racer, has come to San Diego to watch the start of the race. There he encounters Gyro Zeppeli, a racer with two steel balls at his waist instead of a gun. Johnny witnesses Gyro using one of his steel balls to unleash a fantastical power, compelling a man to fire his gun at himself during a duel. In the midst of the action, Johnny happens to touch the steel ball and feels a power surging through his legs, allowing him to stand up for the first time in two years. Vowing to find the secret of the steel balls, Johnny decides to compete in the race, and so begins his bizarre adventure across America on the Steel Ball Run.\n" +
                "\n" +
                "[Written by MAL Rewrite]",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun MangaDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
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
        Text(
            text = "Steel Ball Run",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 21.dp)
        )
    }
}


@Composable
fun MangaDetailCover(modifier: Modifier) {
    Box(
        modifier = modifier
            .clickable { }
    ) {
        SubcomposeAsyncImage(
            model = R.drawable.dummy_cover,
            contentDescription = "Steel Ball Run",
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
fun MangaDetailParamSection() {
    val fontSize = 16.sp
    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            Text(
                text = "Ranking",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "Score",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "Author",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "Chapters",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "Status",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            Text(
                text = "#2",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "9.30",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "Araki, Hirohiko",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "96",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
            Text(
                text = "Finished",
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun TagChipContainer(modifier: Modifier) {
    val genreColor = MaterialTheme.colorScheme.primary
    val themeColor = MaterialTheme.colorScheme.primaryContainer
    val demographicsColor = MaterialTheme.colorScheme.tertiary
    val testList = listOf(
        "Action",
        "Adventure",
        "Mystery",
        "Supernatural",
        "Historical",
        "Seinen",
        "Shonen",
    )
    LazyRow(
        contentPadding = PaddingValues(15.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .height(180.dp)
    ) {
        items(7) {
            TagChip(testList[it], themeColor, MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}


@Composable
fun TagChip(tag: String, typeColor: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(typeColor)
            .padding(10.dp)
    ){ Text(text = tag, color = textColor) }
}