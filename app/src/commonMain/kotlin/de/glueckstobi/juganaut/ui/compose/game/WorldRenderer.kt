package de.glueckstobi.juganaut.ui.compose.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
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
import kotlin.math.min

/**
 * Größe eines Feldes auf dem Bildschirm
 */
private val fieldRenderSize = 45

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

    Canvas(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        val scaleFactor = calculateScaleFactor(size, world.size, fieldRenderSize)
        scale(scaleFactor, pivot = Offset(0f, 0f)) {
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
    }
}

private fun calculateScaleFactor(canvasSize: Size, worldSize: de.glueckstobi.juganaut.bl.space.Size, fieldSize: Int): Float {
    val worldWidthPx = worldSize.width * fieldSize
    val worldHeightPx = worldSize.height * fieldSize
    val scaleX = canvasSize.width / worldWidthPx
    val scaleY = canvasSize.height / worldHeightPx
    return min(scaleX, scaleY)
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
