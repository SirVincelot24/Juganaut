package de.sirvincelot.juganaut.ui.compose.states

import androidx.compose.runtime.mutableStateOf
import de.sirvincelot.juganaut.bl.setup.WorldBuilderConfiguration

class WorldBuilderConfigHolder {

    /**
     * Die Konfiguration, wenn ein neues Spiel gestartet wird.
     */
    val worldBuilderConfig = mutableStateOf(WorldBuilderConfiguration())
}