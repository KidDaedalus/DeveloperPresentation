package com.github.kiddaedalus.presentation

import kotlinx.coroutines.experimental.launch
import kotlin.math.roundToLong

sealed class TimelineStage : Animated

/**
 * A typical stage containing potentially several animations which are played concurrently
 */
class AnimatedStage(private val animations: List<Animated>): TimelineStage(), List<Animated> by animations {
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
 * A no-op stage that is intended to be manually advanced past
 */
class PausingStage(override val durationMillis: Long = Long.MAX_VALUE) : TimelineStage() {
    override val durationFrames: Long = ( durationMillis * Application.framesPerMilli).roundToLong()
    override suspend fun animate(frameDurationMillis: Long) { }
    override fun render(frame: Long) { }
}
