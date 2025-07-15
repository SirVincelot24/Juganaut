package de.glueckstobi.juganaut.ui.compose.states

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class WorldRendererConfigHolder {

    val autoScale = mutableStateOf(false)

    val scaleFactor = mutableFloatStateOf(1f)

    val edgeDistanceForScroll = mutableIntStateOf(3)
}