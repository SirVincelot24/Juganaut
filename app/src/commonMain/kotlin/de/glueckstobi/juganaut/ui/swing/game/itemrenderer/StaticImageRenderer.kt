package de.glueckstobi.juganaut.ui.swing.game.itemrenderer

import de.glueckstobi.juganaut.bl.worlditems.*
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import java.awt.Graphics
import java.awt.Image
import javax.swing.ImageIcon

/**
 * Zeichnet ein Spiel als statisches Bild auf dem Bildschirm.
 */
object StaticImageRenderer : WorldItemRenderer {

    private val playerImage = loadImage("/drawables/cat.png")
    private val dirtImage = loadImage("/drawables/dirt.png")
    private val rockImage = loadImage("/drawables/rock.png")
    private val monsterImage = loadImage("/drawables/monster.png")
    private val monsterSleepImage = loadImage("/textures/monster-sleeping.png")
    private val diamondimage = loadImage("/textures/diamond.png")
    private val bombImage = loadImage("/textures/bombe.png")
    private val bombActiveImage = loadImage("/textures/bombe-active.png")


    /**
     * Lädt das Bild mit dem angegebenen Datei-Namen.
     */
    fun loadImage(filename: String): Image {
        val rawImage = ImageIcon(javaClass.getResource(filename)).image
        val scaledImage = rawImage.getScaledInstance(WorldRenderer.fieldRenderSize, WorldRenderer.fieldRenderSize, 0)
        return scaledImage
    }

    /**
     * Malt das angegebene Element auf den Bildschirm, wenn möglich.
     */
    override fun renderItem(
        item: WorldItem,
        renderX: Int,
        renderY: Int,
        renderWidth: Int,
        renderHeight: Int,
        g: Graphics,
        worldRenderer: WorldRenderer
    ) {
        val image = getImage(item)
        image?.let {
            g.drawImage(image, renderX, renderY, worldRenderer)
        }
    }

    /**
     * Gibt das richtige Bild für das angegebene Spiel-Element zurück.
     * Wenn es kein Bild dafür gibt, wird null zurückgegeben.
     */
    private fun getImage(item: WorldItem): Image? {
        return when (item) {
            is Player -> playerImage
            is Rock -> rockImage
            is Bomb -> {
                if (item.active) {
                    bombActiveImage
                } else {
                    bombImage
                }
            }

            is Monster -> {
                if (item.sleeping) {
                    monsterSleepImage
                } else {
                    monsterImage
                }
            }

            Dirt -> dirtImage
            EmptyField -> null
            Diamond -> diamondimage
        }
    }
}