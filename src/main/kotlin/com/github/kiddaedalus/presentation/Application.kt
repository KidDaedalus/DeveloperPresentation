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

    val circle = two.makeCircle(100, 100, 75).apply {
        fill = "#FF8000"
        stroke = "orangered"
        linewidth = 5
    }


    two.update()
}
