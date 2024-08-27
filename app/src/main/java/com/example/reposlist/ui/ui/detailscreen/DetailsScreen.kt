package com.example.reposlist.ui.ui.detailscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.reposlist.R
import com.example.reposlist.ui.model.GithubRepoDetails
import com.example.reposlist.ui.ui.common.Error
import com.example.reposlist.presentation.ui.detailscreen.components.DetailInfoComponent
import com.example.reposlist.presentation.ui.detailscreen.components.DetailTopBar
import com.example.reposlist.ui.theme.lightGreen
import com.example.reposlist.ui.theme.marginNormal
import com.example.reposlist.ui.theme.marginxSmall
import com.example.reposlist.ui.theme.oliveGreen
import com.example.shareddata.common.Resource
import com.example.shareddata.common.isLoading

@Composable
fun DetailsScreen(backAction: () -> Unit, detailScreenViewmodel: DetailScreenViewmodel = hiltViewModel()) {
    val appDetailsResource = detailScreenViewmodel.currentApp.collectAsStateWithLifecycle().value
    val showDialog = detailScreenViewmodel.showDialog.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            DetailTopBar(detailScreenViewmodel.name, backAction)
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = lightGreen,
                onClick = {
                    detailScreenViewmodel.showDialog()
                }
            ) {
                Icon(painterResource(id = R.drawable.ic_download), contentDescription = "Download")
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (showDialog) {
                AlertDialog(
                    title = { Text(text = stringResource(id = R.string.feature_not_available)) },
                    text = { Text(text = stringResource(id = R.string.download_not_available)) },
                    onDismissRequest = { detailScreenViewmodel.hideDialog() },
                    confirmButton = {},
                    containerColor = oliveGreen,
                )
            }
            when {
                appDetailsResource.isLoading() -> {
                    Loading(modifier = Modifier.fillMaxSize())
                }

                appDetailsResource is Resource.Success -> {
                    Success(appDetails = appDetailsResource.data)
                }

                appDetailsResource is Resource.Failure -> {
                    Error(modifier = Modifier.fillMaxSize()) {
                        detailScreenViewmodel.loadApp()
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Success(appDetails: GithubRepoDetails) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Box(
                Modifier.padding(marginNormal)
            ) {
                AsyncImage(
                    model = appDetails.image,
                    contentDescription = null,
                    error = painterResource(R.drawable.ic_placeholder),
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(marginxSmall)),
                )
            }
        }

        items(appDetails.details) { item ->
            DetailInfoComponent(
                label = getLabelForType(item.type),
                value = item.detailedInfo,
                icon = getIconForType(item.type)
            )
        }
    }
}

private fun getIconForType(type: AppDetailType): Int {
    return when (type) {
        AppDetailType.NAME -> R.drawable.ic_name
        AppDetailType.SIZE -> R.drawable.ic_weight
        AppDetailType.DOWNLOAD -> R.drawable.ic_downloads
        AppDetailType.LAST_UPDATED -> R.drawable.ic_time
        AppDetailType.RATING -> R.drawable.ic_star
    }
}

private fun getLabelForType(type: AppDetailType): Int {
    return when (type) {
        AppDetailType.NAME -> R.string.detail_name
        AppDetailType.SIZE -> R.string.detail_size
        AppDetailType.DOWNLOAD -> R.string.detail_downloads
        AppDetailType.LAST_UPDATED -> R.string.detail_last_updated
        AppDetailType.RATING -> R.string.detail_rating
    }
}