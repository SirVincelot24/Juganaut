package de.glueckstobi.juganaut.ui.swing

import com.adonax.audiocue.AudioCue
import de.glueckstobi.juganaut.bl.Game
import de.glueckstobi.juganaut.bl.setup.WorldBuilder
import de.glueckstobi.juganaut.bl.setup.WorldBuilderConfiguration
import de.glueckstobi.juganaut.ui.swing.game.UserInputHandler
import de.glueckstobi.juganaut.ui.swing.game.WorldRenderer
import de.glueckstobi.juganaut.ui.swing.game.itemrenderer.StaticImageRenderer.loadImage
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.WindowConstants


/**
 * Enthält die gesamte Benutzer-Oberfläche.
 */
class MainGui {

    companion object {
        /**
         * Die Zeit einer Spiel-Runde (in Millisekunden)
         */
        val tickDurationMs = 350.toLong()

        private var collectDiamondSFX = Companion::class.java.getResource("/sound/collect_diamond.wav")

        private var mainMusic = Companion::class.java.getResource("/sound/main_loop.wav")

        var sfxAudioCue = AudioCue.makeStereoCue(collectDiamondSFX, 4)

        var musicAudioCue = AudioCue.makeStereoCue(mainMusic, 4)

    }

    /**
     * Die Uhr, die regelmäßig eine Spiel-Runde ausführt und das Ergebnis anzeigt.
     */
    inner class RenderCycle(val game: Game) : Runnable {

        /**
         * True, wenn die Spiel-Uhr nicht weiterlaufen soll (weil das Spiel beendet ist)
         */
        @Volatile
        var clockStopped = false

        /**
         * Wird regelmäßig für jede neue Spiel-Runde aufgerufen.
         */
        override fun run() {
            var previousTickMs = nanoToMilli(System.nanoTime())
            while (!clockStopped && !Thread.currentThread().isInterrupted) {
                val now = nanoToMilli(System.nanoTime())
                val nextTick = previousTickMs + tickDurationMs
                val sleepTime = nextTick - now
                Thread.sleep(sleepTime)
                previousTickMs = nextTick
                SwingUtilities.invokeLater {
                    tickRenderCycle(game)
                }
            }

        }

    }


    /**
     * Das Fenster
     */
    private val window: JFrame = JFrame("Juganaut")

    private val statusLabel: JLabel = JLabel(" ")
    private val diamondCountLabel:JLabel = JLabel("")

    /**
     * Hört auf Tasten-Drücke und gibt es an den [inputController] weiter.
     */
    private val keyListener = object : KeyAdapter() {
        override fun keyPressed(e: KeyEvent) {
            inputController?.onKeyPress(e)
        }

        override fun keyReleased(e: KeyEvent) {
            inputController?.onKeyRelease(e)
        }
    }

    private var inputController: UserInputHandler? = null

    internal var renderCycle: RenderCycle? = null




    /**
     * Startet das Spiel.
     */
    private fun startPlaying(configuration: WorldBuilderConfiguration): JPanel {
        val game = WorldBuilder().createGame(configuration)
        val renderer = WorldRenderer(game.world)
        inputController = UserInputHandler(game)
        startRenderCycle(game)
        sfxAudioCue.open()
        musicAudioCue.open()
        musicAudioCue.play()
        musicAudioCue.setVolume(musicAudioCue.obtainInstance(), 0.1)
        musicAudioCue.setLooping(musicAudioCue.obtainInstance(), -1)
        return showWindow(renderer)
    }





    /**
     * Startet den die Uhr, die regelmäßig eine Spiel-Runde ausführt und den aktuellen Spiel-Zustand anzeigt.
     */
    private fun startRenderCycle(game: Game) {
        renderCycle = RenderCycle(game)
        Thread(renderCycle, "TickThread").start()
    }


    /**
     * Wird für jede Spiel-Runde aufgerufen.
     * Führt die Spiel-Logik aus und zeichnet danach das Ergebnis neu.
     */
    private fun tickRenderCycle(game: Game) {
        game.turnController.tick()
        window.repaint()
        updateStatus(game)
    }

