package de.sirvincelot.juganaut.ui.compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ResetIcon: ImageVector
    get() {
        val current = _resetIcon
        if (current != null) return current

        return ImageVector.Builder(
            name = "de.sirvincelot.juganaut.ui.theme.AppTheme.ResetIcon",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 960.0f,
            viewportHeight = 960.0f,
        ).apply {
            // M520 -330 v-60 h160 v60 H520Z m60 210 v-50 h-60 v-60 h60 v-50 h60 v160 h-60Z m100 -50 v-60 h160 v60 H680Z m40 -110 v-160 h60 v50 h60 v60 h-60 v50 h-60Z m111 -280 h-83 q-26 -88 -99 -144 t-169 -56 q-117 0 -198.5 81.5 T200 -480 q0 72 32.5 132 t87.5 98 v-110 h80 v240 H160 v-80 h94 q-62 -50 -98 -122.5 T120 -480 q0 -75 28.5 -140.5 t77 -114 q48.5 -48.5 114 -77 T480 -840 q129 0 226.5 79.5 T831 -560Z
            path(
                fill = SolidColor(Color(0xFFE3E3E3)),
            ) {
                // M 520 630
                moveTo(x = 520.0f, y = 630.0f)
                // l 0 -60
                lineToRelative(dx = 0.0f, dy = -60.0f)
                // l 160 0
                lineToRelative(dx = 160.0f, dy = 0.0f)
                // l 0 60
                lineToRelative(dx = 0.0f, dy = 60.0f)
                // L 520 630z
                lineTo(x = 520.0f, y = 630.0f)
                close()
                // m 60 210
                moveToRelative(dx = 60.0f, dy = 210.0f)
                // l 0 -50
                lineToRelative(dx = 0.0f, dy = -50.0f)
                // l -60 0
                lineToRelative(dx = -60.0f, dy = 0.0f)
                // l 0 -60
                lineToRelative(dx = 0.0f, dy = -60.0f)
                // l 60 0
                lineToRelative(dx = 60.0f, dy = 0.0f)
                // l 0 -50
                lineToRelative(dx = 0.0f, dy = -50.0f)
                // l 60 0
                lineToRelative(dx = 60.0f, dy = 0.0f)
                // l 0 160
                lineToRelative(dx = 0.0f, dy = 160.0f)
                // l -60 0z
                lineToRelative(dx = -60.0f, dy = 0.0f)
                close()
                // m 100 -50
                moveToRelative(dx = 100.0f, dy = -50.0f)
                // l 0 -60
                lineToRelative(dx = 0.0f, dy = -60.0f)
                // l 160 0
                lineToRelative(dx = 160.0f, dy = 0.0f)
                // l 0 60
                lineToRelative(dx = 0.0f, dy = 60.0f)
                // L 680 790z
                lineTo(x = 680.0f, y = 790.0f)
                close()
                // m 40 -110
                moveToRelative(dx = 40.0f, dy = -110.0f)
                // l 0 -160
                lineToRelative(dx = 0.0f, dy = -160.0f)
                // l 60 0
                lineToRelative(dx = 60.0f, dy = 0.0f)
                // l 0 50
                lineToRelative(dx = 0.0f, dy = 50.0f)
                // l 60 0
                lineToRelative(dx = 60.0f, dy = 0.0f)
                // l 0 60
                lineToRelative(dx = 0.0f, dy = 60.0f)
                // l -60 0
                lineToRelative(dx = -60.0f, dy = 0.0f)
                // l 0 50
                lineToRelative(dx = 0.0f, dy = 50.0f)
                // l -60 0z
                lineToRelative(dx = -60.0f, dy = 0.0f)
                close()
                // m 111 -280
                moveToRelative(dx = 111.0f, dy = -280.0f)
                // l -83 0
                lineToRelative(dx = -83.0f, dy = 0.0f)
                // q -26 -88 -99 -144
                quadToRelative(
                    dx1 = -26.0f,
                    dy1 = -88.0f,
                    dx2 = -99.0f,
                    dy2 = -144.0f,
                )
                // t -169 -56
                reflectiveQuadToRelative(
                    dx1 = -169.0f,
                    dy1 = -56.0f,
                )
                // q -117 0 -198.5 81.5
                quadToRelative(
                    dx1 = -117.0f,
                    dy1 = 0.0f,
                    dx2 = -198.5f,
                    dy2 = 81.5f,
                )
                // T 200 480
                reflectiveQuadTo(
                    x1 = 200.0f,
                    y1 = 480.0f,
                )
                // q 0 72 32.5 132
                quadToRelative(
                    dx1 = 0.0f,
                    dy1 = 72.0f,
                    dx2 = 32.5f,
                    dy2 = 132.0f,
                )
                // t 87.5 98
                reflectiveQuadToRelative(
                    dx1 = 87.5f,
                    dy1 = 98.0f,
                )
                // l 0 -110
                lineToRelative(dx = 0.0f, dy = -110.0f)
                // l 80 0
                lineToRelative(dx = 80.0f, dy = 0.0f)
                // l 0 240
                lineToRelative(dx = 0.0f, dy = 240.0f)
                // L 160 840
                lineTo(x = 160.0f, y = 840.0f)
                // l 0 -80
                lineToRelative(dx = 0.0f, dy = -80.0f)
                // l 94 0
                lineToRelative(dx = 94.0f, dy = 0.0f)
                // q -62 -50 -98 -122.5
                quadToRelative(
                    dx1 = -62.0f,
                    dy1 = -50.0f,
                    dx2 = -98.0f,
                    dy2 = -122.5f,
                )
                // T 120 480
                reflectiveQuadTo(
                    x1 = 120.0f,
                    y1 = 480.0f,
                )
                // q 0 -75 28.5 -140.5
                quadToRelative(
                    dx1 = 0.0f,
                    dy1 = -75.0f,
                    dx2 = 28.5f,
                    dy2 = -140.5f,
                )
                // t 77 -114
                reflectiveQuadToRelative(
                    dx1 = 77.0f,
                    dy1 = -114.0f,
                )
                // q 48.5 -48.5 114 -77
                quadToRelative(
                    dx1 = 48.5f,
                    dy1 = -48.5f,
                    dx2 = 114.0f,
                    dy2 = -77.0f,
                )
                // T 480 120
                reflectiveQuadTo(
                    x1 = 480.0f,
                    y1 = 120.0f,
                )
                // q 129 0 226.5 79.5
                quadToRelative(
                    dx1 = 129.0f,
                    dy1 = 0.0f,
                    dx2 = 226.5f,
                    dy2 = 79.5f,
                )
                // T 831 400z
                reflectiveQuadTo(
                    x1 = 831.0f,
                    y1 = 400.0f,
                )
                close()
            }
        }.build().also { _resetIcon = it }
    }

@Suppress("ObjectPropertyName")
private var _resetIcon: ImageVector? = null
