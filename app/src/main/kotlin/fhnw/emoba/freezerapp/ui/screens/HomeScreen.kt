package fhnw.emoba.freezerapp.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Artist
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen


@Composable
fun HomeScreen(model: FreezerModel) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.colors),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            backgroundColor = Color.Transparent,

            topBar = {MainBar() },
            bottomBar = {if (model.playerMode)
                             CurrentSongPane(model = model)   },
            floatingActionButton = {
                LibSearchFab(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = {Body(model) },
        )
    }

}

@Composable
private fun Body(model: FreezerModel) {
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
            SubheadingHome(text = "Artists recommended to you")
            LazyRow{
                items(listOfArtists) {
                    ArtistPane(artist = it, model = model)
                }

            }
            if(favoriteSongs.size != 0) {
                Spacer(Modifier.height(50.dp))
                SubheadingHome(text = "Your favourite songs")
                LazyRow {
                    items(favoriteSongs) {
                        FavouritesPane(song = it, model = model)
                    }

                }
            }
            Spacer(Modifier.height(50.dp))
            SubheadingHome(text = "Radio Stations")
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
                    //model.currentPlaying = song
                    model.selectedSong = song
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

            Text(text = name, maxLines = 1,overflow = TextOverflow.Ellipsis,textAlign = TextAlign.Center)
        }
    }

}

@Composable
fun SubheadingHome(text: String) {
    Text(text     = text,
        fontSize = 20.sp,
        modifier = Modifier.padding(10.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


