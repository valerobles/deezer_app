package fhnw.emoba.freezerapp.ui.screens.tabs

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab
import fhnw.emoba.freezerapp.ui.screens.*
import fhnw.emoba.freezerapp.ui.screens.Bar


@Composable
fun TabsScreen(model: FreezerModel) {
    MaterialTheme {
        Scaffold(
            topBar = { Bar(model.title) },
            floatingActionButton = { GoHomeFAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = { Body(model) },
        )
    }
}

@Composable
private fun Body(model: FreezerModel){

    with(model) {
        Column {
            TabRow(selectedTabIndex = currentTab.ordinal,backgroundColor = Color.White) {
                for (tab in Tab.values()) {
                    Tab(
                        text = { Text(tab.title) },
                        selected = tab == currentTab,
                        onClick = { currentTab = tab }
                    )

                }

            }

            Crossfade(targetState = model.currentTab) { tab ->
                when (tab) {
                    Tab.ALBUMSTAB -> {
                        searchText = ""
                        AlbumsSearchScreen(model = model)


                    }
                    Tab.SONGSTAB -> {
                        searchText = ""
                        SongScreen(model = model)

                    }
                }

            }

        }


    }

}










@Composable
fun Bar(title: String) {
    TopAppBar(title = { Text(title) })
}


