package com.example.reposlist.ui.ui.detailscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.reposlist.R
import com.example.reposlist.ui.model.GithubRepoDetails
import com.example.reposlist.ui.ui.common.Error
import com.example.reposlist.ui.ui.detailscreen.components.DetailInfoComponent
import com.example.reposlist.ui.ui.detailscreen.components.DetailTopBar
import com.example.reposlist.ui.theme.marginNormal
import com.example.reposlist.ui.ui.detailscreen.components.DetailListHeader
import com.example.shareddata.common.Resource
import com.example.shareddata.common.isLoading

@Composable
fun DetailsScreen(backAction: () -> Unit, detailScreenViewmodel: DetailScreenViewmodel = hiltViewModel()) {
    val appDetailsResource = detailScreenViewmodel.currentRepository.collectAsStateWithLifecycle().value
    val localUriHandler = LocalUriHandler.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            DetailTopBar(detailScreenViewmodel.name, backAction)
        },
        floatingActionButton = {
            if (appDetailsResource is Resource.Success) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = {
                        localUriHandler.openUri(appDetailsResource.data.url)
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_open_browser),
                        contentDescription = "Open Browser"
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                appDetailsResource.isLoading() -> {
                    Loading(modifier = Modifier.fillMaxSize())
                }

                appDetailsResource is Resource.Success -> {
                    Success(appDetails = appDetailsResource.data)
                }

                appDetailsResource is Resource.Failure -> {
                    Error(modifier = Modifier.fillMaxSize()) {
                        detailScreenViewmodel.loadRepository()
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
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Box(
                Modifier.padding(marginNormal)
            ) {
                DetailListHeader(
                    modifier = Modifier.fillMaxWidth(),
                    appDetails.image,
                    appDetails.ownerName
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

private fun getIconForType(type: RepositoryDetailType): Int {
    return when (type) {
        RepositoryDetailType.NAME -> R.drawable.ic_name
        RepositoryDetailType.DESCRIPTION -> R.drawable.ic_name
        RepositoryDetailType.SIZE -> R.drawable.ic_weight
        RepositoryDetailType.DOWNLOAD -> R.drawable.ic_downloads
        RepositoryDetailType.LAST_UPDATED -> R.drawable.ic_time
        RepositoryDetailType.RATING -> R.drawable.ic_star
        RepositoryDetailType.ISSUES -> R.drawable.ic_bug
    }
}

private fun getLabelForType(type: RepositoryDetailType): Int {
    return when (type) {
        RepositoryDetailType.NAME -> R.string.detail_name
        RepositoryDetailType.DESCRIPTION -> R.string.detail_description
        RepositoryDetailType.SIZE -> R.string.detail_size
        RepositoryDetailType.DOWNLOAD -> R.string.detail_forks
        RepositoryDetailType.LAST_UPDATED -> R.string.detail_last_updated
        RepositoryDetailType.RATING -> R.string.detail_rating
        RepositoryDetailType.ISSUES -> R.string.detail_open_issues
    }
}