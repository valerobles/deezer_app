package fhnw.emoba.freezerapp.model


import fhnw.emoba.freezerapp.data.DeezerService
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.data.impl.DeezerAPIService
import junit.framework.Assert.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

internal class ModelTest {

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val SongAsString = """
        {
        "id": "1109737",
     
      "title": "The Real Slim Shady",

      "preview": "https://cdns-preview-d.dzcdn.net/stream/c-d28ee67c24d60e740866c7709d772f55-12.mp3",
     
      "artist": {
         "name": "Eminem",
         "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg"
      },
      "album": {
         "title": "Curtain Call: The Hits",
        "cover_medium": "https://e-cdns-images.dzcdn.net/images/cover/e2b36a9fda865cb2e9ed1476b6291a7d/250x250-000000-80-0-0.jpg"
      }
    }
    
        """.trimIndent()

    var deezerService: DeezerService = DeezerAPIService()
    var model: FreezerModel = FreezerModel(deezerService)
    val songAsJSON = JSONObject(SongAsString)
    var song: Song = Song(songAsJSON)

    @Before
    fun setUp() {
        val songAsJSON = JSONObject(SongAsString)
        song = Song(songAsJSON)
    }

    @Test
    fun testStartUp(){
        assertTrue(model.listOfRadio.isEmpty())
        assertTrue(model.listOfArtists.isEmpty())
        model.startUp()
        modelScope.launch {
            assertTrue(model.listOfRadio.isNotEmpty())
            assertTrue(model.listOfArtists.isNotEmpty())
        }

    }

    @Test
    fun testAddRemoveFavorite() {

     assertTrue(model.favoriteSongs.isEmpty() )
     assertEquals(false, song.liked)
     model.addRemoveFavorite(song)
     assertEquals(true, song.liked)
     assertNotNull(model.favoriteSongs.isNotEmpty())
     model.addRemoveFavorite(song)
     assertEquals(false, song.liked)

    }
}