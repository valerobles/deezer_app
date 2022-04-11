package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.json.JSONObject
import org.junit.Test

internal class AlbumTests {
    private val AlbumsAsString = """
        {
        "id": 302127,
        "title": "Discovery",
        "cover": "https://api.deezer.com/album/302127/image",
        "cover_medium": "https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/250x250-000000-80-0-0.jpg",
        "tracklist": "https://api.deezer.com/album/302127/tracks",
        "artist": {
           "name": "Daft Punk"
                    }
        }
        """.trimIndent()


    @Test
    fun testConstructor() {

        val albumAsJSON = JSONObject(AlbumsAsString)


        val album = Album(albumAsJSON)


        with(album) {
            assertEquals("Discovery", title)
            assertEquals(302127, id)
           // assertEquals("Daft Punk", artist)
            assertTrue(tracklist.isNotEmpty())
            assertEquals(cover_medium,"https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/250x250-000000-80-0-0.jpg")
        }
    }

}