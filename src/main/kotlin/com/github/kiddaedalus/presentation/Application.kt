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
        const val framesPerSecond = 60

        const val millisPerFrame:Double = (1.0/ framesPerSecond) * 1000
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

    two.bind(Two.Events.update) {
        tableau.tertiaryShapes.map { it.rotation += PI / 120 }
        tableau.cornerShapes.map { it.rotation -= PI / 240 }
        tableau.middlePlus.rotation += PI / 240
    }
    tableau.middlePlus.appear(durationMillis = 1000L) { console.log("middlePlus Finished appearing")}

    two.play()
}
