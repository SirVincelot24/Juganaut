package de.glueckstobi.juganaut.ui.compose

import androidx.activity.compose.setContent
import de.glueckstobi.juganaut.MainActivity

object MainGuiAndroidCompose {

    fun startPlaying(activity: MainActivity) {
        activity.setContent {
            MainGuiCommon(true)
        }
    }
}

