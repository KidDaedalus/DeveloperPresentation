package com.github.kiddaedalus.presentation

import org.two.js.Two
import org.two.js.TwoConstructionParams
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.math.PI
import kotlin.math.min
import kotlin.math.sqrt

fun main(vararg args: String) {

    val messageBox = document.getElementById("message-box") as HTMLElement
    val drawShapes = document.getElementById("draw-shapes") as HTMLElement

    val two = Two(object: TwoConstructionParams {
        override var fullscreen: Boolean? = true
    })
    two.appendTo(drawShapes)

    val tableau = Tableau(150.0,150.0, 40.0 )

    two.add(tableau)
    two.add(tableau.allShapes)

    tableau.middlePlus.noFill()
    tableau.middlePlus.stroke = Tableau.tabOrange
    tableau.middlePlus.closed = false

    var t: Double = 0.1
    two.bind(Two.Events.update) {
        tableau.tertiaryShapes.map { it.rotation += PI / 120 }
        tableau.cornerShapes.map { it.rotation -= PI / 240 }
        tableau.middlePlus.rotation += PI / 240
        tableau.middlePlus.ending = t

        if(t < 1.0) {
            t += 0.01
            t %= 1.0
        } else {
            t = 0.5
        }
    }

    two.play()
}
