package de.sirvincelot.juganaut.bl.logic

import de.sirvincelot.juganaut.bl.Game
import de.sirvincelot.juganaut.bl.space.Coord
import de.sirvincelot.juganaut.bl.space.Direction
import de.sirvincelot.juganaut.bl.worlditems.EmptyField
import de.sirvincelot.juganaut.bl.worlditems.Monster
import de.sirvincelot.juganaut.bl.worlditems.Player

/**
 * Steuert die Bewegungen der Monster
 */
class MonsterController(val game: Game) {

    /**
     * Bewegt jedes Monster ein Feld weiter.
     * Wird einmal pro Runde aufgerufen.
     */
    fun monstersMove() {
        val monsters = game.world.findAll { it is Monster }
        // move the monster in random order
        val shuffledMonsters = monsters.shuffled()
        shuffledMonsters.forEach {
            tryMove(it)
        }
    }

    /**
     * Bewegt das Feld an der angegebenen Koordinate weiter.
     * @param source Coordinate an der ein Monster ist.
     */
    private fun tryMove(source: Coord) {
        val item = game.world.getField(source)
        if (item !is Monster) {
            return // should not happen
        }

        if (item.sleeping) {
            tryWakeUp(source, item)
            return
        }


        val destination = source.move(item.direction)
        if (canMove(destination)) {
            val otherItem = game.world.getField(destination)
            return when (otherItem) {
                is Player -> catchPlayer(item, source, destination)
                EmptyField -> move(item, source, destination)
                else -> {}// should not happen
            }
        } else {
            turn(item, source)
        }
    }

    /**
     * Gibt true zurück, wenn das Monster auf das angegebene Feld laufen kann
     */
    private fun canMove(destination: Coord): Boolean {
        if (!game.world.isValid(destination)) {
            return false
        }
        val otherItem = game.world.getField(destination)
        return when (otherItem) {
            is Player, EmptyField -> true
            else -> false
        }
    }

    /**
     * Das Monster fängt den Spieler.
     * @param monster das Monster
     * @param source die Koordinaten des Monsters
     * @param destination die Koordinaten des Spielers
     */
    private fun catchPlayer(monster: Monster, source: Coord, destination: Coord) {
        move(monster, source, destination)
        game.gameOver(MonsterCatchesPlayer(source, destination))
    }

    /**
     * Bewegt das Monster auf das angegebene Feld.
     * @param monster das Monster
     * @param source die Ursprungs-Koordinate
     * @param destination die Ziel-Koordinate
     */
    private fun move(monster: Monster, source: Coord, destination: Coord) {
        game.world.setField(source, EmptyField)
        game.world.setField(destination, monster)
    }

    /**
     * Das Monster dreht sich und ändert seine Richtung.
     * @param monster das Monster
     * @param c the Koordinate des Monsters
     */
    private fun turn(monster: Monster, c: Coord) {
        monster.direction = getNewDirection(c)
    }

    /**
     * Berechnet die neue Richtung, wenn das Monster sich dreht.
     * @param source die Koordinate des Monsters
     * @return Gibt die neue Richtung des Monsters zurück.
     */
    private fun getNewDirection(source: Coord): Direction {
        var nextDirection = Direction.random()
        (0..4).forEach { _ ->
            val destination = source.move(nextDirection)
            if (canMove(destination)) {
                return nextDirection
            }
            val nextDirectionIndex = (nextDirection.ordinal + 1) % Direction.entries.size
            nextDirection = Direction.entries[nextDirectionIndex]
        }
        return nextDirection
    }

    fun tryWakeUp(source: Coord, item: Monster) {
        if (canWakeUp(source)) {
            item.sleeping = false
        }
    }

    fun canWakeUp(source: Coord): Boolean {
        val cup = source.move(Direction.Up)
        val cleft = source.move(Direction.Left)
        val cright = source.move(Direction.Right)
        val cdown = source.move(Direction.Down)
        return canMove(cup) or canMove(cleft) or canMove(cright) or canMove(cdown)
    }
}