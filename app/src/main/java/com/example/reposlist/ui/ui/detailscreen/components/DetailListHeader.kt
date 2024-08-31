package com.example.reposlist.ui.ui.detailscreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.reposlist.R
import com.example.reposlist.ui.theme.avatarHeaderSize

@Composable
fun DetailListHeader(modifier: Modifier = Modifier, image: String, name: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            error = painterResource(R.drawable.ic_account),
            placeholder = painterResource(R.drawable.ic_account),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(avatarHeaderSize)
                .clip(CircleShape),
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun DetailsListHeaderPreview() {
    DetailListHeader(image = "image", name = "John Doe")
}