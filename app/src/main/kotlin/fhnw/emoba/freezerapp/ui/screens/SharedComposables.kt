package fhnw.emoba.freezerapp.ui.screens

import android.graphics.fonts.FontFamily
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.*
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab




@Composable
fun SearchFab(model: FreezerModel) {
    FloatingActionButton(backgroundColor = Color(245,245,245),onClick = { model.currentScreen = Screen.TABSCREEN })
    { Icon(Icons.Filled.Search, "Search") }

}

@Composable
fun LibraryFAB(model: FreezerModel) {
    FloatingActionButton(backgroundColor = Color(245,245,245),onClick = { model.currentScreen = Screen.LIBRARYSCREEN })
    { Icon(Icons.Filled.LibraryMusic, "Library") }

}

@Composable
fun GoHomeFAB(model: FreezerModel) {
    with(model) {
        FloatingActionButton(
            backgroundColor = Color(245,245,245),
            onClick = { currentScreen = Screen.HOMESCREEN },
        ) { Icon(Icons.Filled.Home, "")}
    }
}

@Composable
fun Bar(title: String) {
TopAppBar(title = { Text(title)}, backgroundColor = Color.White)
}

@Composable
fun MainBar() {
    TopAppBar(title = {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            Image(painterResource(R.drawable.deezerlogo),"",modifier = Modifier.size(150.dp),alignment = Alignment.Center)
        }}, backgroundColor = Color.White)


}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongPane(song: Song, model: FreezerModel,radio: Boolean =false,album: Boolean=false,artistX: Boolean=false,fave: Boolean =false) {
    val color: Color = if (model.currentPlaying != song) {
        Color.White
    }
        else{
            Color(201,213,233)

    }



    with(song) {
        Card(

            modifier  = Modifier

                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .clickable {

                    model.currentScreen = Screen.PLAYERSCREEN
                    model.selectedSong = song
                    //model.currentPlaying = song
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
            backgroundColor = color
            

        ) {
            Row(modifier            = Modifier.padding(10.dp),
                Arrangement.SpaceBetween,


                ) {

                Image(bitmap = albumImage, contentDescription = "",
                    Modifier
                        .size(100.dp)
                        .padding(5.dp))
                Column(modifier            = Modifier
                    .padding(10.dp)
                    .width(170.dp),
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


fun requestImage(url: String): ImageBitmap {
    return try {
        bitmap(url).asImageBitmap()
    } catch (e: Exception) {
        DEFAULT_ICON.asImageBitmap()
    }
}



@Composable
fun CurrentSongPane(model: FreezerModel){
    with(model){

        Card(shape = RoundedCornerShape(30.dp),
            elevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)) {

            Row(
                Modifier
                    .fillMaxWidth()


                    .clickable { currentScreen = Screen.PLAYERSCREEN }
                    .background(Color(245,245,245)),
                Arrangement.SpaceEvenly,
            ) {
                Column(
                    Modifier
                        .padding(10.dp)
                        .width(150.dp)
                ) {
                    currentPlaying?.let { Text(it.title,maxLines = 1,overflow = TextOverflow.Ellipsis) }
                    currentPlaying?.let { Text(it.artist,maxLines = 1) }

                }

                IconButton(onClick = { playPreviousSong()}) {
                    Icon(Icons.Filled.SkipPrevious, "",modifier =Modifier.padding(top=10.dp))
                }
                IconButton(onClick = { currentPlaying?.let { startStopPlayer(it) } }) {
                    if (!model.isPlaying)
                        Icon(
                            Icons.Filled.PlayArrow, "",modifier =Modifier.padding(top=10.dp)
                        )
                    else
                        Icon(
                            Icons.Filled.Pause, "",modifier =Modifier.padding(top=10.dp)
                        )
                }


                IconButton(onClick = { playNextSong() }) {
                    Icon(Icons.Filled.SkipNext, "",modifier =Modifier.padding(top=10.dp))
                }
            }

        }



    }

}

@Composable
fun Heading(text: String) {
    Text(text = text,
        fontSize = 25.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun Subheading(text: String, modifier: Modifier) {
    Text(text     = text,
        fontSize = 20.sp,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun SongListHeading(text: String){
    Text(text     = text,
        fontSize = 30.sp,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )

}
@Composable
fun SongListSubHeading(text: String){
    Text(text     = text,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 10.dp),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )

}








