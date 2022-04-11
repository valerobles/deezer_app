package fhnw.emoba.freezerapp.ui.screens

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.R
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun SongScreen(model: FreezerModel) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.colors),
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )

        Scaffold(
        backgroundColor = Color.Transparent,
        content = { Body(model) })}

}

@Composable
private fun Body(model: FreezerModel) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (songsList, songsChooser, footer) = createRefs()

        SongSearch(model, Modifier.constrainAs(songsChooser) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })
        SongList(model, Modifier.constrainAs(songsList) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(songsChooser.bottom, 20.dp)
            bottom.linkTo(footer.bottom, 5.dp)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        Footer(model, Modifier.constrainAs(footer) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SongSearch(model: FreezerModel, modifier: Modifier) {
    with(model) {
        val keyboard = LocalSoftwareKeyboardController.current
        Card(modifier,backgroundColor = Color.Transparent,elevation = 0.dp) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                OutlinedTextField(value = searchText,
                    onValueChange = { searchText = it },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            keyboard?.hide()
                            searchText = ""
                            fetchSongs()
                        })
                        {
                            Icon(Icons.Filled.Clear, "löschen")
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboard?.hide()
                            fetchSongs()
                        },
                        onDone = {
                            keyboard?.hide()
                            fetchSongs()
                        }
                    ),
                    placeholder = { Text("Suche") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                searchText = searchText.replace("\n", "", ignoreCase = true)
                            }
                            fetchSongs()
                            true
                        }
                )

            }
        }
    }
}

@Composable
private fun SongList(model: FreezerModel, modifier: Modifier) {
    with(model) {
        when {
            isLoading -> LoadingBox("Lieder werden geladen")
            else ->

                Box(modifier) {
                    if (listOfSongs.isEmpty()) {
                        Text(
                            text = "Keine Lieder gefunden",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(10.dp)
                        )
                    } else {
                        Divider(modifier = Modifier.align(Alignment.TopStart))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(listOfSongs) {
                                SongPane(song = it, model = model)
                            }
                        }
                    }
                }
        }
    }
}

@Composable
private fun Footer(model: FreezerModel, modifier: Modifier) {
    with(model) {
        Text(
            text = "Best App Ever",
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .background(Color.White)
                .padding(horizontal = 15.dp, vertical = 5.dp)
        )
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