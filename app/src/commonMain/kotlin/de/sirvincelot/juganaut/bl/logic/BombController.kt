package de.sirvincelot.juganaut.bl.logic

import de.sirvincelot.juganaut.bl.Game
import de.sirvincelot.juganaut.bl.space.Coord
import de.sirvincelot.juganaut.bl.space.Direction
import de.sirvincelot.juganaut.bl.worlditems.*
import de.sirvincelot.juganaut.getPlatform
import de.sirvincelot.juganaut.ui.audio.SFXAudioSample

/**
 * Steuert das Verhalten der Bomben
 */
class BombController(val game: Game) {


    fun bombsTick() {
        val bombList = game.world.findAll { it is Bomb }
        bombList.forEach {
            tick(it)
        }
    }

    private fun tick(coord: Coord) {
        val bomb = game.world.getField(coord) as Bomb
        if (!bomb.active) {
            return
        }
        bomb.countdown -= 1
        tryExplode(bomb, coord)


    }

    fun tryExplode(bomb: Bomb, coord: Coord) {
        if (bomb.countdown > 0) return
        explode(bomb, coord)
    }


    fun explode(bomb: Bomb, coord: Coord) {
        val explosionField = listOf(
            Coord(coord.x - 1, coord.y),
            Coord(coord.x, coord.y + 1),
            Coord(coord.x + 1, coord.y),
            Coord(coord.x, coord.y - 1),
            Coord(coord.x, coord.y),
            Coord(coord.x - 1, coord.y - 1),
            Coord(coord.x - 1, coord.y + 1),
            Coord(coord.x + 1, coord.y + 1),
            Coord(coord.x + 1, coord.y - 1),
        )

        explosionField.forEach { c ->
            if (game.world.isValid(c)) {
                when (val item = game.world.getField(c)) {
                    is Bomb -> activateBomb(bomb, item, c)
                    Diamond -> clearField(c)
                    Dirt -> clearField(c)
                    EmptyField -> {}
                    is Monster -> clearField(c)
                    is Player -> killPlayer(c)
                    is Rock -> clearField(c)
                }
            }

        }
        getPlatform().audioPlayer?.playSfx(SFXAudioSample.BombExplode)
    }

    private fun clearField(c: Coord) {
        game.world.setField(c, EmptyField)
    }

    fun activateBomb(explosionBomb: Bomb, destinationBomb: Bomb, c: Coord) {
        if (explosionBomb != destinationBomb) {
            destinationBomb.active = true
        } else {
            clearField(c)
        }
    }

    fun killPlayer(c: Coord) {
        game.gameOver(Explosion())
        clearField(c)
    }


    /**
     * Lässt alle Bomben nach unten fallen.
     * Wird einmal pro Runde aufgerufen.
     */
    fun bombsFall() {
        val bombs = game.world.findAll { it is Bomb }
        // sort bombs, so we start with the lowest bombs
        val sortedBombs = bombs.sortedWith { c1, c2 ->
            (c1.y - c2.y).takeIf { it != 0 } ?: (c1.x - c2.x)
        }
        sortedBombs.forEach {
            tryFall(it)
        }
    }

    /**
     * Versucht, die Bombe nach unten fallen zu lassen.
     * @param source Koordinate einer Bombe.
     */
    private fun tryFall(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Bomb) {
            return // should not happen
        }
        val destination = source.move(Direction.Down)
        if (!game.world.isValid(destination)) {
            return
        }
        val itemBelow = game.world.getField(destination)
        when (itemBelow) {
            EmptyField -> fall(item, source, destination)
            is Rock, Dirt -> stopFalling(item)
            is Player, is Monster -> stopFalling(item)
            is Bomb -> stopFalling(item)
            Diamond -> stopFalling(item)
        }
    }

    /**
     * Die Bombe fällt 1 Feld nach unten.
     * @param bomb die Bombe
     * @param source die Ursprungs-Koordinate
     * @param destination die Ziel-Koordinate
     */
    private fun fall(bomb: Bomb, source: Coord, destination: Coord) {
        bomb.falling = true
        game.world.setField(source, EmptyField)
        game.world.setField(destination, bomb)
    }

    /**
     * Die Bombe fällt nicht mehr weiter.
     * @param bomb Die Bombe
     */
    private fun stopFalling(bomb: Bomb) {
        if (bomb.falling) {
            bomb.active = true
        }
        bomb.falling = false
    }


}
