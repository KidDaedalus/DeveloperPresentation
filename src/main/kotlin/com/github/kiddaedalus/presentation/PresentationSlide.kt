package com.github.kiddaedalus.presentation

import org.two.js.Two

data class PresentationSlide(val text: List<String>, val listType: BulletListType = BulletListType.Plain) {
    enum class BulletListType {
        Unordered,
        Ordered,
        Plain
    }

    val shapes: List<Two.Text> = listOf()
}