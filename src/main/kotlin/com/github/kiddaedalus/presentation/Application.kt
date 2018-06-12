package com.github.kiddaedalus.presentation

import org.two.js.Two
import org.two.js.TwoConstructionParams
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.math.PI

fun main(vararg args: String) {

    val messageBox = document.getElementById("message-box") as HTMLElement
    val drawShapes = document.getElementById("draw-shapes") as HTMLElement

    val two = Two(object: TwoConstructionParams {
        override var fullscreen: Boolean? = true
    })
    two.appendTo(drawShapes)

    val tableau = Tableau(150.0, 150.0, 30.0)

    two.add(tableau)
    two.add(tableau.allShapes)


    two.bind(Two.Events.update) {
        tableau.tertiaryShapes.map { it.rotation += PI / 120 }
        tableau.cornerShapes.map { it.rotation -= PI / 240 }
    }

    two.play()
}
