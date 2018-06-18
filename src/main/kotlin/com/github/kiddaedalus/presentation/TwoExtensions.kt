package com.github.kiddaedalus.presentation

import org.two.js.Two
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.roundToInt
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
 * Makes a Two.Path fade into existence over the given duration of time.
 * Calls provided callback function once the Path has been fully drawn
 *
 */
fun Two.Path.appear(two: Two = Application.two, durationMillis: Long = 1000, callback: (Two.Path) -> Unit = {} ) {
    var frameCount = 0L
    val finalFrame =  (durationMillis / Application.millisPerFrame).roundToLong()

    fun appearFun(args: Array<Any>?) {
        if(frameCount < finalFrame) {
            frameCount++
            val progressPercent = frameCount.toDouble() / finalFrame
            this.ending = progressPercent
        } else {
            two.unbind(Two.Events.update, ::appearFun)
            callback(this)
        }
    }

    two.bind(Two.Events.update, ::appearFun)
}
