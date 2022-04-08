package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Heading

import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.DEFAULT_ICON
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.data.bitmap
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab
import fhnw.emoba.freezerapp.ui.screens.tabs.Heading
import fhnw.emoba.freezerapp.ui.screens.tabs.Subheading

import kotlinx.coroutines.launch



    @Composable
    fun DefaultTopBar(model: FreezerModel, tab: Tab, lastTab: Tab? = null, nextTab: Tab? = null) {
        with(model) {
            TopAppBar(
                title          = { Text(tab.title) },
                navigationIcon = { if(lastTab != null) { BackToScreenIcon(this, lastTab) } },
                actions        = { if (nextTab != null) {
                    IconButton(onClick = { currentTab = nextTab}) {
                        Icon(Icons.Filled.ArrowForward, "ArrowForward")
                    }
                }
                }
            )
        }
    }

    @Composable
    fun GoHomeFAB(model: FreezerModel) {
        with(model) {
            FloatingActionButton(
                onClick = { currentScreen = Screen.HOMESCREEN }
            ) { Icon(Icons.Filled.Home, "HITS") }
        }
    }

    @Composable
    fun Bar(title: String) {
    TopAppBar(title = { Text(title) })
    }

    @Composable
    fun DefaultScreen(model: FreezerModel, tab: Tab, lastTab: Tab? = null, nextTab: Tab? = null) {
        Scaffold(topBar                       = { DefaultTopBar(model      = model,
            tab     = tab,
            lastTab = lastTab,
            nextTab = nextTab) },
            floatingActionButton         = { GoHomeFAB(model) },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = true
        ){
            DefaultBody(tab)
        }
    }

    @Composable
    fun DrawerIcon(scaffoldState: ScaffoldState) {
        val scope = rememberCoroutineScope()

        IconButton(onClick = { scope.launch{scaffoldState.drawerState.open() }}) {
            Icon(Icons.Filled.Menu, "Menu")
        }
    }

    @Composable
    fun BackToScreenIcon(model: FreezerModel, tab: Tab) {
        with(model) {
            IconButton(onClick = {  currentTab =tab }) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }
    }



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongPane(song: Song, model: FreezerModel) {
    with(song) {
        Card(modifier  = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .clickable {
                model.currentScreen = Screen.PLAYERSCREEN
                model.currentSong = song
                model.playerMode = true
                //song.albumImage = requestImage()
            },
            elevation = 4.dp,
        ) {
            Row(modifier            = Modifier.padding(10.dp)) {
                Column(modifier            = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Heading(title)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Subheading(text     = artist,
                            modifier = Modifier.align(Alignment.CenterStart))

                    }
                }
                Image(bitmap = albumImage, contentDescription = "")
            }

        }
    }
}

@Composable
fun ArtistPane(song: Song, model: FreezerModel){

    with(song) {
        Column() {
            Card(modifier  = Modifier
                .padding(top = 40.dp, start = 12.dp, end = 12.dp)
                .height(100.dp)
                .width(100.dp)
                .clickable {
                    //model.currentScreen = Screen.PLAYERSCREEN
                    //model.currentSong = song
                    //song.albumImage = requestImage()
                },
                elevation = 4.dp,
                shape = RoundedCornerShape(30.dp)
            ) {

                Image(artistImage,
                    contentDescription = "",
                    modifier = Modifier.height(200.dp))


            }
            
            Text(text = artist)
        }
        }
        
    }



fun requestImage(url: String): ImageBitmap {
    return try {
        bitmap(url).asImageBitmap()
    } catch (e: Exception) {
        DEFAULT_ICON.asImageBitmap()
    }
}

@Composable
fun CurrentlyPlaying(model: FreezerModel){

}

@Composable
fun DefaultBody(tab: Tab) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {
        /*
    }
        Image(painter            = painterResource(screen.resId),
            contentDescription = screen.title,
            contentScale       = ContentScale.FillWidth,
            modifier           = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(20.dp))
        )
    }

         */
    }





}

