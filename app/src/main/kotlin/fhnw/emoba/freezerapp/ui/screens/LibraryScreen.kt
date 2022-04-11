package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen


@Composable
fun LibrayScreen(model: FreezerModel) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.colors),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
            backgroundColor = Color.Transparent,
            content = { Body(model) },
            topBar = { model.currentPlaying?.let { TopBar(model = model, song = it) } },
            bottomBar = {
                if (model.playerMode)
                    CurrentSongPane(model = model)
            },
            floatingActionButton = { GoHomeFAB(model = model) })
    }
}

@Composable
private fun TopBar(model: FreezerModel,song: Song) {
    with(model) {
        TopAppBar(
            title = { Text(song.title) },
            backgroundColor = Color.White,
            navigationIcon = {
                IconButton(onClick = { currentScreen = Screen.HOMESCREEN
                }) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
            }
        )
    }
}

@Composable
private fun Body(model: FreezerModel) {
    Column(
        Modifier,
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.size(12.dp))
        Column(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center,
            Alignment.CenterHorizontally,) {

            Heading(text = "Your favorite songs") 
            Spacer(Modifier.height(12.dp))
            


        }
        
        if(model.favoriteSongs.size != 0) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(model.favoriteSongs) {
                    SongPane(song = it, model = model)
                }
            }
        }
        else
            Text(text = "your library is empty, go like some songs")
            

    }

}