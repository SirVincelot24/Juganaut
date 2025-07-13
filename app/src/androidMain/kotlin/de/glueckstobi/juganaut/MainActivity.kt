package de.glueckstobi.juganaut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import de.glueckstobi.juganaut.ui.compose.MainGuiAndroidCompose

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        MainGuiAndroidCompose.startPlaying(this)
    }

}