package com.github.kiddaedalus.presentation

import org.two.js.Two
import kotlin.math.roundToLong

interface Animated {
    fun animate(currentFrame: Double): Boolean
    val durationFrames: Long
    val durationMillis: Long
}

class Animation(
        override val durationMillis: Long = 0,
        val shapes: Array<out Two.Path>,
        val effect: Two.Path.(Double)->Unit) : Animated {

    private var _initialFrame = 0L
    private var _finalFrame = Long.MAX_VALUE

    override val durationFrames: Long = ( durationMillis * Application.framesPerMilli).roundToLong()

    /**
     * Animate the given shapes according to the provided effect
     * Returns "false" while the animation is not complete
     * Returns "true" once the animation is complete
     */
    override fun animate(currentFrame: Double): Boolean {
        if(_initialFrame == 0L) {
            _initialFrame = currentFrame.roundToLong()
            // Take a 0-duration to mean "run animation forever"
            if(durationMillis > 0) {
                _finalFrame =  _initialFrame + durationFrames
            }
        }
        val progressPercent = currentFrame / _finalFrame
        shapes.map { it.effect(progressPercent) }
        return currentFrame > _finalFrame
    }
}

class Appear(override val durationMillis: Long = 1000L,
             val shapes: Array<out Two.Path>) : Animated by
Animation(durationMillis, shapes,
        { progressPercent ->
            svgOpacity = progressPercent
            scale = progressPercent
        })
