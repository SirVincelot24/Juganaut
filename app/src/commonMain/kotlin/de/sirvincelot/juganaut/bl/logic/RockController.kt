package de.sirvincelot.juganaut.bl.logic

import de.sirvincelot.juganaut.bl.Game
import de.sirvincelot.juganaut.bl.space.Coord
import de.sirvincelot.juganaut.bl.space.Direction
import de.sirvincelot.juganaut.bl.worlditems.*

/**
 * Steuert die Bewegung der Steine.
 */
class RockController(val game: Game) {

    /**
     * Lässt alle Steine nach unten fallen.
     * Wird einmal pro Runde aufgerufen.
     */
    fun rocksFall() {
        val rocks = game.world.findAll { it is Rock }
        // sort rocks, so we start with the lowest rocks
        val sortedRocks = rocks.sortedWith { c1, c2 ->
            (c1.y - c2.y).takeIf { it != 0 } ?: (c1.x - c2.x)
        }
        sortedRocks.forEach {
            tryFall(it)
        }
    }

    /**
     * Versucht, den Stein nach unten fallen zu lassen.
     * @param source Koordinate eines Steins.
     */
    private fun tryFall(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Rock) {
            return // should not happen
        }
        val destination = source.move(Direction.Down)
        if (!game.world.isValid(destination)) {
            return
        }
        when (val itemBelow = game.world.getField(destination)) {
            EmptyField -> fall(item, source, destination)
            is Rock, Dirt -> stopFalling(item)
            is Player, is Monster -> tryHit(item, source, destination, itemBelow)
            is Bomb -> rockOnBomb(item, itemBelow)
            Diamond -> stopFalling(item)
        }
    }

    /**
     * Der Stein fällt 1 Feld nach unten.
     * @param rock der Stein
     * @param source die Ursprungs-Koordinate
     * @param destination die Ziel-Koordinate
     */
    private fun fall(rock: Rock, source: Coord, destination: Coord) {
        rock.falling = true
        game.world.setField(source, EmptyField)
        game.world.setField(destination, rock)
    }

    /**
     * Der Stein fällt nicht mehr weiter.
     * @param rock der Stein
     */
    private fun stopFalling(rock: Rock) {
        rock.falling = false
    }

    /**
     * Wird aufgerufen, wenn unterhalb des Steins eine Figur ist.
     * Wenn der Stein fällt, wird die Figur getroffen.
     * @param rock der Stein
     * @param source die Ursprungs-Koordinate des Steins
     * @param destination die Koordinate der Figur
     * @param targetItem die Figur unterhalb des Steins.
     */
    private fun tryHit(rock: Rock, source: Coord, destination: Coord, targetItem: WorldItem) {
        if (rock.falling) {
            // oh, oh, player or monster is hit by rock
            fall(rock, source, destination)
            if (targetItem is Player) {
                game.gameOver(RockHitsPlayer(source, destination))
            }
        }
        // otherwise it's ok, rock is just laying on the head
    }

    fun rockOnBomb(rock: Rock, bomb: Bomb) {
        if (rock.falling) {
            bomb.active = true
        }
    }
}