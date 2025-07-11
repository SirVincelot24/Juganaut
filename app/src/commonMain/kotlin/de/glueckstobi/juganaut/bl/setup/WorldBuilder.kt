package de.glueckstobi.juganaut.bl.setup

import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.space.Direction
import de.glueckstobi.juganaut.bl.worlditems.Bomb
import de.glueckstobi.juganaut.bl.worlditems.Diamond
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.Monster
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.bl.worlditems.Rock
import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Kann eine neue Welt oder ein Spiel erstellen.
 */
class WorldBuilder() {

    /**
     * Erstellt ein neues Spiel.
     */
    fun createGame(config: WorldBuilderConfiguration): Game {
        val world = createWorld(config)
        val diamonds = world.count { it is Diamond }
        return Game(world, diamonds)
    }

    /**
     * Erstellt eine neues Welt
     */
    fun createWorld(config: WorldBuilderConfiguration): World {
        val playerX = config.playerCoord.x
        val playerY = config.playerCoord.y
        val world = World(config.worldSize)
        createItems(world, config.rockCountRange, playerX, playerY) { Rock() }
        createItems(world, config.monsterCountRange, playerX, playerY) { Monster() }
        createItems(world, config.diamondsCountRange, playerX, playerY) { Diamond }
        createItems(world, config.bombsCountRange, playerX, playerY) { Bomb() }

        val playerCoord = Coord(playerX, playerY)
        world.setField(playerCoord, Player())
        val oben = playerCoord.move(Direction.Up)
        val links = playerCoord.move(Direction.Left)
        val unten = playerCoord.move(Direction.Down)
        val rechts = playerCoord.move(Direction.Right)
        world.setField(oben, Dirt)
        world.setField(links, Dirt)
        world.setField(unten, Dirt)
        world.setField(rechts, Dirt)
        return world
    }

    /**
     * erstellt die Items für ein Spiel
     * @param world Die Welt, wo die Items gespawnt werden
     * @param itemCountRange Wie viele Items gespawnt werden
     * @param playerX Die X-Koordinate wo der Spieler steht
     * @param playerY Die Y-Koordinate wo der Spieler steht
     * @param itemFactory Welches Item gespawnt wird
     */
    private fun <T : WorldItem> createItems(world: World, itemCountRange: IntRange, playerX: Int, playerY: Int, itemFactory: () -> T) {
        val itemCount = Random.nextInt(itemCountRange)
        repeat((1..itemCount).count()) {
            world.setField(
                Coord(getValidXCoordinate(world, playerX), getValidYCoordinate(world, playerY)),
                itemFactory()
            )
        }
    }

    /**
     * findet eine gültige Y-Koordinate, wo Items spawnen können
     * (gültig heißt: nicht neben dem Spieler)
     * @param world die Welt, wo eine gültige Koordinate gesucht wird
     * @param playerY Die Y-Koordinate wo der Spieler steht
     */
    private fun getValidYCoordinate(world: World, playerY: Int): Int {
        while (true) {
            val y = Random.nextInt(world.validYRange)
            if (y in playerY-1..playerY+1) {
                continue
            } else {
                return y
            }
        }
    }

    /**
     * findet eine gültige X-Koordinate, wo Items spawnen können
     * (gültig heißt: nicht neben dem Spieler)
     * @param world die Welt, wo eine gültige Koordinate gesucht wird
     * @param playerX Die X-Koordinate wo der Spieler steht
     */
    private fun getValidXCoordinate(world: World, playerX: Int): Int {
        while (true) {
            val x = Random.nextInt(world.validXRange)
            if (x in playerX-1..playerX+1) {
                continue
            } else {
                return x
            }
        }
    }
}