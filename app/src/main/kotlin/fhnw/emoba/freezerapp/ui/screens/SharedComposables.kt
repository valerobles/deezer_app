package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.*
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab
import fhnw.emoba.freezerapp.ui.screens.tabs.Heading
import fhnw.emoba.freezerapp.ui.screens.tabs.Subheading


import kotlinx.coroutines.launch



@Composable
fun DefaultTopBar(model: FreezerModel, tab: Tab, lastTab: Tab? = null, nextTab: Tab? = null) {
    with(model) {
        TopAppBar(
            title          = { Text(tab.title) },
            navigationIcon = { if(lastTab != null) { BackToScreenIcon(this, lastTab) } },
            actions        = { if (nextTab != null) {
                IconButton(onClick = { currentTab = nextTab}) {
                    Icon(Icons.Filled.ArrowForward, "ArrowForward")
                }
            }
            }
        )
    }
}

@Composable
fun SearchFab(model: FreezerModel) {
    FloatingActionButton(onClick = { model.currentScreen = Screen.TABSCREEN })
    { Icon(Icons.Filled.Search, "Search") }

}

@Composable
fun LibraryFAB(model: FreezerModel) {
    FloatingActionButton(onClick = { model.currentScreen = Screen.LIBRARYSCREEN })
    { Icon(Icons.Filled.LibraryMusic, "Library") }

}

@Composable
fun GoHomeFAB(model: FreezerModel) {
    with(model) {
        FloatingActionButton(
            onClick = { currentScreen = Screen.HOMESCREEN }
        ) { Icon(Icons.Filled.Home, "HITS") }
    }
}

@Composable
fun Bar(title: String) {
TopAppBar(title = { Text(title) })
}



@Composable
fun BackToScreenIcon(model: FreezerModel, tab: Tab) {
    with(model) {
        IconButton(onClick = {  currentTab =tab }) {
            Icon(Icons.Filled.ArrowBack, "Back")
        }
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongPane(song: Song, model: FreezerModel) {
    with(song) {
        Card(modifier  = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .clickable {

                model.currentScreen = Screen.PLAYERSCREEN
                model.currentSong = song
                model.playerMode = true
                model.getNextandLastSong()
                //song.albumImage = requestImage()
            },
            elevation = 4.dp,
        ) {
            Row(modifier            = Modifier.padding(10.dp)) {
                Column(modifier            = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Heading(title)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Subheading(text     = artist,
                            modifier = Modifier.align(Alignment.CenterStart))

                    }
                }
                Image(bitmap = albumImage, contentDescription = "")
            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumPane(album: Album, model: FreezerModel) {
    with(album) {
        Card(modifier  = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .clickable {

                model.currentScreen = Screen.ALBUMSCREEN
                model.currentAlbum = album
                model.playerMode = false
                model.fetchAlbumSongs()

            },
            elevation = 4.dp,
        ) {
            Row(modifier            = Modifier.padding(10.dp)) {
                Column(modifier            = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Heading(title)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Subheading(text     = artist,
                            modifier = Modifier.align(Alignment.CenterStart))

                    }
                }
                Image(bitmap = albumImage, contentDescription = "")
            }

        }
    }
}

@Composable
fun LibSearchFab(model: FreezerModel){

    Column(
       verticalArrangement = Arrangement.Center,

    ) {

        SearchFab(model = model)
        Spacer(Modifier.size(16.dp))
        LibraryFAB(model = model)
    }

}

@Composable
fun ArtistPane(song: Song, model: FreezerModel){

    with(song) {
        Column(
            Modifier,
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            Card(modifier  = Modifier
                .padding(top = 10.dp, start = 12.dp, end = 12.dp)
                .height(100.dp)
                .width(100.dp)
                .clickable {
                    //model.currentScreen = Screen.PLAYERSCREEN
                    //model.currentSong = song
                    //song.albumImage = requestImage()
                },
                elevation = 4.dp,
                shape = RoundedCornerShape(30.dp)

            ) {

                Image(artistImage,
                    contentDescription = "",
                    modifier = Modifier.height(200.dp))


            }
            
            Text(text = artist)
        }
        }
        
    }
@Composable
fun RadioPane(radio: Radio, model: FreezerModel){

    with(radio) {
        Column(
            Modifier,
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            Card(modifier  = Modifier
                .padding(top = 10.dp, start = 12.dp, end = 12.dp)
                .height(100.dp)
                .width(100.dp)
                .clickable {
                    model.currentScreen = Screen.RADIOSCREEN
                    model.currentRadio = radio
                    model.fetchRadioSongs()
                },
                elevation = 4.dp,
                shape = RoundedCornerShape(30.dp)

            ) {

                Image(radioImage,
                    contentDescription = "",
                    modifier = Modifier.height(200.dp))


            }

            Text(text = title)
        }
        }

    }




fun requestImage(url: String): ImageBitmap {
    return try {
        bitmap(url).asImageBitmap()
    } catch (e: Exception) {
        DEFAULT_ICON.asImageBitmap()
    }
}


fun currentlyPlaying(model: FreezerModel): Boolean {
    with(model) {
        when (isPlaying) {
            true -> {
                playerBarOn = true
            }
            false -> {
                playerBarOn = false
            }

        }

        return playerBarOn;

    }
}

@Composable
fun CurrentSongPane(model: FreezerModel){
    with(model){

        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceEvenly
        ) {
            Column() {
                currentSong?.let { Text(it.title) }
                currentSong?.let { Text(it.artist) }

            }

            IconButton(onClick = { }) {
                Icon(Icons.Filled.SkipPrevious, "")
            }
            IconButton(onClick = { currentSong?.let { startStopPlayer(it) } }) {
                if (!model.isPlaying)
                    Icon(
                        Icons.Filled.PlayArrow, "",
                        modifier = Modifier.size(60.dp,),
                    )
                else
                    Icon(
                        Icons.Filled.Pause, "",
                        modifier = Modifier.size(60.dp),
                    )
            }


            IconButton(onClick = {  }) {
                Icon(Icons.Filled.SkipNext, "")
            }
        }


    }

}

@Composable
fun DefaultBody(tab: Tab) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {
        /*
    }
        Image(painter            = painterResource(screen.resId),
            contentDescription = screen.title,
            contentScale       = ContentScale.FillWidth,
            modifier           = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(20.dp))
        )
    }

         */
    }





}

