package fhnw.emoba.freezerapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.freezerapp.data.impl.DeezerAPIService
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.FreezerUI


object FreezerApp : EmobaApp {
    private lateinit var model: FreezerModel

    override fun initialize(activity: ComponentActivity) {
        val deezerService = DeezerAPIService()
        model = FreezerModel(deezerService)
        model.startUp()

    }

    @Composable
    override fun CreateUI() {
        FreezerUI(model)
    }

}