package com.github.kiddaedalus.presentation

import kotlinx.coroutines.experimental.delay
import org.two.js.Two
import org.two.js.TwoRenderable
import kotlin.math.PI
import kotlin.math.roundToLong

/**
 * Describe an object which is animated over a finite duration of time
 * Given a constant framerate durationFrames and durationMillis should be consistent to within the duration of a single
 * frame
 */
interface Animated {
    val durationFrames: Long
    val durationMillis: Long
    /**
     * Set the animated objects to the state they should have on the specified frame
     * If the provided frame is less than or equal to 0, the animated objects will be set to their initial state
     * If the provided frame is greater than or equal to durationFrames, the animated objects will be set to its final
     * state
     */
    fun render(frame: Long)


    /**
     * The shapes being animated
     */
    val shapes: List<Two.Path>

    /**
     * Animate the effect from start to finish
     * Time is kept internally and so is not guaranteed to be kept aligned with anything else
     * Useful for fire-and-forget animations that don't need to be kept in sync with anything
     */
    suspend fun animate(frameDurationMillis: Long = Application.millisPerFrame.roundToLong())
}

/**
 * One second in milliseconds
 */
const val defaultEffectDuration = 500L

/**
 * Shorthand for a function that renders some visual effect on the basis of a percentage
 */
typealias RenderEffect = Two.Path.(Double) -> Unit

class Animation(
        override val durationMillis: Long = defaultEffectDuration,
        override val shapes: List<Two.Path>,
        val effect: RenderEffect) : Animated {

    override val durationFrames: Long = ( durationMillis * Application.framesPerMilli).roundToLong()

    override fun render(frame: Long){
        val clampedFrame = frame.clamp(0L, durationFrames)
        val progressPercent = clampedFrame.toDouble() / durationFrames
        shapes.map { it.effect(progressPercent) }
    }

    override suspend fun animate(frameDurationMillis: Long) {
        for(currentFrame in 0..durationFrames) {
            render(currentFrame)
            delay(frameDurationMillis)
        }
    }

    /**
     * Produce a new animation that plays the given animation backwards
     */
    fun reverse() = Animation(durationMillis, shapes) { progressPercentage ->
        effect(this, (1 - progressPercentage).clamp(0.0, 1.0))
    }

    companion object {

        /**
         * Create a new animation which is composed of the given animations running in sequence
         * Only the shapes from the first animation are used
         */
        // This could be varargs but that requires more thinking on how to generalize to any number of arguments
        //TODO: Actually make this work... right now it does not
        fun sequence(animationA: Animation, animationB: Animation): Animation {
            val totalDuration = animationA.durationMillis + animationB.durationMillis
            val switchoverFrame = animationA.durationMillis
            return Animation(
                    durationMillis = totalDuration,
                    shapes = animationA.shapes,
                    effect = { progressPercentage ->
                        // It feels a bit silly/wasteful to be bouncing back and forth between a concrete frame
                        // and percentage progress... but that happens when failing to consider composition
                        // to be a requirement up front
                        val currentFrame = (totalDuration * progressPercentage).roundToLong()
                        when {
                            currentFrame < switchoverFrame -> animationA.render(currentFrame)
                            else -> animationB.render(currentFrame)
                        }
                    })
        }
    }
}


/**
 * Causes the specified shape to appear out of nothing
 */
fun Two.Path.appearEffect(progressPercent: Double) {
    svgOpacity = progressPercent
    scale = progressPercent
}
fun Two.Path.appear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, listOf(this), Two.Path::appearEffect)
fun Collection<Two.Path>.appear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, this.toList(), Two.Path::appearEffect)
/**
 * Cause the specified shape to fade out of existence
 */
fun Two.Path.disappearEffect(progressPercent: Double) {
    svgOpacity = 1 - progressPercent
    scale = 1 - progressPercent
}
fun Two.Path.disappear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, listOf(this), Two.Path::disappearEffect)
fun Collection<Two.Path>.disappear(durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, this.toList(), Two.Path::disappearEffect)

/**
 * Cause the shape to complete the given number of clockwise rotations
 */
fun Two.Path.spinEffect(rotations: Double = 1.0, progressPercent: Double) {
    rotation = 2 * PI * rotations * progressPercent
}
fun Two.Path.spin(rotations: Double = 1.0, durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, listOf(this)) { progress -> this.spinEffect(rotations, progress)}
fun Collection<Two.Path>.spin(rotations: Double = 1.0, durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, this.toList()) { progress -> this.spinEffect(rotations, progress)}

/**
 * Scale the shape from startScale to endScale
 */
fun Two.Path.scaleEffect(startScale: Double = 1.0, endScale: Double = 2.0, progressPercent: Double ) {
    scale = progressPercent*(endScale-startScale) + startScale
}
fun Two.Path.scale(startScale: Double = 1.0, endScale: Double = 2.0, durationMillis: Long = defaultEffectDuration) =
        Animation(durationMillis, listOf(this), { progress -> this.scaleEffect(startScale, endScale, progress)})

/**
 * An animation which first scales the shape to the target size then reverts it back to its original size
 */
fun Two.Path.scalePop(startScale: Double = 1.0, endScale: Double = 2.0, durationMillis: Long = defaultEffectDuration): Animation {
    return Animation.sequence(
            scale(startScale, endScale, durationMillis/2),
            scale(endScale, startScale, durationMillis/2))
}
