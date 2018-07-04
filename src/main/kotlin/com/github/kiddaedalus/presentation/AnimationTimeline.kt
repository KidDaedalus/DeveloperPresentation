package com.github.kiddaedalus.presentation

import kotlin.math.min
import kotlin.math.roundToLong

class TimelineStage(private val animations: List<Animated>): List<Animated> by animations, Animated {
    override fun animateForFrame(frame: Long) {
        animations.map {
            it.animateForFrame(frame)
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
    return AnimationTimeline(builder.build())
}

/**
 * Builder for AnimationTimeline to be used with the `timeline` DSL
 */
class TimelineBuilder {
    private val stages: MutableList<TimelineStage> = mutableListOf()

    fun stage(vararg animated: Animated) = stages.add(TimelineStage(listOf(*animated)))
    fun stage(animated: Collection<Animated>) = stages.add(TimelineStage(animated.toList()))

    fun build(): List<TimelineStage> {
        return stages.toList()
    }
}


class AnimationTimeline(private val stages:  List<TimelineStage>):  List<TimelineStage> by stages {

    val durationFrames: Long = stages.map { it.durationFrames }.sum()
    val durationMillis: Long = stages.map { it.durationMillis }.sum()

    private var frameCounter: Long = 0

    fun update() {
        var framesRemaining: Long = frameCounter
        for(stage in stages) {
            if(framesRemaining <= stage.durationFrames) {
                stage.animateForFrame(framesRemaining)
            }
            framesRemaining -= stage.durationFrames
            if(framesRemaining < 0) {
                break
            }
        }

        frameCounter++
    }
    fun resetFrameCounter() {
        frameCounter = 0
    }
}
