package de.glueckstobi.juganaut.ui.swing

import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.uiDesigner.core.Spacer
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.plaf.FontUIResource
import javax.swing.text.StyleContext

@Suppress("UNUSED_EXPRESSION")
class SettingsMenuGui(returnEvent: ActionListener) : JPanel() {
    private var contentPane: JPanel? = null
    private var sfxVolumeSlider: JSlider? = null
    private var musicVolumeSlider: JSlider? = null
    private var sfxVolumeLabel: JLabel? = null
    private var musicVolumeLabel: JLabel? = null
    private var settingsLabel: JLabel? = null
    private var returnButton: JButton? = null
    private var musicVolume: JLabel? = null
    private var SFXVolume: JLabel? = null


    init {
        setupUI()
        returnButton!!.addActionListener { returnEvent}
        do {
            SFXVolume!!.text = sfxVolumeSlider!!.value.toString()
            musicVolume!!.text = musicVolumeSlider!!.value.toString()
        } while (isEnabled)
    }

    private fun setupUI() {
        contentPane = JPanel()
        contentPane!!.layout = GridLayoutManager(6, 3, Insets(0, 0, 0, 0), -1, -1)
        contentPane!!.background = Color(-13618892)
        contentPane!!.preferredSize = Dimension(1024, 700)
        musicVolumeSlider = JSlider()
        musicVolumeSlider!!.background = Color(-14079186)
        val musicVolumeSliderFont = this.getFont("Comic Sans MS", Font.PLAIN, 28, musicVolumeSlider!!.font)
        if (musicVolumeSliderFont != null) musicVolumeSlider!!.font = musicVolumeSliderFont
        musicVolumeSlider!!.foreground = Color(-2170653)
        musicVolumeSlider!!.value = 100
        contentPane!!.add(
            musicVolumeSlider,
            GridConstraints(
                3,
                2,
                1,
                1,
                GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                null,
                null,
                0,
                false
            )
        )
        sfxVolumeLabel = JLabel()
        sfxVolumeLabel!!.background = Color(-14079186)
        val sfxVolumeLabelFont = this.getFont("Comic Sans MS", Font.PLAIN, 28, sfxVolumeLabel!!.font)
        if (sfxVolumeLabelFont != null) sfxVolumeLabel!!.font = sfxVolumeLabelFont
        sfxVolumeLabel!!.foreground = Color(-2170653)
        sfxVolumeLabel!!.text = "Sound effect volume:"
        contentPane!!.add(
            sfxVolumeLabel,
            GridConstraints(
                2,
                0,
                1,
                1,
                GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                Dimension(223, 17),
                null,
                0,
                false
            )
        )
        musicVolumeLabel = JLabel()
        musicVolumeLabel!!.background = Color(-14079186)
        val musicVolumeLabelFont = this.getFont("Comic Sans MS", Font.PLAIN, 28, musicVolumeLabel!!.font)
        if (musicVolumeLabelFont != null) musicVolumeLabel!!.font = musicVolumeLabelFont
        musicVolumeLabel!!.foreground = Color(-2170653)
        musicVolumeLabel!!.text = "Music Volume:"
        contentPane!!.add(
            musicVolumeLabel,
            GridConstraints(
                3,
                0,
                1,
                1,
                GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                Dimension(223, 17),
                null,
                0,
                false
            )
        )
        sfxVolumeSlider = JSlider()
        sfxVolumeSlider!!.background = Color(-14079186)
        val sfxVolumeSliderFont = this.getFont("Comic Sans MS", Font.PLAIN, 28, sfxVolumeSlider!!.font)
        if (sfxVolumeSliderFont != null) sfxVolumeSlider!!.font = sfxVolumeSliderFont
        sfxVolumeSlider!!.foreground = Color(-2170653)
        sfxVolumeSlider!!.inverted = false
        sfxVolumeSlider!!.paintLabels = false
        sfxVolumeSlider!!.paintTicks = false
        sfxVolumeSlider!!.paintTrack = true
        sfxVolumeSlider!!.snapToTicks = false
        sfxVolumeSlider!!.value = 100
        contentPane!!.add(
            sfxVolumeSlider,
            GridConstraints(
                2,
                2,
                1,
                1,
                GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                null,
                null,
                0,
                false
            )
        )
        settingsLabel = JLabel()
        settingsLabel!!.background = Color(-14079186)
        val settingsLabelFont = this.getFont("Comic Sans MS", -1, 36, settingsLabel!!.font)
        if (settingsLabelFont != null) settingsLabel!!.font = settingsLabelFont
        settingsLabel!!.foreground = Color(-2170653)
        settingsLabel!!.horizontalAlignment = 10
        settingsLabel!!.horizontalTextPosition = 11
        settingsLabel!!.text = "Settings"
        contentPane!!.add(
            settingsLabel,
            GridConstraints(
                0,
                0,
                1,
                1,
                GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                null,
                null,
                0,
                false
            )
        )
        returnButton = JButton()
        returnButton!!.background = Color(-65468)
        returnButton!!.isEnabled = true
        val returnButtonFont = this.getFont("Comic Sans MS", -1, 28, returnButton!!.font)
        if (returnButtonFont != null) returnButton!!.font = returnButtonFont
        returnButton!!.text = "Return to Menu"
        contentPane!!.add(
            returnButton,
            GridConstraints(
                5,
                0,
                1,
                1,
                GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK or GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                null,
                null,
                0,
                false
            )
        )
        val spacer1 = Spacer()
        contentPane!!.add(
            spacer1,
            GridConstraints(
                4,
                0,
                1,
                1,
                GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_VERTICAL,
                1,
                GridConstraints.SIZEPOLICY_WANT_GROW,
                null,
                null,
                null,
                0,
                false
            )
        )
        SFXVolume = JLabel()
        val SFXVolumeFont = this.getFont("Comic Sans MS", -1, 28, SFXVolume!!.font)
        if (SFXVolumeFont != null) SFXVolume!!.font = SFXVolumeFont
        SFXVolume!!.foreground = Color(-2170653)
        SFXVolume!!.isOpaque = false
        SFXVolume!!.text = "100"
        contentPane!!.add(
            SFXVolume,
            GridConstraints(
                2,
                1,
                1,
                1,
                GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                null,
                null,
                0,
                false
            )
        )
        musicVolume = JLabel()
        val musicVolumeFont = this.getFont("Comic Sans MS", -1, 28, musicVolume!!.font)
        if (musicVolumeFont != null) musicVolume!!.font = musicVolumeFont
        musicVolume!!.foreground = Color(-2170653)
        musicVolume!!.isOpaque = false
        musicVolume!!.text = "100"
        contentPane!!.add(
            musicVolume,
            GridConstraints(
                3,
                1,
                1,
                1,
                GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null,
                null,
                null,
                0,
                false
            )
        )
        val separator1 = JSeparator()
        separator1.foreground = Color(-2170653)
        contentPane!!.add(
            separator1,
            GridConstraints(
                1,
                0,
                1,
                1,
                GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_WANT_GROW,
                null,
                Dimension(223, 3),
                null,
                0,
                false
            )
        )
    }

    /**
     * @noinspection ALL
     */
    @Suppress("SameParameterValue")
    private fun getFont(fontName: String?, style: Int, size: Int, currentFont: Font?): Font? {
        if (currentFont == null) return null
        val resultName: String
        if (fontName == null) {
            resultName = currentFont.name
        } else {
            val testFont = Font(fontName, Font.PLAIN, 10)
            resultName = if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                fontName
            } else {
                currentFont.name
            }
        }
        val font =
            Font(resultName, if (style >= 0) style else currentFont.style, if (size >= 0) size else currentFont.size)
        val isMac = System.getProperty("os.name", "").lowercase().startsWith("mac")
        val fontWithFallback = if (isMac) Font(font.family, font.style, font.size) else StyleContext().getFont(
            font.family,
            font.style,
            font.size
        )
        return if (fontWithFallback is FontUIResource) fontWithFallback else FontUIResource(fontWithFallback)
    }
}
