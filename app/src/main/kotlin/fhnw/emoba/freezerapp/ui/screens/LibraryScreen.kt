package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.model.FreezerModel


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

            bottomBar = {
                if (model.playerMode)
                    CurrentSongPane(model = model)
            },
            floatingActionButton = { GoHomeFAB(model = model) })
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
            Spacer(Modifier.height(20.dp))
            SongListHeading(text = "Your favorite songs")
            Spacer(Modifier.height(12.dp))
            


        }
        
        if(model.listOfFavoriteSongs.size != 0) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(model.listOfFavoriteSongs) {
                    SongPane(song = it, model = model)
                }
            }
        }
        else
            Text(text = "your library is empty :( \n go like some songs <3",modifier = Modifier.padding(top = 50.dp),
                fontSize = 20.sp)
            

    }

}