package de.sirvincelot.juganaut.bl.logic

import de.sirvincelot.juganaut.bl.space.Direction

/**
 * Ein Steuer-Kommando des Spielers.
 */
sealed interface PlayerInput

/**
 * Steuer-Kommando des Spielers, um sich zu bewegen.
 * @param direction die Richtung, in die sich der Spieler bewegen will.
 */
data class  PlayerMovement(val direction: Direction) : PlayerInput