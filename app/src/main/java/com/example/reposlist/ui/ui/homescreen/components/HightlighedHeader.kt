package com.example.reposlist.ui.ui.homescreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.reposlist.R
import com.example.reposlist.ui.theme.marginNormal

@Composable
fun HeaderZone(modifier: Modifier, text: String, icon: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(end = marginNormal)
        )
        Spacer(modifier = Modifier.width(marginNormal))
        Icon(
            modifier = Modifier.padding(end = marginNormal),
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewHeaderZone() {
    HeaderZone(Modifier, text = "This is an header test", icon = R.drawable.ic_account)
}
