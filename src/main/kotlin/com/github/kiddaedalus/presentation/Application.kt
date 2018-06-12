package com.github.kiddaedalus.presentation

import org.two.js.Two
import org.two.js.TwoConstructionParams
import org.w3c.dom.HTMLElement
import kotlin.browser.document

fun main(vararg args: String) {

    console.log("Hello console from kotlin")
    val messageBox = document.getElementById("message-box") as HTMLElement
    val drawShapes = document.getElementById("draw-shapes") as HTMLElement

    val two = Two(object: TwoConstructionParams {
        override var fullscreen: Boolean? = true
    })
    two.appendTo(drawShapes)

    val tableau = Tableau(500.0,500.0)
    two.scene.add(tableau.allShapes)

    //val plus = Two.Path(arrayOf(Two.Anchor()))

    two.update()
}
