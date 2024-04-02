package com.example.pokemon.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.pokemon.R
import com.example.pokemon.data.network.models.PokemonItem
import com.example.pokemon.ui.theme.Dimen
import com.example.pokemon.ui.theme.PokemonTheme

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun PokemonCard(
    isLoading: Boolean,
    pokemonItem: PokemonItem? = null,
    goToPokemonDetails: () -> Unit
) {
    val dimens = compositionLocalOf { Dimen() }.current

    if (isLoading) {
        Card(
            elevation = CardDefaults.cardElevation(dimens.spaceXXSmall),
            shape = RoundedCornerShape(dimens.default),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .padding(dimens.default)
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier.padding(dimens.default)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8F)
                        .aspectRatio(1.5F)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(dimens.default))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2F)
                        .height(dimens.default)
                        .align(Alignment.CenterHorizontally)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(dimens.default))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .align(Alignment.CenterHorizontally)
                        .height(dimens.default)
                        .shimmerEffect()
                )
            }
        }
    }

    else {
        Card(
            elevation = CardDefaults.cardElevation(dimens.spaceXXSmall),
            shape = RoundedCornerShape(dimens.default),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .padding(dimens.default)
                .fillMaxWidth()
                .clickable(
                    onClick = goToPokemonDetails
                )

        ) {
            Column(
                modifier = Modifier.padding(dimens.default)
            ) {
                pokemonItem?.let {
                    AsyncImage(
                        model = pokemonItem.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(dimens.default))
                    Text(
                        text = stringResource(R.string.pokemon_number, pokemonItem.numberPokedex),
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontSize = dimens.fontDefault,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(dimens.default))
                    Text(
                        text = pokemonItem.name,
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontSize = dimens.fontDefault,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Preview
@Composable
fun PreviewPokemonCard() {
    PokemonTheme {
        PokemonCard(
            isLoading = true,
            pokemonItem = PokemonItem(
                numberPokedex = 1,
                name = "bulbasaur",
                imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/133.png"
            ),
            goToPokemonDetails = {}
        )
    }
}