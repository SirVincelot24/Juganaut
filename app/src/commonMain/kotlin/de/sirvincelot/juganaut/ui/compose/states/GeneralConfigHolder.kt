package de.sirvincelot.juganaut.ui.compose.states

import de.sirvincelot.juganaut.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Enthält die Daten für die allgemeinen Einstellungen.
 */
class GeneralConfigHolder {

    /**
     * Interner State für den ausgewählten Index der Theme-Auswahl
     */
    private var themeModeState = MutableStateFlow(ThemeMode.SYSTEM)

    /**
     * Theme-Auswahl
     */
    var themeMode: StateFlow<ThemeMode> = themeModeState.asStateFlow()

    fun setThemeMode(themeMode: ThemeMode) {
        themeModeState.value = themeMode

        println("Changed Theme to $themeMode")
    }
}