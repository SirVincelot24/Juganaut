package de.glueckstobi.juganaut.ui.compose

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import de.glueckstobi.juganaut.ui.compose.states.WorldRendererConfigHolder

object AndroidWorldRendererConfigHolder : WorldRendererConfigHolder {
    override val autoScale = mutableStateOf(false)

    override val scaleFactor = mutableFloatStateOf(1f)

    override val edgeDistanceForScroll = mutableIntStateOf(3)
}