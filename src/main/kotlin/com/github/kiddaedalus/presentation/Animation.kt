package com.github.kiddaedalus.presentation

import org.two.js.Two
import kotlin.math.min
import kotlin.math.roundToLong

interface Animated {
    fun animateForFrame(frame: Long)
    val durationFrames: Long
    val durationMillis: Long
}

class Animation(
        override val durationMillis: Long = 0,
        val shapes: List<out Two.Path>,
        val effect: Two.Path.(Double)->Unit) : Animated {

    override val durationFrames: Long = ( durationMillis * Application.framesPerMilli).roundToLong()

    /**
     * Animate the given shapes according to the provided effect
     * If the provided frame is less than 0, the animated objects will be set to their initial state
     * If the provided frame is greater than the total durationFrames, the animated objects will be set to their final state
     */
    override fun animateForFrame(frame: Long){
        val clampedFrame = frame.clamp(0L, durationFrames)
        val progressPercent = clampedFrame.toDouble() / durationFrames
        shapes.map { it.effect(progressPercent) }
    }
}

fun Two.Path.appear(progressPercent: Double) {
    svgOpacity = progressPercent
    scale = progressPercent
}

fun appear(durationMillis: Long = 1000L, vararg shapes: Two.Path): Animated {
    return Animation(durationMillis, listOf(*shapes), Two.Path::appear)
}

class Appear(override val durationMillis: Long = 1000L,
             val shapes: List<out Two.Path>) : Animated by
Animation(durationMillis, shapes,
        { progressPercent ->
            svgOpacity = progressPercent
            scale = progressPercent
        })

class Disappear(override val durationMillis: Long = 1000L,
             val shapes: List<out Two.Path>) : Animated by
Animation(durationMillis, shapes,
        { progressPercent ->
            svgOpacity = 1 - progressPercent
            scale = 1 - progressPercent
        })
