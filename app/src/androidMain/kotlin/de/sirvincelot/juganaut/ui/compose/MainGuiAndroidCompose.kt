package de.sirvincelot.juganaut.ui.compose

import androidx.activity.compose.setContent
import de.sirvincelot.juganaut.MainActivity
import de.sirvincelot.juganaut.ui.theme.AppTheme

object MainGuiAndroidCompose {

    fun startPlaying(activity: MainActivity) {
        activity.setContent {
            AppTheme {
                MainGuiCommon(true)
            }
        }
    }
}

