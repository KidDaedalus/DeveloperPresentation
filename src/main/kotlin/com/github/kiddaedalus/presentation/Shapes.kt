package com.github.kiddaedalus.presentation

import org.two.js.Two

/**
 * A '+' symbol
 */
class Plus(var x: Double,
           var y: Double,
           var armLength: Double = 12.0,
           var thickness: Double = armLength/3):
        Two.Path(
                // Twelve anchor points fully specify a '+'
            Array(12, {_ -> anchor()}),
            true, false) {
    init {
        noStroke()
        update()
        translation.set(x, y)
    }

    /**
     * Update the position of each anchor according to the current x, y, armLength, thickness
     */
    fun update() {
        val halfThick = thickness / 2
        vertices[0].command = Two.Commands.move
        vertices[0].set(-(armLength+halfThick), halfThick)
        vertices[1].set(-halfThick, halfThick)
        vertices[2].set(-halfThick, halfThick+armLength)
        vertices[3].set(halfThick, halfThick+armLength)
        vertices[4].set(halfThick, halfThick)
        vertices[5].set(halfThick+armLength, halfThick)
        vertices[6].set(halfThick+armLength, -halfThick)
        vertices[7].set(halfThick, -halfThick)
        vertices[8].set(halfThick, -halfThick-armLength)
        vertices[9].set(-halfThick, -halfThick-armLength)
        vertices[10].set(-halfThick, -halfThick)
        vertices[11].set(-halfThick-armLength, -halfThick)
    }
}

class Tableau(var x: Double,
              var y: Double,
              val plusSize: Double = 30.0,
              val offset: Double = plusSize*2.5,
              val semiOffset: Double = plusSize*1.6,
              val middlePlus: Plus = Plus(x, y, plusSize).apply { fill = tabOrange },

              var cornerScaling: Double = 0.87,
              val upperLeftPlus:  Plus = Plus(x - semiOffset, y - semiOffset, plusSize * cornerScaling).apply { fill = tabOrangeLight},
              val upperRightPlus: Plus = Plus(x + semiOffset, y - semiOffset, plusSize * cornerScaling).apply { fill = tabBlueLight},
              val lowerLeftPlus:  Plus = Plus(x - semiOffset, y + semiOffset, plusSize * cornerScaling).apply { fill = tabRed},
              val lowerRightPlus: Plus = Plus(x + semiOffset, y + semiOffset, plusSize * cornerScaling).apply { fill = tabBlue},

              var tertiaryScaling: Double = 0.6,
              val bottomPlus:        Plus = Plus(x, y - offset, plusSize * tertiaryScaling ).apply { fill = tabBlueLight},
              val leftPlus:       Plus = Plus(x - offset, y,plusSize * tertiaryScaling ).apply { fill = tabBlueLight},
              val rightPlus:      Plus = Plus(x + offset, y,plusSize * tertiaryScaling ).apply { fill = tabBluePale},
              val topPlus:     Plus = Plus(x, y + offset,plusSize * tertiaryScaling ).apply { fill = tabBluePale}
): Two.Group() {
    companion object {
        const val tabOrange = "#E9762D"
        const val tabOrangeLight = "#EC912D"
        const val tabRed = "#C92035"
        const val tabBlue = "#1C447E"
        const val tabBluePale = "#5A6591"
        const val tabBlueLight = "#59879B"
    }

    val allShapes: Array<Plus> = arrayOf(middlePlus, upperLeftPlus, upperRightPlus,lowerLeftPlus,
            lowerRightPlus, topPlus,leftPlus, rightPlus,bottomPlus)
    val cornerShapes: Array<Plus> = arrayOf(upperLeftPlus, upperRightPlus,lowerLeftPlus, lowerRightPlus)
    val tertiaryShapes: Array<Plus> = arrayOf(topPlus, bottomPlus, leftPlus, rightPlus)
    init {

        this.add(allShapes)
        translation.set(x, y)
    }
}