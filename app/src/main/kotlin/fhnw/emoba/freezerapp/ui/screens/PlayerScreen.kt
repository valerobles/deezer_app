package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen




@Composable
fun PlayerScreen(model: FreezerModel){
    MaterialTheme {
        Scaffold(
            topBar = { model.currentSong?.let { TopBar(model = model,song = it) } },
            floatingActionButton = { GoHomeFAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = { model.currentSong?.let { it1 -> Body(it1,model) } },
        )
        DefaultBody(tab = fhnw.emoba.freezerapp.model.Tab.ALBUMSTAB)
    }


}

@Composable
private fun TopBar(model: FreezerModel,song: Song) {
    with(model) {
        TopAppBar(
            title = { Text(song.title) },
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
            Text(text = title)
            Spacer(Modifier.height(12.dp))
            Box(

                Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                Alignment.Center

            ) {
                Image(albumImage,"")
            }
            Spacer(Modifier.height(12.dp))

            Text(text = artist)

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceEvenly,
            ) {
                IconButton(onClick = { }) { // playPreviousSong()
                    Icon(Icons.Filled.SkipPrevious, "",
                        modifier = Modifier.size(40.dp),
                    )
                }
                IconButton(onClick = {model.startStopPlayer(song) }) {

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
                IconButton(onClick = { }) { //  playNextSong()
                    Icon(Icons.Filled.SkipNext, "",
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceEvenly,
            ) {
                IconButton(onClick = { }) { // replayFromStart() }
                    Icon(Icons.Filled.Repeat, "",
                    )
                }
            }

        }

    }

}