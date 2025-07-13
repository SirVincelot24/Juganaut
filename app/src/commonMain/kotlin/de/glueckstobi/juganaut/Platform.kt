package de.glueckstobi.juganaut

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform