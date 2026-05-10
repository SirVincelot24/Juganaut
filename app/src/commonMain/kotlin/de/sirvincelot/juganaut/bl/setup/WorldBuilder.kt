package de.sirvincelot.juganaut.bl.setup

import de.sirvincelot.juganaut.bl.Game
import de.sirvincelot.juganaut.bl.World
import de.sirvincelot.juganaut.bl.space.Coord
import de.sirvincelot.juganaut.bl.space.Direction
import de.sirvincelot.juganaut.bl.worlditems.Bomb
import de.sirvincelot.juganaut.bl.worlditems.Diamond
import de.sirvincelot.juganaut.bl.worlditems.Dirt
import de.sirvincelot.juganaut.bl.worlditems.Monster
import de.sirvincelot.juganaut.bl.worlditems.Player
import de.sirvincelot.juganaut.bl.worlditems.Rock
import de.sirvincelot.juganaut.bl.worlditems.WorldItem
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Kann eine neue Welt oder ein Spiel erstellen
 */
class WorldBuilder {

    /**
     * Erstellt ein neues Spiel.
     */
    fun createGame(config: WorldBuilderConfiguration): Game {
        val world = createWorld(config)
        val diamonds = world.count { it is Diamond }
        return Game(world, diamonds)
    }

    /**
     * Erstellt eine neue Welt
     */
    fun createWorld(config: WorldBuilderConfiguration): World {
        val playerX = config.playerCoord.x
        val playerY = config.playerCoord.y
        val world = World(config.worldSize)
        createItems(world, config.rockCountRange, playerX, playerY) { Rock() }
        createItems(world, config.monsterCountRange, playerX, playerY) { Monster() }
        createItems(world, config.bombsCountRange, playerX, playerY) { Bomb() }
        createItems(world, config.diamondsCountRange, playerX, playerY) { Diamond }

        val playerCoord = Coord(playerX, playerY)
        world.setField(playerCoord, Player())
        val nextToPlayer = arrayOf(
            playerCoord.move(Direction.Up),
            playerCoord.move(Direction.Left),
            playerCoord.move(Direction.Down),
            playerCoord.move(Direction.Right)
        )
        nextToPlayer.forEach {
            when(world.getField(it)) {
                Diamond -> Unit
                else -> world.setField(it, Dirt)
            }
        }
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
     * @param world die Welt, wo eine gültige Koordinate gesucht wird
     * @param playerY Die Y-Koordinate wo der Spieler steht
     */
    private fun getValidYCoordinate(world: World, playerY: Int): Int {
        var coord = Random.nextInt(world.validYRange)
        while (coord == playerY) {
            coord = Random.nextInt(world.validYRange)
        }
        return coord
    }

    /**
     * findet eine gültige X-Koordinate, wo Items spawnen können
     * @param world die Welt, wo eine gültige Koordinate gesucht wird
     * @param playerX Die X-Koordinate wo der Spieler steht
     */
    private fun getValidXCoordinate(world: World, playerX: Int): Int {
        var coord = Random.nextInt(world.validXRange)
        while (coord == playerX) {
            coord = Random.nextInt(world.validXRange)
        }
        return coord
    }
}