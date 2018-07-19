package com.github.kiddaedalus.presentation

import org.two.js.Two

sealed class PresentationText(val text: String) : Two.Text(text, 0.0, 0.0, null)

class Heading(text: String) : PresentationText(text) {
    init {
        size = 80.0
        linewidth = 1.0
        fill = Color.copperOrange.asRgba
    }
}
class SubHeading(text: String) : PresentationText(text) {
    init {
        size = 30.0
        fill = Color.skyBlue.asRgba
    }
}
class SlideTitle(text: String) : PresentationText(text) {
    init {
        size = 60.0
        fill = Color.copperOrange.asRgba
    }
}
class SlideText(text: String) : PresentationText(text) {
    init {
        size = 40.0
    }
}
