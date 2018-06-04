package com.github.kiddaedalus.presentation

import org.w3c.dom.HTMLElement
import kotlin.browser.document

fun main(vararg args: String) {
    console.log("Hello console from kotlin")
    val drawShapes = document.getElementById("draw-shapes") as HTMLElement
    val params = object : TwoConstructionParams {
        override var width: Number? = 200
        override var height: Number? = 200
    }
    val two = Two(params)
    two.appendTo(drawShapes)

    val circle = two.makeCircle(50, 50, 100).apply {
        fill = "#FF8000"
        stroke = "orangered"
        linewidth = 5
    }
    two.update()
}
