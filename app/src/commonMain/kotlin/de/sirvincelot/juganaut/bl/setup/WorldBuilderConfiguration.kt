package de.sirvincelot.juganaut.bl.setup

import de.sirvincelot.juganaut.bl.space.Coord
import de.sirvincelot.juganaut.bl.space.Size

/**
 * Die Konfiguration für die Erstellung einer neuen Welt.
 */
data class WorldBuilderConfiguration(
    /**
     * Die Größe der Welt.
     */
    val worldSize: Size = Size(20, 20),

    /**
     * Zahlenbereich, wie viele Steine es geben kann.
     */
    val rockCountRange: IntRange = (20..50),

    /**
     * Zahlenbereich, wie viele Monster es geben kann.
     */
    val monsterCountRange: IntRange = (20..50),

    /**
     * Zahlenbereich, wie viele Bombs es geben kann.
     */
    val bombsCountRange: IntRange = (10..20),

    /**
     * Zahlenbereich, wie viele Diamanten es geben kann.
     */
    val diamondsCountRange: IntRange = (10..30),

    /**
     * Die Koordinate wo der Spieler gespawnt wird
     */
    val playerCoord: Coord = Coord(10, 10),
)