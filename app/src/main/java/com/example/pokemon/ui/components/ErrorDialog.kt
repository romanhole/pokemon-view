package com.example.pokemon.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.pokemon.R
import com.example.pokemon.ui.theme.Dimen

@Composable
fun ErrorDialog(
    text: String,
    textButton: String = stringResource(id = R.string.dialog_ok),
    onDismissRequest: () -> Unit = {}
) {
    val dimens = compositionLocalOf { Dimen() }.current

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(252.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(dimens.default)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimens.default)
            ) {
                Spacer(modifier = Modifier.height(dimens.spaceXXXLarge))
                Text(text = text, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.weight(1.0f))
                Button(
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimens.default),
                    onClick = onDismissRequest,
                    content = {
                        Text(text = textButton)
                    }
                )
            }
        }
    }
}
