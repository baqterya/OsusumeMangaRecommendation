package com.baqterya.mangarecommendation.ui.theme.mainmenu

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.baqterya.mangarecommendation.R

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun MainMenuScreen(/*navController: NavController*/) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
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
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end=35.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.fire),
                        contentDescription = "Streak Icon",
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end=5.dp)
                    )

                }
            }

        }


    }
}