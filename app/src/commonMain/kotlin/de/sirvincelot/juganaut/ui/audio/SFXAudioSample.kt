package de.sirvincelot.juganaut.ui.audio

/**
 * Die verschiedenen Soundeffekte, die abgespielt werden können.
 */
enum class SFXAudioSample(val path: String) {
    Crisp1("files/crisp1.wav"),
    Crisp2("files/crisp2.wav"),
    Crisp3("files/crisp3.wav"),
    Crisp4("files/crisp4.wav"),

    CollectDiamond("files/collect_diamond.wav"),

    Lose("files/lose_game1.wav"),
    Win("files/win_game.wav"),
}