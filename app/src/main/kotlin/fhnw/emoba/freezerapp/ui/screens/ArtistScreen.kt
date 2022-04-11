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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun ArtistScreen(model: FreezerModel){
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

            model.currentArtist?.let { SongListHeading(text = it.name) }
            Spacer(Modifier.height(12.dp))
            Box(

                Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                Alignment.Center

            ) {
                model.currentArtist?.let { Image(it.artistImage, "", Modifier.size(128.dp)) }
            }
            Spacer(Modifier.height(12.dp))



        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(model.listOfArtistsSongs) {
                SongPane(song = it, model = model, artistX = true)
            }
        }

    }

}