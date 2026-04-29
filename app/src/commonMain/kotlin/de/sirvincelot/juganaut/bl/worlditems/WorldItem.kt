package de.sirvincelot.juganaut.bl.worlditems

/**
 * Die verschiedenen Elemente auf den Feldern des Spiels.
 */
sealed interface WorldItem

/**
 * Erde.
 */
object Dirt : WorldItem

/**
 * Ein leeres Feld
 */
object EmptyField : WorldItem


object Diamond : WorldItem





