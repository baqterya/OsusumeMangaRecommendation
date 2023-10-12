package com.baqterya.mangarecommendation.ui.mainmenu

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.baqterya.mangarecommendation.R
import coil.compose.SubcomposeAsyncImage

@Composable
fun MainMenuScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Logo()
            WeeklyChallenge()
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

            val text = "13" //TODO MAKE INTO MUTABLE STATE
            Text(
                text = text,
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
fun WeeklyChallenge() {
    val defaultDominantColor = MaterialTheme.colorScheme.surfaceVariant
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    ConstraintLayout(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
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
            .clickable { }
    ) {
        val (progressBar, coverImage, topText) = createRefs()

        Text(
            text = "Weekly Challenge",
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp).constrainAs(topText){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        SubcomposeAsyncImage(
            model = R.drawable.dummy_cover,
            contentDescription = "Steel Ball Run",
            modifier = Modifier
                .height(170.dp)
                .alpha(0.8f)
                .constrainAs(coverImage){
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
        ReadingProgress(modifier = Modifier.padding(end = 10.dp).constrainAs(progressBar){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(coverImage.start)
            width = Dimension.fillToConstraints
        })
    }
}


@Composable
fun ReadingProgress(modifier: Modifier) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed) 0.6f else 0f,
        animationSpec = tween(1000, 0),
        label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = modifier
            .height(12.dp)
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

fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
    val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

    Palette.from(bitmap).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { colorValue ->
            onFinish(Color(colorValue))
        }
    }
}
