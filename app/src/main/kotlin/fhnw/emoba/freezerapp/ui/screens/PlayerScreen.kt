package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen




@Composable
fun PlayerScreen(model: FreezerModel){
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.colors_2),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = { model.currentPlaying?.let { TopBar(model = model,song = it) } },
            floatingActionButton = { GoHomeFAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = { model.currentPlaying?.let { it1 -> Body(it1,model) } },
        )
    }


}

@Composable
private fun TopBar(model: FreezerModel,song: Song) {
    with(model) {
        TopAppBar(
            title = { Text(song.title) },
            backgroundColor = Color.White,
            navigationIcon = {
                IconButton(onClick = { currentScreen = Screen.TABSCREEN
               }) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
            }
        )
    }
}

@Composable
private fun Body(song: Song, model: FreezerModel){
    with(song){
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            Text(text = title,fontSize = 30.sp, maxLines = 2, overflow = TextOverflow.Ellipsis,textAlign = TextAlign.Center,)
            Spacer(Modifier.height(12.dp))
            Box(

                Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),

                Alignment.Center

            ) {

                Image(albumImage,"",modifier = Modifier.size(250.dp))
            }
            Spacer(Modifier.height(12.dp))

            Text(text = artist, maxLines = 2, overflow = TextOverflow.Ellipsis,fontSize = 20.sp)

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceEvenly,
            ) {
                IconButton(onClick = {model.playPreviousSong() }) { // playPreviousSong()
                    Icon(Icons.Filled.SkipPrevious, "",
                        modifier = Modifier.size(40.dp),
                    )
                }
                IconButton(onClick = {
                    model.startStopPlayer(song) }) {

                    if (model.playerMode) {
                        if (!model.isPlaying)
                            Icon(
                                Icons.Filled.PlayArrow, "",
                                modifier = Modifier.size(60.dp),
                            )
                        else
                            Icon(
                                Icons.Filled.Pause, "",
                                modifier = Modifier.size(60.dp),
                            )

                    }
                }
                IconButton(onClick = { model.playNextSong()}) {
                    Icon(Icons.Filled.SkipNext, "",
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceEvenly,
            ) {
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