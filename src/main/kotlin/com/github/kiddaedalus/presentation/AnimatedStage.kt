package com.github.kiddaedalus.presentation

import kotlinx.coroutines.experimental.launch
import org.two.js.Two
import org.two.js.TwoRenderable
import kotlin.math.roundToLong

sealed class TimelineStage : Animated

/**
 * A typical stage containing potentially several animations which are played concurrently
 */
class AnimatedStage(private val animations: List<Animated>): TimelineStage(), List<Animated> by animations {
    override val shapes: List<Two.Path>
        get() = animations.flatMap { it.shapes }

    override fun render(frame: Long) {
        animations.map {
            it.render(frame)
        }
    }

    override suspend fun animate(frameDurationMillis: Long) {
        for (animation in animations) {
            launch {
                animation.animate(durationMillis)
            }
        }
    }

    override val durationFrames: Long = animations
            .map {it.durationFrames}
            .max() ?: 0

    override val durationMillis: Long = (durationFrames * Application.framesPerMilli).roundToLong()
}

/**
 * A no-op stage that must be manually advanced past
 */
class PausingStage(override val durationMillis: Long = Long.MAX_VALUE) : TimelineStage() {
    override val shapes: List<Two.Path> = listOf()
    override val durationFrames: Long = ( durationMillis * Application.framesPerMilli).roundToLong()
    override suspend fun animate(frameDurationMillis: Long) { }
    override fun render(frame: Long) { }
}

/**
 * A stage that repeats, potentially indefinitely
 */
class RepeatingStage(val repetitions: Int = 2, private val animations: List<Animated> ) : TimelineStage(), Animated {
    override val shapes: List<Two.Path>
        get() = animations.flatMap { it.shapes }

    override fun render(frame: Long) {
        val frameToRender = if(frame > durationFrames) {
            durationSingleRepetitionFrames
        } else {
            frame % durationSingleRepetitionFrames
        }

        animations.map {
            it.render(frameToRender)
        }
    }
    override suspend fun animate(frameDurationMillis: Long) {
        TODO("I don't think I will be calling this function anywhere...")
    }

    /**
     * The duration of a single repetition
     */
    val durationSingleRepetitionFrames: Long = animations
            .map {it.durationFrames}
            .max() ?: 0L

    /**
     * The total duration of the repeating stage, including all repetitions
     */
    override val durationFrames: Long  = durationSingleRepetitionFrames.times(repetitions)

    override val durationMillis: Long = (durationFrames * Application.framesPerMilli).roundToLong()
}