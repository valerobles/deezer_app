package fhnw.emoba.freezerapp.ui.screens.tabs

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
import fhnw.emoba.freezerapp.ui.screens.PlayerScreen


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
            BodyList(model = model)
        }

    }

}


@Composable
private fun BodyList(model: FreezerModel) {
    with(model){
        LazyColumn {
            items(songs) {
                SongPane(song = it, model = model)
            }
        }
    }
}
// https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/package-summary#lazycolumn


// : Implementieren Sie eine Funktion, die ein einzelnes Country visualisiert
//  verwenden Sie 'Card'
//  https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#card

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SongPane(song: Song, model: FreezerModel) {
    with(song) {
        Card(modifier  = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .clickable {
                model.currentScreen = Screen.PLAYERSCREEN
                model.currentSong = song
                       },
            elevation = 4.dp,
            ) {
            Column(modifier            = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Heading(songTitle)
                Box(modifier = Modifier.fillMaxWidth()) {
                    Subheading(text     = artist,
                        modifier = Modifier.align(Alignment.CenterStart))

                }
            }
        }
    }
}




@Composable
private fun Heading(text: String) {
    Text(text = text,
        style = MaterialTheme.typography.h4)
}

@Composable
private fun Subheading(text: String, modifier: Modifier) {
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

