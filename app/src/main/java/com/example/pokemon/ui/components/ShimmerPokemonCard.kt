package com.example.pokemon.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.example.pokemon.ui.theme.Dimen

@Composable
fun ShimmerPokemonCard() {
    val dimens = compositionLocalOf { Dimen() }.current

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