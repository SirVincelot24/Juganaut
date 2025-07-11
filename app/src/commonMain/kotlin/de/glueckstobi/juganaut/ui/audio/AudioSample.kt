package de.glueckstobi.juganaut.ui.audio

/**
 * Die verschiedenen Audio-Töne, die abgespielt werden können.
 */
enum class AudioSample(val path: String) {

    MainLoop("files/main_loop.wav"),

    Crisp1("files/crisp1.wav"),
    Crisp2("files/crisp2.wav"),
    Crisp3("files/crisp3.wav"),
    Crisp4("files/crisp4.wav"),

    CollectDiamond("files/collect_diamond.wav"),

    Lose("files/lose_game1.wav"),
    Win("files/win_game.wav"),
}