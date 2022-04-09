package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.MessageBox


@Composable
fun LibrayScreen(model: FreezerModel) {
    Scaffold(
        content = { Body(model) },
        bottomBar = {if (model.playerMode)
            CurrentSongPane(model = model)   },
        floatingActionButton = { GoHomeFAB(model = model) })

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