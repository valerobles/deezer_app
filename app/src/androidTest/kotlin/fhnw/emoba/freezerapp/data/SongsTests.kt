package fhnw.emoba.freezerapp.data

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.json.JSONObject
import org.junit.Test

internal class SongsTests {
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

    @Test
    fun testConstructor() {

        val songAsJSON = JSONObject(SongAsString)



        val song = Song(songAsJSON)


        with(song) {
            assertEquals("The Real Slim Shady", title)
            assertEquals(1109737, id)
            assertEquals("Eminem", artist)
            assertTrue(songPreview.isNotEmpty())
            assertEquals(album_cover,"https://e-cdns-images.dzcdn.net/images/cover/e2b36a9fda865cb2e9ed1476b6291a7d/250x250-000000-80-0-0.jpg")
        }


    }

}