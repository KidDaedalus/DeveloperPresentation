package com.github.kiddaedalus.presentation

data class Color(val red: Short, val green: Short, val blue: Short, val alpha: Double = 1.0) {
    init {
        clampColorValue(red)
        clampColorValue(green)
        clampColorValue(blue)
        clampAlpha(alpha)
    }

    companion object {
        const val maxColorValue = 255
        const val hexadecimalRadix = 16
        fun fromString(colorString: String): Color =
            when {
                colorString.startsWith("#") && colorString.length == "#RRGGBB".length -> {
                    Color(red   = colorString.substring(1,3).toShort(hexadecimalRadix),
                          blue  = colorString.substring(3,5).toShort(hexadecimalRadix),
                          green = colorString.substring(5,7).toShort(hexadecimalRadix),
                          alpha = 1.0)
                }
                colorString.startsWith("rgba") -> {
                    val matches = colorString.match("""rgba\(([0-9]+), *([0-9]+), *([0-9]+), *([0-9.]+)\)""")
                        ?: throw IllegalArgumentException("Couldn't parse color '$colorString' as an rgba")
                    Color(matches[1].toShort(), matches[2].toShort(), matches[3].toShort(), matches[4].toDouble())
                }
                else -> throw IllegalArgumentException(
                        "Color '$colorString' is not one of '#RRGGBB'  or 'rgba()' formats ")
            }

        fun clampColorValue(value: Short) =
                when {
                    value < 0 -> 0
                    value > maxColorValue -> 255
                    else -> value
                }

        fun clampAlpha(value: Double) =
                when {
                    value < 0.0 -> 0.0
                    value > 1.0 -> 1.0
                    else -> value
                }
    }

    fun setAlpha(value: Double) = Color(red, green, blue, value)

    operator fun plus(other: Color) =
            Color(red   = (red + other.red).toShort(),
                  green = (green + other.green).toShort(),
                  blue  = (blue + other.blue).toShort())


    operator fun minus(other: Color) =
            Color(red   = clampColorValue((red - other.red).toShort()),
                  green = (green - other.green).toShort(),
                  blue  = (blue - other.blue).toShort())

    /**
     * Returns this color as a CSS rgba string
     * https://www.w3schools.com/cssref/func_rgba.asp
     */
    fun toRgba(): String = "rgba($red,$blue,$green,$alpha)"

    /**
     * Return this color as a CSS hex string #RRGGBB
     * This format does not include the alpha channel
     */
    fun toHex(): String = "#" +
            red.toString(hexadecimalRadix).padStart(2, '0').toUpperCase() +
            green.toString(hexadecimalRadix).padStart(2, '0').toUpperCase() +
            blue.toString(hexadecimalRadix).padStart(2, '0').toUpperCase()
}