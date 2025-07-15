package de.glueckstobi.juganaut.ui.compose.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import de.glueckstobi.juganaut.bl.World
import de.glueckstobi.juganaut.bl.space.Coord
import de.glueckstobi.juganaut.bl.worlditems.Bomb
import de.glueckstobi.juganaut.bl.worlditems.Diamond
import de.glueckstobi.juganaut.bl.worlditems.Dirt
import de.glueckstobi.juganaut.bl.worlditems.EmptyField
import de.glueckstobi.juganaut.bl.worlditems.Monster
import de.glueckstobi.juganaut.bl.worlditems.Player
import de.glueckstobi.juganaut.bl.worlditems.Rock
import de.glueckstobi.juganaut.bl.worlditems.WorldItem
import juganaut.app.generated.resources.Res
import juganaut.app.generated.resources.bombe
import juganaut.app.generated.resources.bombe_active
import juganaut.app.generated.resources.cat
import juganaut.app.generated.resources.diamond
import juganaut.app.generated.resources.dirt
import juganaut.app.generated.resources.monster
import juganaut.app.generated.resources.monster_sleeping
import juganaut.app.generated.resources.rock
import org.jetbrains.compose.resources.imageResource
import kotlin.math.max
import kotlin.math.min

/**
 * Größe eines Feldes auf dem Bildschirm
 */
private val fieldRenderSize = 45

private val translationMinVisibleFields = 3

private val translationEmptyBorder = 10f

/**
 * Sammlung der verfügbaren Images
 */
data class Images(
    val player: ImageBitmap,
    val dirt: ImageBitmap,
    val rock: ImageBitmap,
    val monster: ImageBitmap,
    val monsterSleep: ImageBitmap,
    val diamond: ImageBitmap,
    val bomb: ImageBitmap,
    val bombActive: ImageBitmap,
)

/**
 * Gibt das richtige Bild für das angegebene Spiel-Element zurück.
 * Wenn es kein Bild dafür gibt, wird null zurückgegeben.
 */
fun getImageForItem(item: WorldItem, images: Images): ImageBitmap? {
    return when (item) {
        is Player -> images.player
        is Rock -> images.rock
        is Bomb -> {
            if (item.active) {
                images.bombActive
            } else {
                images.bomb
            }
        }

        is Monster -> {
            if (item.sleeping) {
                images.monsterSleep
            } else {
                images.monster
            }
        }

        Dirt -> images.dirt
        Diamond -> images.diamond
        EmptyField -> null
    }
}

/**
 * Malt das ganze Spielfeld auf den Bildschirm.
 */
@Composable
fun WorldRenderer(world: World, tickCount: MutableIntState) {
    val images = Images(
        player = imageResource(Res.drawable.cat),
        dirt = imageResource(Res.drawable.dirt),
        rock = imageResource(Res.drawable.rock),
        monster = imageResource(Res.drawable.monster),
        monsterSleep = imageResource(Res.drawable.monster_sleeping),
        diamond = imageResource(Res.drawable.diamond),
        bomb = imageResource(Res.drawable.bombe),
        bombActive = imageResource(Res.drawable.bombe_active),
    )

    var translation = remember { Offset(0f, 0f) }

    Canvas(modifier = Modifier.fillMaxSize().clipToBounds().background(Color.Black)) {
        val scaleFactor = 2f //calculateScaleFactor(size, world.size)
        scale(scaleFactor, pivot = Offset(0f, 0f)) {
            val newTranslation = calculateTranslation(size, world, scaleFactor, translation)
            translation = newTranslation
            translate(left = newTranslation.x, top = newTranslation.y) {
                drawWorld(world, images, tickCount)
            }
        }
    }
}

private fun DrawScope.drawWorld(
    world: World,
    images: Images,
    tickCount: MutableIntState
) {
    tickCount.value // tickCount triggers a repaint
    world.validYRange.forEach { y ->
        world.validXRange.forEach { x ->
            val item = world.getField(Coord(x, y))
            val image = getImageForItem(item, images)
            if (image != null) {
                renderItem(image, x, y)
            }
        }
    }
}

