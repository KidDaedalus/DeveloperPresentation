package com.github.kiddaedalus.presentation

import kotlin.math.roundToLong

class TimelineStage(private val animations: List<Animated>): List<Animated> by animations, Animated {
    override fun render(frame: Long) {
        animations.map {
            it.render(frame)
        }
    }

    override suspend fun animate(frameDurationMillis: Long) {
        for (animation in animations) {
            animation.animate(durationMillis)
        }
    }

    override val durationFrames: Long = animations
            .map {it.durationFrames}
            .max() ?: 0

    override val durationMillis: Long = (durationFrames * Application.framesPerMilli).roundToLong()

}

/**
 * DSL for creating an AnimationTimeline
 */
fun timeline(initFun: TimelineBuilder.()->Unit ): AnimationTimeline {
    val builder = TimelineBuilder()
    builder.initFun()
    return builder.build()
}

/**
 * Builder for AnimationTimeline to be used with the `timeline` DSL
 */
class TimelineBuilder {
    private val stages: MutableList<TimelineStage> = mutableListOf()
    private val listeners: MutableList<(AnimationTimeline)->Unit> = mutableListOf()

    fun listener(listener: (AnimationTimeline)->Unit) = listeners.add(listener)
    fun stage(vararg animated: Animated) = stages.add(TimelineStage(listOf(*animated)))
    fun stage(animated: Collection<Animated>) = stages.add(TimelineStage(animated.toList()))

    fun build(): AnimationTimeline {
        return AnimationTimeline(stages.toList(), listeners)
    }
}


class AnimationTimeline(
        private val stages:  List<TimelineStage>,
        /**
         * Each listener function is called when the current stage changes
         */
        val listeners: MutableList<(AnimationTimeline)->Unit> = mutableListOf()):  List<TimelineStage> by stages {

    val durationFrames: Long = stages.map { it.durationFrames }.sum()
    val durationMillis: Long = stages.map { it.durationMillis }.sum()

    private var frameCounter: Long = 0

    var pause: Boolean = false

    var currentStageIndex: Int = 0
        private set(value) {
            field = value.clamp(0, stages.size - 1)
            listeners.map { listenerFun -> listenerFun(this) }
        }

    /**
     * Skip to the next stage. Won't attempt to go past the final stage
     */
    fun advanceStage() {
        currentStage.render(currentStage.durationFrames)
        currentStageIndex++
    }

    /**
     * Skip to the previous stage. Won't attempt to go before the first stage
     */
    fun previousStage() {
        currentStage.render(0)
        currentStageIndex--
    }

    var currentStage: TimelineStage = stages[currentStageIndex]
        get() = stages[currentStageIndex]
        private set

    /**
     * Advances the timeline by a single frame
     */
    fun update() {
        if(pause) {
            return
        }
        /**
         * If the currently playing stage has run its course, advance to the next stage, pausing at the very end
         */
        if(frameCounter > currentStage.durationFrames &&
           currentStage != stages.last()) {
            currentStageIndex++
            frameCounter = 0

        }
        currentStage.render(frameCounter)

        frameCounter++
    }
}
