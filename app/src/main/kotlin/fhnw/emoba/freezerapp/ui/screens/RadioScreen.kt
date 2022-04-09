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
fun RadioScreen(model: FreezerModel){
    Scaffold(
        content = { Body(model) })

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