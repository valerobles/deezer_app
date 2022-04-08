package fhnw.emoba.freezerapp.ui.screens

import android.view.KeyEvent
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
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun AlbumsSearchScreen(model: FreezerModel) {
    Scaffold(
        content = { Body(model) })

}

@Composable
private fun Body(model: FreezerModel) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (albumsList, albumsChooser, footer) = createRefs()

        AlbumSearch(model, Modifier.constrainAs(albumsChooser) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })
        AlbumList(model, Modifier.constrainAs(albumsList) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(albumsChooser.bottom, 20.dp)
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
private fun AlbumSearch(model: FreezerModel, modifier: Modifier) {
    with(model) {
        val keyboard = LocalSoftwareKeyboardController.current
        Card(modifier) {
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
                            fetchAlbums()
                        })
                        {
                            Icon(Icons.Filled.Clear, "löschen")
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboard?.hide()
                            fetchAlbums()
                        },
                        onDone = {
                            keyboard?.hide()
                            fetchAlbums()
                        }
                    ),
                    placeholder = { Text("Suche") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                searchText = searchText.replace("\n", "", ignoreCase = true)
                            }
                            fetchAlbums()
                            true
                        }
                )

            }
        }
    }
}

@Composable
private fun AlbumList(model: FreezerModel, modifier: Modifier) {
    with(model) {
        when {
            isLoading -> LoadingBox("Alben werden geladen")
            else ->

                Box(modifier) {
                    if (listOfAlbums.isEmpty()) {
                        Text(
                            text = "Keine Alben gefunden",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(10.dp)
                        )
                    } else {
                        Divider(modifier = Modifier.align(Alignment.TopStart))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(listOfAlbums) {
                                AlbumPane(album = it, model = model)
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
                .background(MaterialTheme.colors.primary)
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