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

@Composable
fun PokemonCard(
    pokemonItem: PokemonItem? = null,
    goToPokemonDetails: () -> Unit
) {
    val dimens = compositionLocalOf { Dimen() }.current

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

@Preview
@Composable
fun PreviewPokemonCard() {
    PokemonTheme {
        PokemonCard(
            pokemonItem = PokemonItem(
                numberPokedex = 1,
                name = "bulbasaur",
                imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/133.png"
            ),
            goToPokemonDetails = {}
        )
    }
}