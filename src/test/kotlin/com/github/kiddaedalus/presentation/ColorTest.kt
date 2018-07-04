package com.github.kiddaedalus.presentation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail


class ColorTest {
    @Test fun basicSerializationTest() {
        val black = Color(0, 0,0, 1.0)
        assertEquals("#000000", black.toHex() )
        assertEquals("rgba(0,0,0,1)", black.toRgba() )
        assertEquals(black, Color.fromString("rgba(0,0,0,1)"))

        val white = Color(255, 255, 255, 1.0)
        assertEquals("#FFFFFF", white.toHex())
        assertEquals("rgba(255,255,255,1)", white.toRgba())
        assertEquals(white, Color.fromString("#FFFFFF"))
    }
}
