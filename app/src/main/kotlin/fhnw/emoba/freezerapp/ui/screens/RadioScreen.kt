package fhnw.emoba.freezerapp.ui.screens

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
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab
import fhnw.emoba.freezerapp.ui.MessageBox

@Composable
fun RadioScreen(model: FreezerModel){
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.colors),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
            backgroundColor = Color.Transparent,
        content = { Body(model) }, floatingActionButton = { GoHomeFAB(model = model) },
        topBar = { model.currentRadio?.let { Bar(model.currentRadio!!.title) } }
    )}

}

@Composable
private fun TopBar(model: FreezerModel,radio: Radio) {
    with(model) {
        TopAppBar(
            title = { Text(radio.title) },
            navigationIcon = {
                IconButton(onClick = { currentScreen = Screen.HOMESCREEN
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

            model.currentRadio?.let { Text(text = it.title) }
            Spacer(Modifier.height(12.dp))
            Box(

                Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                Alignment.Center

            ) {
                model.currentRadio?.let { Image(it.radioImage, "", Modifier.size(128.dp)) }
            }
            Spacer(Modifier.height(12.dp))



        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(model.listOfRadioSongs) {
                SongPane(song = it, model = model, radio = true)
            }
        }

    }

}