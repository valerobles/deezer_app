package fhnw.emoba.freezerapp.data

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.json.JSONObject
import org.junit.Test

internal class RadioTests {
    private val RadioAsString = """
        {
           "id": "37151",
           "title": "Hits",
           "picture_medium": "https://e-cdns-images.dzcdn.net/images/misc/235ec47f2b21c3c73e02fce66f56ccc5/250x250-000000-80-0-0.jpg",
           "tracklist": "https://api.deezer.com/radio/37151/tracks"
        }
        """.trimIndent()

    @Test
    fun testConstructor() {

        val radioAsJSON = JSONObject(RadioAsString)


        val radio = Radio(radioAsJSON)


        with(radio) {
            assertEquals("Hits", title)
            assertEquals(37151, id)
            assertTrue(tracklist.isNotEmpty())
            assertEquals(picture_medium,"https://e-cdns-images.dzcdn.net/images/misc/235ec47f2b21c3c73e02fce66f56ccc5/250x250-000000-80-0-0.jpg")
        }
    }

}