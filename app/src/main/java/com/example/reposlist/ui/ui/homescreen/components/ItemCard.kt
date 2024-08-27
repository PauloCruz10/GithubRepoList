package com.example.reposlist.ui.ui.homescreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reposlist.ui.model.GithubRepoItem
import com.example.reposlist.R
import com.example.reposlist.ui.theme.Typography
import com.example.reposlist.ui.theme.marginNormal
import com.example.reposlist.ui.theme.marginSmall
import com.example.reposlist.ui.theme.marginxxSmall

@Composable
fun ItemCard(item: GithubRepoItem, onAppClick: (id: Long, name: String) -> Unit) {
    Card(
        onClick = { onAppClick(item.id, item.name) },
        modifier = Modifier
            .fillMaxSize()
            .padding(end = marginNormal, bottom = marginNormal)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(marginSmall)),
        shape = RoundedCornerShape(marginSmall),
        elevation = CardDefaults.cardElevation(marginxxSmall),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .padding(marginSmall)
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(marginxxSmall),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = marginxxSmall, top = marginxxSmall),
                    text = item.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )

                Row {
                    Icon(
                        modifier = Modifier.padding(end = marginxxSmall),
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier.padding(top = marginxxSmall),
                        style = Typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewItemCard() {
    ItemCard(
        item = GithubRepoItem(
            1,
            "Test",
            "https:exampleImage.com/leImage.png",
            "Best description eva",
            R.drawable.ic_star,
            "2.2"
        )
    ) { _, _ ->

    }
}