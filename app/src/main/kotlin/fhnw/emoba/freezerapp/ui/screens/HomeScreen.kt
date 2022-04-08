package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel

import fhnw.emoba.freezerapp.ui.searchFAB


@Composable
fun HomeScreen(model: FreezerModel) {
    MaterialTheme {
        Scaffold(
            topBar = {Bar(model.title) },
            floatingActionButton = { searchFAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = {Body(model.listOfSongs,model) },
        )
    }
}

@Composable
private fun Body(list: List<Song>,model: FreezerModel) {
    with(model) {
        when {
            isLoading -> LoadingBox("Songs are being loaded")
            else -> allSongs(songs = list, model = model)
        }
    }




}

@Composable
private fun allSongs(songs: List<Song>, model: FreezerModel){
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()) {
        Spacer(Modifier.height(50.dp))
        Text(text = "Artists recommended to you")
        LazyRow{
            items(songs) {
                ArtistPane(song = it, model = model)
            }

        }
        Spacer(Modifier.height(100.dp))
        Text(text = "Your favourite songs")
        LazyRow{
            items(songs) {
                SongPane(song = it, model = model)
            }

        }
    }

}

@Composable
private fun LoadingBox(message: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(message, style = MaterialTheme.typography.h5)
        CircularProgressIndicator(modifier = Modifier.padding(10.dp))
    }
}