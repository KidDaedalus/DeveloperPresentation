package com.github.kiddaedalus.presentation

import org.two.js.Two

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
class TimelineBuilder(val two: Two = Application.two) {
    enum class BulletListType {
        Unordered,
        Ordered,
        Plain
    }

    private val stages: MutableList<TimelineStage> = mutableListOf()
    private val listeners: MutableList<(AnimationTimeline)->Unit> = mutableListOf()

    /**
     * Add a listener that will be notified when the current stage changes
     */
    fun listener(listener: (AnimationTimeline)->Unit) = listeners.add(listener)
    /**
     * Add a stage that pauses indefinitely, displaying no particular animation, necessitating manual advancement
     */
    fun pause() = stages.add(PausingStage())

    /**
     * Add a typical animation stage to the timeline
     */
    fun stage(vararg animated: Animated) = stages.add(AnimatedStage(listOf(*animated)))
    fun stage(animated: List<Animated>) = stages.add(AnimatedStage(animated))
    /**
     * Add a stage that repeats the specified number of times
     */
    fun repeating(repetitions: Int, vararg animated: Animated) = stages.add(RepeatingStage(repetitions, listOf(*animated)))
    /**
     * Add a stage that repeats (effectively) forever, necessitating manual advancement to go past it
     */
    fun repeating(vararg animated: Animated) = stages.add(RepeatingStage(Int.MAX_VALUE, listOf(*animated)))
    fun repeating(vararg animated: Animated, repetitions: Int = Int.MAX_VALUE) = stages.add(RepeatingStage(repetitions, listOf(*animated)))

    /**
     * Quick way to create several stages of text
     */
    fun slide(vararg lines: String, listType: BulletListType = BulletListType.Unordered) {
        val vectorizedLines = lines.map {
            Two.Text(it, x = 20.0, y = 0.0, styles = null).apply {
                svgOpacity = 0.0
            }
        }
        vectorizedLines.forEachIndexed { i, line ->
            line.translation.y = (i + 1) * 20.0
            two.add(line)
            stage(line.appear(1000L))
        }
        val plus = Plus(two.width - 20.0, two.height - 20.0, 20.0).apply { fill = Color.skyBlue.asRgba }//svgOpacity = 0.0 }
        two.add(plus)
        repeating(plus.spin())
        stage( vectorizedLines.map{ it.disappear() }.plus(plus.disappear()) )
    }

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
            frameCounter = 0
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
        when(currentStage) {
            is PausingStage -> return
            else -> {
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
    }
}