    private fun updateStatus(game: Game) {
        if (game.gameOverReason != null) {
            statusLabel.foreground = Color.RED
            statusLabel.text = "GAME OVER!! R:Restart Q:Quit"
        } else if (game.winningReason != null) {
            statusLabel.foreground = Color.GREEN
            statusLabel.text = "Gewonnen!!!"
        } else {
            statusLabel.foreground = Color.WHITE
            statusLabel.text = "Viel Spaß!"

        }
        diamondCountLabel.text = game.diamondCount.toString() + " / " + game.diamondsInGame.toString()

    }


    fun showMenu(configuration: WorldBuilderConfiguration) {
        val quitActionEvent = ActionListener { _ -> window.dispose() }
        val startActionEvent = ActionListener { _ -> startPlaying(configuration) }
        val returnActonEvent = ActionListener { _ -> window.contentPane = MenuGui(quitActionEvent, startActionEvent) { } }
        val settingsActionEvent = ActionListener { _ -> window.contentPane = SettingsMenuGui(returnActonEvent) }
        val menuGui = MenuGui(quitActionEvent, startActionEvent, settingsActionEvent)
        menuGui.quitButton.addActionListener { window.dispose() }
        menuGui.startButton.addActionListener { startPlaying(configuration) }
        window.contentPane = menuGui.contentPane
        window.iconImage = ImageIO.read(this.javaClass.getResource("/textures/monster.png"))
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        window.setSize(1024, 1000)

        window.isVisible = true

        window.contentPane.requestFocus()

        window.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                window.dispose()
            }
        })

    }
//    fun showSettingsMenu(playerX: Int, playerY: Int, diamondsInGame: Int) {
//        val quitActionEvent = ActionListener { _ -> window.dispose() }
//        val startActionEvent = ActionListener { _ -> startPlaying(playerX, playerY, diamondsInGame) }
//        val menuGui = MenuGui(quitActionEvent, startActionEvent)
//        menuGui.quitButton.addActionListener { window.dispose() }
//        menuGui.startButton.addActionListener { startPlaying(playerX, playerY, diamondsInGame) }
//        window.contentPane = menuGui.contentPane
//        window.iconImage = ImageIO.read(this.javaClass.getResource("/textures/monster.png"))
//        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
//        window.setSize(1024, 1000)
//
//        window.isVisible = true
//
//        window.contentPane.requestFocus()
//
//        window.addWindowListener(object : WindowAdapter() {
//            override fun windowClosed(e: WindowEvent?) {
//                super.windowClosed(e)
//                window.dispose()
//            }
//        })
//
//    }

    /**
     * Baut das Fenster auf und zeigt es an.
     */
    private fun showWindow(renderer: WorldRenderer): JPanel {
        val contentPane = JPanel(BorderLayout())
        val statusPane = JPanel(GridLayout())
        val backgroundColor = Color.BLACK
        contentPane.add(statusPane, BorderLayout.SOUTH)
        statusPane.add(statusLabel, 0)
        statusPane.add(diamondCountLabel, 1)
        contentPane.add(renderer, BorderLayout.CENTER)
        statusLabel.font = Font("Sans-Serif", Font.PLAIN, 30)
        statusLabel.isOpaque = true
        statusLabel.background = backgroundColor

        diamondCountLabel.foreground = Color.CYAN
        diamondCountLabel.isOpaque = true
        diamondCountLabel.background = backgroundColor
        diamondCountLabel.horizontalAlignment = SwingConstants.RIGHT
        diamondCountLabel.icon = ImageIcon(loadImage("/textures/diamond.png"))
        diamondCountLabel.font = Font("Sans-Serif", Font.PLAIN, 30)
        diamondCountLabel.setHorizontalTextPosition(SwingConstants.LEFT)

        window.contentPane = contentPane

        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
        //window.setSize(1024, 1000)

        window.isVisible = true

        window.contentPane.requestFocus()
        window.contentPane.addKeyListener(keyListener)

        window.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent?) {
                super.windowClosed(e)
                renderCycle?.clockStopped = true
                try {
                    sfxAudioCue.close()
                    musicAudioCue.close()
                } catch (e: IllegalStateException) {
                    System.err.println("Already closed AudioCue!")
                }
                window.dispose()
            }
        })
        return contentPane
    }


    /**
     * Umrechnung von Nanosekunden in Millisekunden.
     */
    private fun nanoToMilli(nano: Long): Long = nano / 1_000_000

}