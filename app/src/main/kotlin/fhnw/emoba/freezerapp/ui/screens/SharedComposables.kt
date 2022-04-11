package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.freezerapp.data.*
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab




@Composable
fun SearchFab(model: FreezerModel) {
    FloatingActionButton(backgroundColor = Color.White,onClick = { model.currentScreen = Screen.TABSCREEN })
    { Icon(Icons.Filled.Search, "Search") }

}

@Composable
fun LibraryFAB(model: FreezerModel) {
    FloatingActionButton(backgroundColor = Color.White,onClick = { model.currentScreen = Screen.LIBRARYSCREEN })
    { Icon(Icons.Filled.LibraryMusic, "Library") }

}

@Composable
fun GoHomeFAB(model: FreezerModel) {
    with(model) {
        FloatingActionButton(
            backgroundColor = Color.White,
            onClick = { currentScreen = Screen.HOMESCREEN },
        ) { Icon(Icons.Filled.Home, "")}
    }
}

@Composable
fun Bar(title: String) {
TopAppBar(title = { Text(title)}, backgroundColor = Color.White,)
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
fun SongPane(song: Song, model: FreezerModel,radio: Boolean =false,album: Boolean=false,artistX: Boolean=false,fave: Boolean =false) {
    with(song) {
        Card(
            modifier  = Modifier

                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .clickable {

                    model.currentScreen = Screen.PLAYERSCREEN
                   // model.selectedSong = song
                    model.currentPlaying = song
                    model.playerMode = true
                    if (radio)
                        model.currentPlaylist = model.listOfRadioSongs
                    else if (album)
                        model.currentPlaylist = model.listOfAlbumSongs
                    else if (artistX)
                        model.currentPlaylist = model.listOfArtistsSongs
                    else if (fave)
                        model.currentPlaylist = model.favoriteSongs
                    else
                        model.currentPlaylist = model.listOfSongs
                },


            elevation = 4.dp,
            

        ) {
            Row(modifier            = Modifier.padding(10.dp),
                Arrangement.SpaceBetween
                ) {

                Image(bitmap = albumImage, contentDescription = "", Modifier.size(100.dp).padding(5.dp))
                Column(modifier            = Modifier.padding(10.dp).width(170.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Heading(title)
                    Box() {
                        Subheading(text     = artist,
                            modifier = Modifier.align(Alignment.CenterStart))

                    }


                }
                Column(modifier            = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconButton(
                        onClick = { model.addRemoveFavorite(song) }) {
                        if (song.liked)
                            Icon(Icons.Filled.Favorite, "")
                        else
                            Icon(Icons.Outlined.FavoriteBorder, "")
                }

                }



            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouritesPane(song: Song, model: FreezerModel,fave: Boolean =false) {
    with(song) {
        Card(
            modifier  = Modifier

                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .clickable {

                    model.currentScreen = Screen.PLAYERSCREEN
                    model.currentPlaying = song
                    model.playerMode = true

                    model.currentPlaylist = model.favoriteSongs

                },


            elevation = 4.dp,

        ) {
            Row(modifier            = Modifier.padding(10.dp),
                Arrangement.SpaceBetween
                ) {
                Image(bitmap = albumImage, contentDescription = "", Modifier.size(80.dp).padding(5.dp))
                Column(modifier            = Modifier.padding(10.dp).width(90.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text=title, fontSize = 19.sp,maxLines = 1,overflow = TextOverflow.Ellipsis)
                    Box() {
                        Subheading(text     = artist,
                            modifier = Modifier.align(Alignment.CenterStart))

                    }


                }
                Column(modifier            = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    IconButton(
                        onClick = { model.addRemoveFavorite(song) }) {
                        if (song.liked)
                            Icon(Icons.Filled.Favorite, "")
                        else
                            Icon(Icons.Outlined.FavoriteBorder, "")
                }

                }



            }

        }
    }
}


// When searching albums
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
                elevation = 7.dp,
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
@Composable
fun ArtistPane(artist: Artist, model: FreezerModel){

    with(artist) {
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
                    model.currentScreen = Screen.ARTISTSCREEN
                    model.currentArtist = artist
                    model.fetchArtistSongs()
                },
                elevation = 7.dp,
                shape = RoundedCornerShape(50.dp)

            ) {

                Image(artistImage,
                    contentDescription = "",
                    modifier = Modifier.height(200.dp))


            }

            Text(text = name, overflow = TextOverflow.Ellipsis)
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

        Card(shape = RoundedCornerShape(30.dp),
            elevation = 10.dp,
            modifier = Modifier.fillMaxWidth().background(Color.Transparent)) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clickable { currentScreen = Screen.PLAYERSCREEN }
                    .background(Color.White),
                Arrangement.SpaceEvenly,
            ) {
                Column(
                    Modifier.padding(10.dp).width(150.dp)
                ) {
                    currentPlaying?.let { Text(it.title,maxLines = 1,overflow = TextOverflow.Ellipsis) }
                    currentPlaying?.let { Text(it.artist,maxLines = 1) }

                }

                IconButton(onClick = { playPreviousSong()}) {
                    Icon(Icons.Filled.SkipPrevious, "")
                }
                IconButton(onClick = { currentPlaying?.let { startStopPlayer(it) } }) {
                    if (!model.isPlaying)
                        Icon(
                            Icons.Filled.PlayArrow, "",
                        )
                    else
                        Icon(
                            Icons.Filled.Pause, "",
                        )
                }


                IconButton(onClick = { playNextSong() }) {
                    Icon(Icons.Filled.SkipNext, "")
                }
            }

        }



    }

}

@Composable
fun Heading(text: String) {
    Text(text = text,
        fontSize = 25.sp,
        maxLines = 1,overflow = TextOverflow.Ellipsis)
}

@Composable
fun Subheading(text: String, modifier: Modifier) {
    Text(text     = text,
        fontSize = 20.sp,
        modifier = modifier,
        maxLines = 1,overflow = TextOverflow.Ellipsis
    )
}








