package com.example.reposlist.ui.ui.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.reposlist.R
import com.example.reposlist.ui.model.GithubRepoItem
import com.example.reposlist.ui.ui.common.Error
import com.example.reposlist.presentation.ui.common.Loading
import com.example.shareddata.common.Resource
import com.example.shareddata.common.isLoading
import com.example.shareddata.model.repositories.Repository

@Composable
fun AllItemsZone(
    modifier: Modifier,
    allApps: Resource<List<Repository>>,
    onAppClick: (id: Long, name: String) -> Unit = { _, _ -> },
    onRetryClick: () -> Unit = {},
) {
    when {
        allApps.isLoading() -> Loading(modifier)
        allApps is Resource.Success -> Success(modifier, allApps.data, onAppClick)
        allApps is Resource.Failure -> Error(modifier, onRetryClick)
    }
}

@Composable
private fun Success(modifier: Modifier, allApps: List<Repository>, onAppClick: (id: Long, name: String) -> Unit) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(allApps) { item ->
            ItemCard(
                item = GithubRepoItem(
                    id = item.id,
                    name = item.fullName,
                    description = item.description,
                    image = item.avatarUrl,
                    icon = R.drawable.ic_star,
                    stars = item.stars.toString(),
                ),
                onAppClick = onAppClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previvewAllItemsZone() {
    AllItemsZone(Modifier, Resource.Loading(emptyList()))
}
