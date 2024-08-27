package com.example.reposlist.ui.ui.homescreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.reposlist.R
import com.example.reposlist.ui.ui.homescreen.components.AllItemsZone
import com.example.reposlist.ui.ui.homescreen.components.HeaderZone
import com.example.reposlist.ui.ui.homescreen.components.TopBar
import com.example.reposlist.ui.theme.marginNormal

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel(), onAppSelected: (id: Long, name: String) -> Unit) {
    val context = LocalContext.current
    val appsList = homeViewModel.listAppApps.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopBar(
                stringResource(id = R.string.app_title),
                onIconAction = {
                    Toast.makeText(context, R.string.feature_not_available, Toast.LENGTH_SHORT).show()
                })
        }) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = marginNormal)
        ) {
            HeaderZone(Modifier.weight(0.1f), stringResource(id = R.string.all_aps), R.drawable.ic_more)

            AllItemsZone(Modifier.weight(0.9f), appsList.value, onAppSelected) {
                homeViewModel.loadApps()
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen() { _, _ -> }
}