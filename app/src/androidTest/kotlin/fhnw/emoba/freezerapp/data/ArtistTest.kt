package fhnw.emoba.freezerapp.data

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.json.JSONObject
import org.junit.Test

internal class ArtistsTest {
    private val ArtistsAsString = """
        {
           "id": "27",
           "name": "Daft Punk",
           "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg",
           "tracklist": "https://api.deezer.com/artist/27/top?limit=50"
        }
        """.trimIndent()

    @Test
    fun testConstructor() {

        val artistAsJSON = JSONObject(ArtistsAsString)


        val artist = Artist(artistAsJSON)


        with(artist) {
            assertEquals("Daft Punk", name)
            assertEquals(27, id)
            assertTrue(tracklist.isNotEmpty())
            assertEquals(picture_medium,"https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg")
        }
    }

}