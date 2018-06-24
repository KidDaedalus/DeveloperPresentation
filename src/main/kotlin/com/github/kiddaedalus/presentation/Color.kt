package com.github.kiddaedalus.presentation

data class Color(val red: Short, val green: Short, val blue: Short, val alpha: Double) {
    init {
        fun boundsCheck(value: Short) =
                when(value) {
                    in 0..255 -> {} // valid
                    else -> throw IllegalArgumentException("Color value $value is not within expected range 0 to 255")
                }

        boundsCheck(red)
        boundsCheck(green)
        boundsCheck(blue)
        when(alpha) {
            in 0.0..0.1 -> {}
            else -> throw IllegalArgumentException("Alpha value $alpha is not within expected range 0.0 to 1.0")
        }
    }

    companion object {
        fun fromString(colorString: String): Color {
            return when {
                colorString.startsWith("#") && colorString.length == "#RRGGBB".length -> {
                    Color(colorString.substring(1,3).toShort(),colorString.substring(3,5).toShort(),colorString.substring(5,7).toShort(), 1.0)
                }
                colorString.startsWith("rgba") -> {
                    val matches = colorString.match("""rgba\(([0-9]+),([0-9]+),([0-9]+),([0-9.]+)\)""")
                        ?: throw IllegalArgumentException("Couldn't parse color '$colorString' as an rgba")
                    Color(matches[1].toShort(), matches[2].toShort(), matches[3].toShort(), matches[4].toDouble())
                }
                else -> throw IllegalArgumentException(
                        "Color '$colorString' is not one of '#RRGGBB'  or 'rgba()' formats ")
            }
        }
    }

    /**
     * Returns this color as a CSS rgba string
     * https://www.w3schools.com/cssref/func_rgba.asp
     */
    fun toRgba(): String = "rbga($red, $blue, $green, $alpha)"

    /**
     * Return this color as a CSS hex string #RRGGBB
     * This format does not include the alpha channel
     */
    fun toHex(): String = ""
}