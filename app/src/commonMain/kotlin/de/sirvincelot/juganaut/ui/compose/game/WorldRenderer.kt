package de.sirvincelot.juganaut.ui.compose.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import de.sirvincelot.juganaut.bl.World
import de.sirvincelot.juganaut.bl.space.Coord
import de.sirvincelot.juganaut.bl.worlditems.Bomb
import de.sirvincelot.juganaut.bl.worlditems.Diamond
import de.sirvincelot.juganaut.bl.worlditems.Dirt
import de.sirvincelot.juganaut.bl.worlditems.EmptyField
import de.sirvincelot.juganaut.bl.worlditems.Monster
import de.sirvincelot.juganaut.bl.worlditems.Player
import de.sirvincelot.juganaut.bl.worlditems.Rock
import de.sirvincelot.juganaut.bl.worlditems.WorldItem
import de.sirvincelot.juganaut.ui.compose.states.GameStateHolder
import de.sirvincelot.juganaut.ui.compose.states.WorldRendererConfigHolder
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
private val fieldRenderSize = 100

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
fun WorldRenderer(gameStateHolder: GameStateHolder, worldRendererConfig: WorldRendererConfigHolder) {
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

    Canvas(modifier = Modifier.fillMaxSize().clipToBounds().background(MaterialTheme.colorScheme.surfaceDim)) {
        val world = gameStateHolder.game?.world
        if (world != null) {
            val scaleFactor = if (worldRendererConfig.autoScale.value) {
                calculateScaleFactor(size, world.size)
            } else {
                worldRendererConfig.scaleFactor.floatValue
            }
            scale(scaleFactor, pivot = Offset(0f, 0f)) {
                translation = calculateTranslation(
                    canvasSize = size,
                    world = world,
                    scaleFactor = scaleFactor,
                    minVisibleEdgeFields = worldRendererConfig.edgeDistanceForScroll.intValue,
                    currentTranslation = translation
                )
                translate(left = translation.x, top = translation.y) {
                    drawWorld(world, images)
                }
            }
        }
    }
}

private fun DrawScope.drawWorld(
    world: World,
    images: Images
) {
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
 * Berechnet den Skalierungsfaktor, sodass das gesamte Spielfeld auf dem Bildschirm passt.
 * @param canvasSize Größe des Render-Bereichs in Pixel
 * @param worldSize Größe der Spielwelt in Anzahl der Felder
 * @return Gibt den Skalierungsfaktor zurück.
 */
private fun calculateScaleFactor(canvasSize: Size, worldSize: de.sirvincelot.juganaut.bl.space.Size): Float {
    val worldWidthPx = worldSize.width * fieldRenderSize
    val worldHeightPx = worldSize.height * fieldRenderSize
    val scaleX = canvasSize.width / worldWidthPx
    val scaleY = canvasSize.height / worldHeightPx
    return min(scaleX, scaleY)
}

/**
 * Berechnet die Translation (Verschiebung), sodass der Spieler immer im sichtbaren Bereich ist.
 * @param canvasSize Größe des Render-Bereichs in Pixel
 * @param world die Spiel-Welt
 * @param scaleFactor Skalierungsfaktor beim Rendern
 * @param currentTranslation die bisherige Translation
 * @return Gibt die neue Translation zurück
 */
private fun calculateTranslation(
    canvasSize: Size,
    world: World,
    scaleFactor: Float,
    minVisibleEdgeFields: Int,
    currentTranslation: Offset
): Offset {
    val playerFieldPos = world.find { it is Player }
    if (playerFieldPos != null) {
        val translateX = calculateTranslationSingleDimension(
            canvasSize = canvasSize.width,
            worldSize = world.width,
            playerPos = playerFieldPos.x,
            scaleFactor = scaleFactor,
            minVisibleEdgeFields = minVisibleEdgeFields,
            currentTranslation = currentTranslation.x
        )
        val translateY = calculateTranslationSingleDimension(
            canvasSize = canvasSize.height,
            worldSize = world.height,
            playerPos = playerFieldPos.y,
            scaleFactor = scaleFactor,
            minVisibleEdgeFields = minVisibleEdgeFields,
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
 * @param scaleFactor Skalierungsfaktor beim Rendern
 * @param currentTranslation die bisherige Translation
 * @return Gibt die neue Translation zurück
 */
private fun calculateTranslationSingleDimension(
    canvasSize: Float,
    worldSize: Int,
    playerPos: Int,
    scaleFactor: Float,
    minVisibleEdgeFields: Int,
    currentTranslation: Float
): Float {
    var translate = currentTranslation
    val playerPosPx = playerPos * fieldRenderSize * scaleFactor + (translate * scaleFactor)
    val minPlayerPosPx = minVisibleEdgeFields * fieldRenderSize * scaleFactor
    val maxPlayerPosPx = canvasSize - (minVisibleEdgeFields + 1) * fieldRenderSize * scaleFactor
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
