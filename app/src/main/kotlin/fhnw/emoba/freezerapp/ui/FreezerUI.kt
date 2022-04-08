package fhnw.emoba.freezerapp.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.ui.screens.*
import fhnw.emoba.freezerapp.ui.screens.tabs.TabsScreen


@Composable
fun FreezerUI(model: FreezerModel) {
    MaterialTheme {
        Scaffold(
            floatingActionButton = { searchFAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            content = { },
        )

            Crossfade(targetState = model.currentScreen) { screen ->
                when (screen) {
                    Screen.HOMESCREEN -> {
                        HomeScreen(model = model)
                    }
                    Screen.TABSCREEN -> {
                        TabsScreen(model = model)
                    }

                    Screen.PLAYERSCREEN -> {
                        PlayerScreen( model = model)
                    }


                }



        }

    }
}







@Composable
fun searchFAB(model: FreezerModel) {
    FloatingActionButton(onClick = { model.currentScreen = Screen.TABSCREEN })
    { Icon(Icons.Filled.Search, "Search") }

}

@Composable
fun MessageBox(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h3
        )
    }
}








