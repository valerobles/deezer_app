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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.Screen
import fhnw.emoba.freezerapp.model.Tab
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
fun ImageWithRoundedCornersAndPadding(imageBitmap: ImageBitmap) {

        Spacer(Modifier.height(12.dp))
        Box(
            Modifier.clip(RoundedCornerShape(5.dp)),
            Alignment.Center
        ) {
            Image(bitmap = imageBitmap, contentDescription = "")
        }
        Spacer(Modifier.height(12.dp))

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

    @Composable
    fun DefaultBody(tab: Tab) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(15.dp),
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



        @Composable
        fun DrawerRow(model: FreezerModel, tab: Tab) {
            with(model) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(5.dp)
                )


                {
                    /*
                    //TODO: Image and text
                currentSong?.let { painterResource(id = it.imageId) }?.let {
                    Image(
                        painter = it,
                        contentDescription = currentSong!!.songTitle,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.size(48.dp).clip(RoundedCornerShape(5.dp))
                    )
                }

                     */

                    Text(text = tab.title,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(5.dp)
                            .fillMaxWidth()
                            .clickable(onClick = { currentTab = tab })
                    )
                }


                Divider()
            }


        }


        @Composable
        fun Drawer(model: FreezerModel) {
            Column {
                DrawerRow(model, Tab.HITSTAB)
                DrawerRow(model, Tab.SONGSTAB)
                DrawerRow(model, Tab.ALBUMSTAB)
                DrawerRow(model, Tab.RADIOTAB)
            }
        }





}

