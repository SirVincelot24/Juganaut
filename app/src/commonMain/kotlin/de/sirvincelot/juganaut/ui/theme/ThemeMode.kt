package de.sirvincelot.juganaut.ui.theme

enum class ThemeMode(val value: Int) {
    LIGHT(0), DARK(1), SYSTEM(2);

    companion object {
        private val map = ThemeMode.entries.associateBy { it.value }
        infix fun from(value: Int): ThemeMode = map[value]!!
    }
}