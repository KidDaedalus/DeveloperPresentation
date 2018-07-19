package com.github.kiddaedalus.presentation

import com.github.kiddaedalus.presentation.Application.Companion.two
import kotlinx.coroutines.experimental.launch
import kotlinx.html.dom.append
import kotlinx.html.dom.create
import kotlinx.html.id
import kotlinx.html.js.div
import kotlinx.html.style
import org.two.js.Two
import org.two.js.TwoConstructionParams
import org.two.js.TwoRenderable
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.document
import kotlin.browser.window

class Application {
    companion object {
        val two = Two(TwoConstructionParams(fullscreen = true, type = Two.Types.svg))
        // two.js calls update() 60 times per second when told to play, so this is effectively fixed
        const val framesPerSecond: Long  = 60L
        const val framesPerMilli: Double = framesPerSecond / 1000.0
        const val millisPerFrame: Double = 1000.0 / framesPerSecond
    }
}

fun main(vararg args: String) {
    // Configure the DOM
    val body = document.body!!
    body.style.background = Color.white.asHex

    // Create the vector art & shapes that comprise this presentation
    val controls = ControlBar(0.0, 0.0)
    val tableau = Tableau(two.width/2,150.0, 40.0 )
    val stageCounter = Two.Text("~", 0.0, 0.0, null )
    val leftMargin = two.makeRectangle(0.0, 0.0, 20.0, two.height).apply { fill = Color.skyBlue.asRgba; noStroke() }
    val rightMargin = two.makeRectangle(two.width, 0.0, 20.0, two.height).apply { fill = Color.skyBlue.asRgba; noStroke() }

    //val titleBanner = Two.Text("Hello World")


    // Setup a timeline of animations performed on the shapes
    val timeline = timeline {
        listener {
            stageCounter.value = "${it.currentStageIndex + 1}/${it.size}"
        }
        slide("Hello (to the) World (of software development)")
        stage(
                tableau.middlePlus.appear(1500L),
                tableau.cornerShapes.appear(1000L),
                tableau.tertiaryShapes.appear(500L)
        )
        stage(tableau.cornerShapes.spin())
        stage(
                tableau.middlePlus.disappear()
        )
        stage(
                tableau.middlePlus.appear(),
                tableau.middlePlus.spin(),
                tableau.cornerShapes.disappear()
        )
        stage(
                tableau.cornerShapes.appear()
        )
        repeating(tableau.allShapes.spin(0.5, 2000L))
    }
    stageCounter.value = "1/${timeline.size}"
    val allShapes = timeline.flatMap { it.shapes }
    val allText = allShapes.filter { it is Two.Text }

    fun reposition() {
        controls.translation.set(two.width/2, two.height - 25.0)
        tableau.translation.x = two.width/2
        stageCounter.translation.set(two.width/2, two.height - 10.0)
        leftMargin.apply {
            width = (two.width * 0.2).clamp(10.0, 200.0)
            height = two.height
            translation.y = two.height/2.0
            translation.x = width/2.0
        }
        rightMargin.apply {
            width = (two.width * 0.2).clamp(10.0, 200.0)
            height = two.height
            translation.x = two.width - width/2.0
            translation.y = two.height/2.0
        }
        allText.forEach {
            // It would be very convenient to use two.js's getBoundingClientRect() function here
            // but here's a comment excerpted straight from the source of two.js:
            //     "TODO: Implement a way to calculate proper bounding boxes of `Two.Text`."
            val width = it.domElement()?.getBoundingClientRect()?.width ?: 0.0
            it.translation.x = (two.width * 0.2).clamp(20.0, 200.0) + width/2.0
            console.log("Setting x position of textual element according to $width")
        }
    }

    // Add the shapes to the two.js scenegraph and bind events to animate
    two.apply {
        appendTo(body)
        add(controls)
        add(stageCounter)
        add(tableau)

        bind(Two.Events.update) {
            // Keep positions and sizes consistent if the window is resized
            // This involves redundant computation every frame... but this is a glorified powerpoint so good enough
            reposition()
            timeline.update()
        }

        allShapes.map {
            // Make presentation shapes invisible to start with until something makes the shape appear
            it.scale = 0.0
        }

        play()
    }



    // Setup mouse & keyboard controls
    // Use the keyboard to control progression through the timeline
    fun backButtonPressed() {
        timeline.previousStage()
        launch {
            controls.backButton.scale(1.2, 1.0, 150L).animate()
        }
    }
    fun forwardButtonPressed() {
        timeline.advanceStage()
        launch {
            controls.forwardButton.scale(1.2, 1.0, 150L).animate()
        }
    }
    fun playPauseButtonPressed() {
        timeline.pause = !timeline.pause
        launch {
            controls.playPauseButton.scale(1.2, 1.0, 150L).animate()
        }
    }

    // Wait for the page to load to add click events to the controls
    // Is there a race condition here? I do not know for sure that two.js will add all elements to the DOM before
    // the onload event fires but for now it "works on my machine"
    //TODO: Figure out how to re-wire these when Webpack does a hot-replace, or decide that it does not matter
    window.onload = {
        controls.backButton.domElement()?.onClick {
            backButtonPressed()
        }
        controls.forwardButton.domElement()?.onClick {
            forwardButtonPressed()
        }
        controls.playPauseButton.domElement()?.onClick {
            playPauseButtonPressed()
        }
    }
    window.onkeyup = { event ->
        event as KeyboardEvent
        when(event.key) {
            "ArrowLeft" ->  backButtonPressed()
            " " -> playPauseButtonPressed()
            "ArrowRight" ->forwardButtonPressed()
            else -> { }
        }
    }

}
