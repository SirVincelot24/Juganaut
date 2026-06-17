package de.sirvincelot.juganaut.ui.compose.states

import androidx.compose.runtime.mutableIntStateOf

/**
 * Enthält die Daten für die allgemeinen Einstellungen.
 */
class GeneralConfigHolder {

    /**
     * Interner State für den ausgewählten Index der Theme-Auswahl
     */
    private var darkModeIndexState = mutableIntStateOf(0)

    /**
     * Musik-Lautstärke.
     */
    var darkModeIndex: Int
        get() = darkModeIndexState.intValue
        set(value) {
            darkModeIndexState.intValue = value
        }
}