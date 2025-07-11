package de.glueckstobi.juganaut.ui.audio

/**
 * Die verschiedenen Audio-Töne, die abgespielt werden können.
 */
enum class AudioSample(val path: String) {

    MainLoop("/sound/main_loop.wav"),

    Crisp1("/sound/crisp1.wav"),
    Crisp2("/sound/crisp2.wav"),
    Crisp3("/sound/crisp3.wav"),
    Crisp4("/sound/crisp4.wav"),

    CollectDiamond("/sound/collect_diamond.wav"),

    Lose("/sound/lose_game1.wav"),
    Win("/sound/win_game.wav"),
}