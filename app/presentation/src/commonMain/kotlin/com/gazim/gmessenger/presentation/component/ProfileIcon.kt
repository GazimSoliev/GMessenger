package com.gazim.gmessenger.presentation.component

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gazim.gmessenger.presentation.theme.GMessengerTheme

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    image: Painter? = null,
    firstNameLetter: Char = ' ',
    size: Dp = 48.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    onClick: (() -> Unit)? = null,
) {
    val clickableModifier = if (onClick == null) Modifier else Modifier.clickable(onClick = onClick)
    Box(
        modifier =
            modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
                .then(clickableModifier),
        contentAlignment = Alignment.Center,
    ) {
        if (image != null) {
            Image(
                painter = image,
                modifier = Modifier.fillMaxSize().blur(1.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Image(
                painter = image,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
            )
        } else {
            Text(
                text = firstNameLetter.toString(),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                style = textStyle,
            )
        }
    }
}

@Preview
@Composable
fun ProfileIconPreview() {
    GMessengerTheme {
        ProfileIcon(
            firstNameLetter = 'G',
        )
    }
}
