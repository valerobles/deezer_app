package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel



@Composable
fun HomeScreen(model: FreezerModel) {
    MaterialTheme {
        Scaffold(
            topBar = {Bar(model.title) },
            bottomBar = {if (model.playerMode)
                             CurrentSongPane(model = model)   },
            floatingActionButton = {
                LibSearchFab(model) },
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
            else -> HomeBody(model = model)
        }
    }




}

@Composable
private fun HomeBody(model: FreezerModel){
    with(model){
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()) {
            Spacer(Modifier.height(50.dp))
            Text(text = "Artists recommended to you")
            LazyRow{
                items(listOfArtists) {
                    ArtistPane(artist = it, model = model)
                }

            }
            if(favoriteSongs.size != 0) {
                Spacer(Modifier.height(50.dp))
                Text(text = "Your favourite songs")
                LazyRow {
                    items(favoriteSongs) {
                        SongPane(song = it, model = model)
                    }

                }
            }
            Spacer(Modifier.height(50.dp))
            Text(text = "Radio Stations")
            LazyRow{
                items(listOfRadio) {
                    RadioPane(radio = it, model = model)
                }

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