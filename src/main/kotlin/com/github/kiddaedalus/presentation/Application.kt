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
    val leftMargin = document.create.div {id = "leftMargin"}.apply {
        style.background = Color.skyBlue.asHex
        style.height = "100%"
        style.width = "20%"
        style.cssFloat = "left"
    }
    val contentArea = document.create.div { id = "contentArea"}.apply {
        style.height = "100%"
        style.width = "60%"
        style.cssFloat = "left"
    }
    val rightMargin = document.create.div { id = "rightMargin" }.apply {
        style.background = Color.skyBlue.asHex
        style.height = "100%"
        style.width = "20%"
        style.cssFloat = "left"
    }

    val body = document.body!!.apply {
        style.background = Color.white.asHex
        append(leftMargin,contentArea,rightMargin)
    }

    // Create the vector art & shapes that comprise this presentation
    val controls = ControlBar(two.width/2, two.height - 25.0)
    val tableau = Tableau(two.width/2,150.0, 40.0 )
    val stageCounter = Two.Text("~", two.width/2, two.height -10.0, null )

    // Setup a timeline of animations performed on the shapes
    val timeline = timeline {
        listener {
            stageCounter.value = "${it.currentStageIndex + 1}/${it.size}"
        }
        slide("Hello", "World")
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

    // Add the shapes to the two.js scenegraph and bind events to animate
    two.apply {
        appendTo(contentArea)
        add(controls)
        add(stageCounter)
        add(tableau)

        // Add all the shapes used in the presentation to the scenegraph
        allShapes.map {
            // Make them invisible to start with until something makes the shape appear
            it.scale = 0.0
            //add(it)
        }

        bind(Two.Events.update) {
            timeline.update()
        }

        bind(Two.Events.resize) {
            // Keep things horizontally centered
            tableau.translation.x = two.width/2
            controls.translation.set(two.width/2, two.height - 25.0)
            stageCounter.translation.set(two.width/2, two.height - 10.0)
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
