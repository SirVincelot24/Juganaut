package de.sirvincelot.juganaut.ui.compose.states

import androidx.compose.runtime.mutableStateOf
import de.sirvincelot.juganaut.ui.theme.ThemeMode

/**
 * Enthält die Daten für die allgemeinen Einstellungen.
 */
class GeneralConfigHolder {

    /**
     * Interner State für den ausgewählten Index der Theme-Auswahl
     */
    private var themeIndexState = mutableStateOf(ThemeMode.DARK)

    /**
     * Theme-Auswahl
     */
    var themeIndex: ThemeMode
        get() = themeIndexState.value
        set(value) {
            themeIndexState.value = value
        }
}