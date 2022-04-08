package fhnw.emoba.freezerapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.ui.screens.requestImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject

data class Radio(val id: Int,
                 val title: String,
                 val picture_medium: String,
                 val tracklist: String) {

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var radioImage by mutableStateOf(DEFAULT_ICON.asImageBitmap())

    fun loadImage(){
        modelScope.launch {
            radioImage = requestImage(picture_medium)
        }
    }

    constructor(json: JSONObject) : this(
        id = json.getInt("id"),
        title = json.getString("title"),
        picture_medium = json.getString("picture_medium"),
        tracklist = json.getString("tracklist"),
    )
}