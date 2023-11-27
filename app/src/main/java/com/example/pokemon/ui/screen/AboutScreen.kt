package com.example.pokemon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pokemon.BuildConfig
import com.example.pokemon.R
import com.example.pokemon.ui.theme.Dimen

@Composable
fun AboutScreen(
    dimens: Dimen
) {
    val appVersion = BuildConfig.VERSION_NAME

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pokedex",
                    fontSize = dimens.fontExtraLarge
                )
                Spacer(modifier = Modifier.height(dimens.spaceSmall))
                Text(text = "Version: $appVersion")
                Spacer(modifier = Modifier.height(dimens.default))
                Image(painter = painterResource(id = R.drawable.ic_launcher), contentDescription = null)
                Spacer(modifier = Modifier.height(dimens.spaceSmall))
                Text(text = "by Rafael Romanhole Borrozino")
            }
        }
    }
}