package de.sirvincelot.juganaut.ui.audio

/**
 * Die verschiedenen Soundeffekte, die abgespielt werden können.
 */
enum class SFXAudioSample(val path: String) {
    Crisp1("files/crisp1_sfx.wav"),
    Crisp2("files/crisp2_sfx.wav"),
    Crisp3("files/crisp3_sfx.wav"),
    Crisp4("files/crisp4_sfx.wav"),

    CollectDiamond("files/collect_diamond_sfx.wav"),

    BombExplode("files/bomb_explode_sfx.wav"),

    Lose("files/lose_game_sfx.wav"),
    Win("files/win_game_sfx.wav"),
}