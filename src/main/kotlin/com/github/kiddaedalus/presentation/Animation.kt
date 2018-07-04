package com.github.kiddaedalus.presentation

import org.two.js.Two
import kotlin.math.min
import kotlin.math.roundToLong

interface Animated {
    fun animateForFrame(frame: Long)
    val durationFrames: Long
    val durationMillis: Long
}

/**
 * One second in milliseconds
 */
const val defaultEffectDuration = 1000L

class Animation(
        override val durationMillis: Long = defaultEffectDuration,
        val shapes: List<Two.Path>,
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


/**
 * An Animation effect that causes the specified shape to
 */
fun Two.Path.appearEffect(progressPercent: Double) {
    svgOpacity = progressPercent
    scale = progressPercent
}
fun Two.Path.appear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, listOf(this), Two.Path::appearEffect)
fun Collection<Two.Path>.appear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, this.toList(), Two.Path::appearEffect)


fun Two.Path.disappearEffect(progressPercent: Double) {
    svgOpacity = 1 - progressPercent
    scale = 1 - progressPercent
}
fun Two.Path.disappear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, listOf(this), Two.Path::disappearEffect)
fun Collection<Two.Path>.disappear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, this.toList(), Two.Path::disappearEffect)

