package de.sirvincelot.juganaut.ui.compose

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder

object DesktopWorldRendererConfigHolder : WorldRendererConfigHolder {

    override val autoScale = mutableStateOf(true)

    override val scaleFactor = mutableFloatStateOf(1f)

    override val edgeDistanceForScroll = mutableIntStateOf(3)
}