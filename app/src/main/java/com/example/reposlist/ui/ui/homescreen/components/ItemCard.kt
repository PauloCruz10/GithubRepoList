package com.example.reposlist.ui.ui.homescreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reposlist.ui.model.GithubRepoItem
import com.example.reposlist.R
import com.example.reposlist.ui.theme.Typography
import com.example.reposlist.ui.theme.itemCardSize
import com.example.reposlist.ui.theme.marginNormal
import com.example.reposlist.ui.theme.marginSmall
import com.example.reposlist.ui.theme.marginxxSmall

@Composable
fun ItemCard(item: GithubRepoItem, onAppClick: (id: Long, name: String) -> Unit) {
    Card(
        onClick = { onAppClick(item.id, item.name) },
        modifier = Modifier
            .fillMaxWidth()
            .height(itemCardSize)
            .padding(end = marginNormal, bottom = marginNormal)
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(marginSmall)),
        shape = RoundedCornerShape(marginSmall),
        elevation = CardDefaults.cardElevation(marginxxSmall),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(marginSmall)
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(marginxxSmall),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                if (item.description.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = marginxxSmall, top = marginxxSmall),
                        text = item.description,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(marginSmall),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = marginNormal)
                ) {
                    Text(
                        text = item.language,
                        modifier = Modifier.padding(top = marginxxSmall),
                        style = Typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                    Icon(
                        modifier = Modifier.padding(start = marginxxSmall),
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )

                    Text(text = item.stars, style = MaterialTheme.typography.bodySmall)
                }

                Column(
                    modifier = Modifier
                        .padding(end = marginSmall)
                        .align(Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = item.image,
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_account),
                        placeholder = painterResource(R.drawable.ic_account),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.End)
                            .clip(CircleShape),
                    )
                    Text(
                        text = item.owner,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewItemCard() {
    MaterialTheme {
        ItemCard(
            item = GithubRepoItem(
                1,
                "açwdhçadshfalsdfhaçsdfhns",
                "Best description eva",
                "https:exampleImage.com/leImage.png",
                R.drawable.ic_star,
                "2.2",
                "Steve Jobs",
                "Kotlin",
            )
        ) { _, _ -> }
    }
}