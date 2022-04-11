package fhnw.emoba.freezerapp.ui.screens.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.ui.screens.AlbumPane
import fhnw.emoba.freezerapp.ui.screens.SongPane

@Composable
fun AlbumScreen(model: FreezerModel){
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.colors),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
         backgroundColor = Color.Transparent,
        content = { Body(model)},
        topBar = { model.currentAlbum?.let { TopBar(model = model,album = it) } }
    )
    }

}

@Composable
private fun TopBar(model: FreezerModel,album: Album) {
    with(model) {
        TopAppBar(
            title = { Text(album.title) },
            navigationIcon = {
                IconButton(onClick = { currentScreen = Screen.TABSCREEN
                    currentTab = fhnw.emoba.freezerapp.model.Tab.ALBUMSTAB
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

        model.currentAlbum?.let { Text(text = it.title) }
        Spacer(Modifier.height(12.dp))
        Box(

            Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(15.dp)),
            Alignment.Center

        ) {
            model.currentAlbum?.let { Image(it.albumImage, "", Modifier.size(128.dp)) }
        }
        Spacer(Modifier.height(12.dp))

        model.currentAlbum?.let { Text(text = it.artist) }


    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(model.listOfAlbumSongs) {
            SongPane(song = it, model = model,album = true)
        }
    }

}

}