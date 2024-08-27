package com.example.reposlist.ui.ui.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reposlist.R
import com.example.reposlist.ui.theme.AppListTheme
import com.example.reposlist.ui.theme.lightGreen
import com.example.reposlist.ui.theme.marginxSmall
import com.example.reposlist.ui.theme.oliveGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    leftIconResId: Int = R.drawable.ic_store,
    rightIconResId: Int = R.drawable.ic_account,
    onIconAction: () -> Unit,
) {
    // TODO remove text
    val gradientColors = listOf(
        lightGreen, oliveGreen
    )
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.padding(start = marginxSmall)
                ) {
                    Icon(
                        painter = painterResource(leftIconResId),
                        contentDescription = "iconTitle",
                        modifier = Modifier.size(50.dp),
                    )
                }
                Text(
                    text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        modifier = Modifier.background(
            Brush.horizontalGradient(
                colors = gradientColors
            )
        ),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        actions = {
            IconButton(
                onClick = onIconAction
            ) {
                Icon(
                    painter = painterResource(id = rightIconResId),
                    contentDescription = "End icon",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    AppListTheme {
        TopBar(title = "WallApps", onIconAction = {})
    }
}