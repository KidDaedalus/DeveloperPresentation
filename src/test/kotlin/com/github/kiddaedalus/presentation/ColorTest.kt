package com.github.kiddaedalus.presentation

import kotlin.test.Test
import kotlin.test.assertEquals


class ColorTest {
    @Test fun basicSerializationTest() {
        val black = Color(0, 0,0, 1.0)
        assertEquals("#000000", black.asHex )
        assertEquals("rgba(0,0,0,1)", black.asRgba )
        assertEquals(black, Color.fromString("rgba(0,0,0,1)"))

        val white = Color(255, 255, 255, 1.0)
        assertEquals("#FFFFFF", white.asHex)
        assertEquals("rgba(255,255,255,1)", white.asRgba)
        assertEquals(white, Color.fromString("#FFFFFF"))

        val red = Color(255,0,0)
        assertEquals("#FF0000", red.asHex)
        assertEquals("rgba(255,0,0,1)", red.asRgba)
        assertEquals(red, Color.fromString("rgba(255,0,0,1)"))
    }
}
