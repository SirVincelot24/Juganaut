package de.glueckstobi.juganaut.ui.compose.states

import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState

interface WorldRendererConfigHolder {

    val autoScale : MutableState<Boolean>

    val scaleFactor : MutableFloatState

    val edgeDistanceForScroll : MutableIntState
}