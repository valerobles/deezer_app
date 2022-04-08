package fhnw.emoba.freezerapp.ui.screens.tabs

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Song
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
            floatingActionButton = { FAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = { Body(model) },
        )
    }
}

@Composable
private fun Body(model: FreezerModel){

    with(model) {
        Column {
            TabRow(selectedTabIndex = currentTab.ordinal) {
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
                        AlbumsScreen(model = model)
                    }
                    Tab.HITSTAB -> {
                        HitsScreen(text = "Hi")
                    }
                    Tab.RADIOTAB -> {
                        RadioScreen(text = "Radio")
                    }
                    Tab.SONGSTAB -> {
                        BodyList(model = model)
                    }
                }

            }

        }


    }

}


@Composable
fun BodyList(model: FreezerModel) {
    with(model){
        LazyColumn {
            items(listOfSongs) {
                SongPane(song = it, model = model)
            }
        }
    }
}
// https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/package-summary#lazycolumn


// : Implementieren Sie eine Funktion, die ein einzelnes Country visualisiert
//  verwenden Sie 'Card'
//  https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#card






@Composable
 fun Heading(text: String) {
    Text(text = text,
        style = MaterialTheme.typography.h4)
}

@Composable
 fun Subheading(text: String, modifier: Modifier) {
    Text(text     = text,
        style    = MaterialTheme.typography.h5,
        modifier = modifier
    )
}


@Composable
fun Bar(title: String) {
    TopAppBar(title = { Text(title) })
}

@Composable
private fun FAB(model: FreezerModel) {
    FloatingActionButton(onClick = { model.currentScreen = Screen.HOMESCREEN })
    { Icon(Icons.Filled.Home, "Home") }

}