/**
 * Berechnet den Skalierungsfaktor, so dass das gesamte Spielfeld auf dem Bildschirm passt.
 * @param canvasSize Größe des Render-Bereichs in Pixel
 * @param worldSize Größe der Spielwelt in Anzahl der Felder
 * @return Gibt den Skalierungsfaktor zurück.
 */
private fun calculateScaleFactor(canvasSize: Size, worldSize: de.glueckstobi.juganaut.bl.space.Size): Float {
    val worldWidthPx = worldSize.width * fieldRenderSize
    val worldHeightPx = worldSize.height * fieldRenderSize
    val scaleX = canvasSize.width / worldWidthPx
    val scaleY = canvasSize.height / worldHeightPx
    return min(scaleX, scaleY)
}

/**
 * Berechnet die Translation (Verschiebung), so dass der Spieler immer im sichtbaren Bereich ist.
 * @param canvasSize Größe des Render-Bereichs in Pixel
 * @param world die Spiel-Welt
 * @param scaleFactor Skalierungs-Faktor beim Rendern
 * @param currentTranslation die bisherige Translation
 * @return Gibt die neue Translation zurück
 */
private fun calculateTranslation(canvasSize: Size, world: World, scaleFactor: Float, currentTranslation: Offset): Offset {
    val playerFieldPos = world.find { it is Player }
    if (playerFieldPos != null) {
        val translateX = calculateTranslationSingleDimension(
            canvasSize = canvasSize.width,
            worldSize = world.width,
            playerPos = playerFieldPos.x,
            scaleFactor = scaleFactor,
            currentTranslation = currentTranslation.x
        )
        val translateY = calculateTranslationSingleDimension(
            canvasSize = canvasSize.height,
            worldSize = world.height,
            playerPos = playerFieldPos.y,
            scaleFactor = scaleFactor,
            currentTranslation = currentTranslation.y
        )
        return Offset(translateX, translateY)
    } else {
        return currentTranslation
    }

}

/**
 * Berechnet die Translation (Verschiebung) in einer Dimension (entweder horizontal oder vertikal).
 * @param canvasSize Größe des Render-Bereichs in Pixel
 * @param worldSize Größe der Welt in Anzahl der Felder
 * @param playerPos Position des Spielers (als Feld-Index)
 * @param scaleFactor Skalierungsfaktor beim rendern
 * @param currentTranslation die bisherige Translation
 * @return Gibt die neue Translation zurück
 */
private fun calculateTranslationSingleDimension(canvasSize: Float, worldSize: Int, playerPos: Int, scaleFactor: Float, currentTranslation: Float): Float {
    var translate = currentTranslation
    val playerPosPx = playerPos * fieldRenderSize * scaleFactor + (translate * scaleFactor)
    val minPlayerPosPx = translationMinVisibleFields * fieldRenderSize * scaleFactor
    val maxPlayerPosPx = canvasSize - (translationMinVisibleFields + 1) * fieldRenderSize * scaleFactor
    val minTranslate = -(worldSize * fieldRenderSize * scaleFactor - canvasSize) / scaleFactor

    if (playerPosPx < minPlayerPosPx) {
        translate += (minPlayerPosPx - playerPosPx) / scaleFactor
        translate = min(translationEmptyBorder, translate)
    } else if (playerPosPx > maxPlayerPosPx) {
        translate -= (playerPosPx - maxPlayerPosPx) / scaleFactor
        translate = max(minTranslate - translationEmptyBorder, translate)
    }
    return translate
}

/**
 * Malt ein einzelnes Spiel-Element.
 * @param image das Bild, das gemalt werden soll
 * @param x die X-Koordinate (horizontal), an der gemalt werden soll.
 * @param y die Y-Koordinate (vertikal), an der gemalt werden soll.
 */
private fun DrawScope.renderItem(image: ImageBitmap, x: Int, y: Int) {
    val renderX = x * fieldRenderSize
    val renderY = y * fieldRenderSize
    drawImage(
        image,
        dstOffset = IntOffset(renderX, renderY),
        dstSize = IntSize(fieldRenderSize, fieldRenderSize)
    )
}
