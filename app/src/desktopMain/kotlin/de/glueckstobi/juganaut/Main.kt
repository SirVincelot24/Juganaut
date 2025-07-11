package de.glueckstobi.juganaut

import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.ui.swing.MainGui


fun main(args: Array<String>) {
    val providedArgs = checkArgs(args)
    val playerX = providedArgs[0].toInt()
    val playerY = providedArgs[1].toInt()
    val diamondsInGame = providedArgs[2].toInt()
    val config = WorldBuilderConfiguration(
        diamondsCountRange = (diamondsInGame..diamondsInGame),
        playerCoord = Coord(playerX, playerY)
    )
    MainGui().showMenu(config)
}

/**
 * Checkt, ob die Argumente alle vorhanden sind. Wenn nicht, dann gibt er die Standartwerte aus.
 * Diese sind: playerX = 10; playerY = 10; diamondsInGame = 3;
 */
private fun checkArgs(args: Array<String>): Array<String> {
    if (args.size == 3) {
        return args
    }
    return arrayOf("10", "10", "10")
}
