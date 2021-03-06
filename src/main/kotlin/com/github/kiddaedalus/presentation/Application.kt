package com.github.kiddaedalus.presentation

import com.github.kiddaedalus.presentation.Application.Companion.two
import kotlinx.coroutines.experimental.launch
import org.two.js.Two
import org.two.js.TwoConstructionParams
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

    // Create the vector art & shapes that together comprise this presentation
    val controls = ControlBar(0.0, 0.0)
    val tableau = Tableau(two.width/2,150.0, 40.0 )
    val stageCounter = Two.Text("~", 0.0, 0.0, null )
    val leftMargin = two.makeRectangle(0.0, 0.0, 20.0, two.height).apply { fill = Color.skyBlue.asRgba; noStroke() }
    val rightMargin = two.makeRectangle(two.width, 0.0, 20.0, two.height).apply { fill = Color.skyBlue.asRgba; noStroke() }

    val titleBanner = Heading("Hello Software")
    val titleBannerSubtext = SubHeading(
            "a brief primer on getting hired")


    // Setup a timeline of animations performed on the shapes
    val presentation = timeline {
        listener {
            stageCounter.value = "${it.currentStageIndex + 1}/${it.size}"
        }
        stage(titleBanner.appear())
        stage(titleBannerSubtext.appear())
        pause()
        stage(titleBanner.disappear(), titleBannerSubtext.disappear())
        slide("Presentation Overview",
                "Context",
                "Get Hired",
                "Got Hired",
                "Q & A")
        slide("Context: Me",
                "4 year Computer Engineering Degree",
                "8 years industry experience",
                "Senior Software Engineer",
                "Interviewed 50+ candidates",
                "Mentored 4 junior engineers")
        slide("Disclaimer",
                "This is my personal experience",
                "Not representing anyone but myself")
        slide("Context: You",
                "Good News!",
                "You have what it takes!")
        slide("Context: Employers",
                "Core Technology Businesses",
                "Incidentally Technical Businesses",
                "All Other")
        slide("Get Hired: The Process",
                "0. Current employee referral - not all referrals are equal",
                "1. Recruiters and hiring managers sift resumes",
                "2. Recruiter conducts interpersonal phone screen",
                "3. Engineer conducts technical phone screen",
                "4. The Loop",
                "5. Hiring Committee meets",
                "6. Offer")
        slide("Get Hired: Phone Screen",
                "Loops are expensive!",
                "Exists to avoid wasting interviewer time",
                "1 hour, less complex code problem",
                "Out of my 15 phone screens: ",
                " 2/3 passed",
                " 1/3 failed")
        slide("Get Hired: The Loop",
                "4 to 6 interviews, 1 hour each",
                "Pairs of interviewers: Engineers, Managers, PMs",
                "whiteboarding, design, testing, communication",
                "Of my ~40 interviews roughly 1/2 received an offer")
        slide("Get Hired: Reasons for Rejection",
        "40% - Problem Solving",
                "40% - Coding",
                "20% - Modeling",
                "15% - Design/Architecture",
                "10% - Communication",
                "5% - Testing")
        slide("Get Hired: Hiring Committee Meets",
                "Lack of red flags insufficient",
                "Need affirmative cause to hire",
                "One strong no-hire -> rejection")
        slide("Get Hired: So what do you have to offer?",
                "Future potential",
                "Enthusiasm for learning",
                "Coachable",
                "Likeable",
                "~6 months before a college hire is net-beneficial")
        slide("Get Hired: What to ask",
                "Decision making process?",
                "Test/quality strategy?",
                "Engineering toolchain?",
                "How often is the build broken?",
                "Code review process?",
                "Release cadence?")
        slide("Got Hired: Congrats!",
                "Do: Learn theory",
                "Do: Seek mentors",
                "Do: Invite code review",
                "Don't: Be afraid to look ignorant",
                "Don't: Be satisfied with not understanding why it works",
                "Don't: Follow orders without understanding")
        stage(
                tableau.middlePlus.appear(1500L),
                tableau.cornerShapes.appear(1000L),
                tableau.tertiaryShapes.appear(500L)
        )
        repeating(tableau.allShapes.spin(0.5, 2000L))
    }
    stageCounter.value = "1/${presentation.size}"
    val allShapes = presentation.flatMap { it.shapes }
    val allText = allShapes.filter { it is PresentationText }.map { it as PresentationText }

    /**
     * Adjust elements into their proper places according to the current size of the animated area
     */
    fun reposition() {
        val marginWidth = (two.width * 0.2).clamp(10.0, 200.0)
        val contentLeftEdge = marginWidth + 30.0
        controls.translation.set(two.width/2, two.height - 25.0)
        tableau.translation.x = two.width/2
        stageCounter.translation.set(two.width/2, two.height - 10.0)
        leftMargin.apply {
            width = marginWidth
            height = two.height
            translation.y = two.height/2.0
            translation.x = width/2.0
        }
        rightMargin.apply {
            width = marginWidth
            height = two.height
            translation.x = two.width - width/2.0
            translation.y = two.height/2.0
        }
        allText.map {
            when(it) {
                is Heading -> it.translation.set(two.width/2, two.height/2)
                is SubHeading -> it.translation.set(two.width/2, two.height/2 + titleBannerSubtext.height() + 10.0)
                is SlideTitle -> it.translation.set(contentLeftEdge + it.width()/2.0, it.height())
                is SlideText -> it.translation.x = contentLeftEdge + it.width()/2.0
            }
        }
    }

    two.apply {
        // Add the shapes to the two.js scenegraph
        appendTo(body)
        add(controls)
        add(stageCounter)
        add(tableau)
        add(titleBanner)
        add(titleBannerSubtext)

        bind(Two.Events.update) {
            // Keep positions and sizes consistent if the window is resized
            // This involves redundant computation every frame... but this is a glorified powerpoint so good enough
            reposition()
            presentation.update()
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
        presentation.previousStage()
        // Keep going back until we get to a break
        // This is to avoid having to mash the back button to skip over many animations
        // Or being trapped by an animation that's faster than a mere human button press
        while(presentation.currentStage !is PausingStage &&
                presentation.currentStage !is RepeatingStage && presentation.currentStageIndex > 0) {
            presentation.previousStage()
        }
        launch {
            controls.backButton.scale(1.2, 1.0, 150L).animate()
        }
    }
    fun forwardButtonPressed() {
        presentation.advanceStage()
        launch {
            controls.forwardButton.scale(1.2, 1.0, 150L).animate()
        }
    }
    fun playPauseButtonPressed() {
        presentation.pause = !presentation.pause
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
