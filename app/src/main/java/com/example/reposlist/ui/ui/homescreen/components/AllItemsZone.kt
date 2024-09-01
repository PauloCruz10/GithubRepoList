package com.example.reposlist.ui.ui.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.reposlist.R
import com.example.reposlist.ui.model.GithubRepoItem
import com.example.reposlist.ui.ui.common.Error
import com.example.reposlist.ui.ui.common.Loading
import com.example.shareddata.model.repositories.Repository

@Composable
fun AllItemsZone(
    modifier: Modifier,
    allApps: LazyPagingItems<Repository>,
    onAppClick: (id: Long, name: String) -> Unit = { _, _ -> },
    onRetryClick: () -> Unit = {},
) {
    when(allApps.loadState.refresh) {
        is LoadState.Loading -> Loading(modifier)
        is LoadState.Error -> {
            if (allApps.itemCount == 0) Error(modifier, onRetryClick) else Success(modifier, allApps, onAppClick)
        }
        else -> Success(modifier, allApps, onAppClick)
    }
}

@Composable
private fun Success(modifier: Modifier, allApps: LazyPagingItems<Repository>, onAppClick: (id: Long, name: String) -> Unit) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = allApps.itemCount,
            key = { index -> allApps[index]?.id ?: index }
        ) { index ->
            val item = allApps[index] ?: return@items
            ItemCard(
                item = GithubRepoItem(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    image = item.avatarUrl,
                    icon = R.drawable.ic_star,
                    stars = item.stars.toString(),
                    owner = item.ownerName,
                    language = item.language,
                ),
                onAppClick = onAppClick,
            )
        }
    }
}