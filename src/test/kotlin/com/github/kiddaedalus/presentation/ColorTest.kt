package com.github.kiddaedalus.presentation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail


class ColorTest {
    @Test fun basicSerializationTest() {

        val black = Color(0, 0,0, 1.0)
        assertEquals("#000000", black.toHex() )
        assertEquals("rgba(0,0,0,1.0)", black.toRgba() )

        val white = Color(255, 255, 255, 1.0)
        assertEquals("#FFFFFF", white.toHex())
        assertEquals("rgba(255,255,255,1.0)", white.toRgba())
    }

}