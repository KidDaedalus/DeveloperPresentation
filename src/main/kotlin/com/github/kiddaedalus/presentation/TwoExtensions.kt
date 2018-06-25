package com.github.kiddaedalus.presentation

import org.two.js.Two
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
 * Animate an effect over a duration in time
 * Once per frame the supplied 'effect' function is called, supplied with the progress so far given as a percentage
 * Calls the completedCallback once the animation is complete
 */
fun Two.Path.animate(two: Two = Application.two, durationMillis: Long = 1000, completedCallback: Two.Path.() -> Unit = {}, effect: Two.Path.(Double) -> Unit ) {
    val finalFrame =  (durationMillis / Application.millisPerFrame).roundToLong()

    fun effectImpl(frameCount: Double) {
        if(frameCount < finalFrame) {
            val progressPercent = frameCount / finalFrame
            effect(progressPercent)
        } else {
            two.unbind(Two.Events.update, ::effectImpl)
            completedCallback()
        }
    }

    two.bind(Two.Events.update, ::effectImpl)
}

/**
 * Makes a Two.Path fade into existence over the given duration of time.
 */
fun Two.Path.appear(two: Two = Application.two, durationMillis: Long = 1000, completedCallback: Two.Path.() -> Unit = {}) =
        animate(two, durationMillis, completedCallback) { progress ->
            svgOpacity = progress
            scale = progress
        }

/**
 * Make a Two.Path fade out of existence over the given duration
 */
fun Two.Path.disappear(two: Two = Application.two, durationMillis: Long = 1000, completedCallback: Two.Path.() -> Unit = {}) =
        animate(two, durationMillis, completedCallback) { progress ->
            svgOpacity = 1 - progress
            scale = 1 - progress
        }


fun Two.Path.rotate(two: Two = Application.two, durationMillis: Long = 1000, completedCallback: Two.Path.() -> Unit) =
        animate(two, durationMillis, completedCallback) {

        }
/**
 * For some reason setting opacity through the usual property doesn't work
 */
var Two.Path.svgOpacity: Double
    get() {
        val element = document.getElementById(this.id)
        return element?.getAttribute("fill-opacity")?.toDouble() ?: 0.0

    }
    set(value) {
        // Try to keep the two.js model in sync with the view
        this.opactiy = value
        val element = document.getElementById(this.id)
        element?.setAttribute("fill-opacity", value.toString())
    }