package de.sirvincelot.juganaut.ui.compose

import androidx.activity.compose.setContent
import de.sirvincelot.juganaut.MainActivity

object MainGuiAndroidCompose {

    fun startPlaying(activity: MainActivity) {
        activity.setContent {
            MainGuiCommon(true)
        }
    }
}

