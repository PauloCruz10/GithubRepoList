package com.example.reposlist.presentation.ui.detailscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reposlist.R
import com.example.reposlist.ui.theme.Typography
import com.example.reposlist.ui.theme.detailComponentIcon
import com.example.reposlist.ui.theme.lightGreen
import com.example.reposlist.ui.theme.marginNormal
import com.example.reposlist.ui.theme.marginxSmall
import com.example.reposlist.ui.theme.marginxxSmall

@Composable
fun DetailInfoComponent(label: Int, value: String, icon: Int) {
    Column(modifier = Modifier.padding(start = marginNormal, end = marginNormal, bottom = marginNormal)) {
        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(detailComponentIcon)
                    .clip(CircleShape)
                    .background(lightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "iconTitle",
                    modifier = Modifier.size(30.dp),
                )
            }
            Text(
                text = stringResource(label),
                modifier = Modifier.padding(top = marginxxSmall, start = marginxSmall),
                style = Typography.bodyLarge,
            )
        }
        Text(
            text = value,
            modifier = Modifier.padding(start = marginxxSmall, end = marginNormal, top = marginxxSmall),
            style = Typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewDetailInfoComponent() {
    DetailInfoComponent(R.string.detail_name, "John Doe", R.drawable.ic_store)
}