package com.github.kiddaedalus.presentation

import com.github.kiddaedalus.presentation.Application.Companion.two
import org.two.js.Two
import org.two.js.TwoConstructionParams
import kotlin.browser.document
import kotlin.math.PI
import kotlin.math.roundToLong

class Application {
    companion object {
        val two = Two(TwoConstructionParams(fullscreen = true))
        // two.js calls update() 60 times per second when told to play, so this is effectively fixed
        const val framesPerSecond: Long  = 60L
        const val framesPerMilli: Double = framesPerSecond / 1000.0
        const val millisPerFrame: Double = 1000.0 / framesPerSecond
    }
}

fun main(vararg args: String) {

    val tableau = Tableau(150.0,150.0, 40.0 )

    val timeline = timeline {
        stage(
                appear(3000L, tableau.middlePlus),
                Appear(durationMillis = 2000L, shapes = tableau.cornerShapes),
                Appear(durationMillis = 1000L, shapes = tableau.tertiaryShapes)
        )
    }

    two.apply {
        appendTo(document.body!!)
        add(tableau)

        bind(Two.Events.update) {
            timeline.update()

            tableau.tertiaryShapes.map { it.rotation += PI / 120 }
            tableau.cornerShapes.map { it.rotation -= PI / 240 }
            tableau.middlePlus.rotation += PI / 240
        }
        play()
    }
}
