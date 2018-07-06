package com.github.kiddaedalus.presentation

import org.two.js.Two
import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.math.roundToLong

/**
 * Create an anchor point at the origin
 */
fun anchor(): Two.Anchor = anchor(0.0, 0.0)
/**
 * Convenience function for making an anchor. Sets the control points for each vertex directly on the vertex itself
 */
fun anchor(x: Double, y: Double, command: Two.Commands = Two.Commands.line): Two.Anchor =
        Two.Anchor(x,y,x,y,x,y,command)

/**
 * For some reason setting opacity through the 'opacity' property doesn't work...
 */
var Two.Path.svgOpacity: Double
    get() {
        val element = document.getElementById(this.id)
        return element?.getAttribute("fill-opacity")?.toDouble() ?: 0.0

    }
    set(value) {
        // Try to keep the two.js model in sync with the view, despite setting opacity having no apparent effect
        this.opactiy = value
        val element = document.getElementById(this.id)
        element?.setAttribute("fill-opacity", value.toString())
    }

/**
 * Restrict a comparable to a range.
 * If the comparable exceeds the upper bound given by max, return max
 * If the comparable exceeds the lower bound given by min, return min
 */
fun <T : Comparable<T>> T.clamp(min: T, max: T): T =
        when {
            this < min -> min
            this > max -> max
            else -> this
        }

/**
 * Gets the DOM element associated with this Two.Path
 * Only expected to work when rendering to SVG as paths on canvas or WebGL don't have DOM elements so far as I know
 */
fun Two.Path.domElement(): Element? = document.getElementById(this.id)
