package com.example.reposlist.ui.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.reposlist.R
import com.example.reposlist.ui.theme.Typography
import com.example.reposlist.ui.theme.lightGreen
import com.example.reposlist.ui.theme.marginNormal

@Composable
fun Error(modifier: Modifier = Modifier, onRetryClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "My GIF",
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .size(100.dp, 60.dp)
                .clickable { onRetryClick() }
                .background(lightGreen, RoundedCornerShape(marginNormal)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.reload),
                style = Typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}