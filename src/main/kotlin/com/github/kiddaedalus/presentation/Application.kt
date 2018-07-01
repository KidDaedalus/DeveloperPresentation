package com.github.kiddaedalus.presentation

import org.two.js.Two
import org.two.js.TwoConstructionParams
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.sqrt

class Application {
    companion object {
        val two = Two( js("{ fullscreen: true }") )
        // two.js calls update() 60 times per second when told to play, so this is effectively fixed
        const val framesPerSecond: Long  = 60L
        const val framesPerMilli: Double = framesPerSecond / 1000.0
        const val millisPerFrame: Double = 1000.0 / framesPerSecond
    }
}

fun main(vararg args: String) {

    //val messageBox = document.getElementById("message-box") as HTMLElement
    //val drawShapes = document.getElementById("draw-shapes") as HTMLElement

    val two = Application.two

    two.appendTo(document.body!!)

    val tableau = Tableau(150.0,150.0, 40.0 )

    two.add(tableau)
    two.add(tableau.allShapes)

    tableau.opactiy = 0.5
    tableau.middlePlus.opactiy = 0.5

    val timeline = AnimationTimeline(listOf(
            TimelineStage(listOf(
                    listOf(Appear(durationMillis = 3000L, shapes = arrayOf(tableau.middlePlus))),
                    listOf(Appear(durationMillis = 2000L, shapes = tableau.cornerShapes)),
                    listOf(Appear(durationMillis = 1000L, shapes = tableau.tertiaryShapes))
            ))
    ))

    two.bind(Two.Events.update) {
        val currentFrame = it
        val currentStage = timeline.first()
        if(currentFrame < currentStage.durationFrames) {
            currentStage.forEach {
                it.forEach {
                    it.animate(currentFrame)
                }
            }
        }

        tableau.tertiaryShapes.map { it.rotation += PI / 120 }
        tableau.cornerShapes.map { it.rotation -= PI / 240 }
        tableau.middlePlus.rotation += PI / 240
    }

    two.play()
}
