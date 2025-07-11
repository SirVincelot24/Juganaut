package de.glueckstobi.juganaut.bl.setup

import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Size

/**
 * Die Konfiguration für die Erstellung einer neuen Welt.
 */
data class WorldBuilderConfiguration(
    /**
     * Die Größe der Welt.
     */
    val worldSize: Size = Size(20, 20),

    /**
     * Zahlenbereich, wieviele Steine es geben kann.
     */
    val rockCountRange: IntRange = (20..50),

    /**
     * Zahlenbereich, wieviele Monster es geben kann.
     */
    val monsterCountRange: IntRange = (20..50),

    /**
     * Zahlenbereich, wieviele Bombs es geben kann.
     */
    val bombsCountRange: IntRange = (10..20),

    /**
     * Zahlenbereich, wieviele Diamanten es geben kann.
     */
    val diamondsCountRange: IntRange = (10..30),

    /**
     * Die Koordinate wo der Spieler gespawnt wird
     */
    val playerCoord: Coord,
)