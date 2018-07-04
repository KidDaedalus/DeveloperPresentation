package com.github.kiddaedalus.presentation

import org.two.js.Two
import kotlin.math.sqrt

/**
 * A '+' symbol
 */
class Plus(x: Double,
           y: Double,
           var armLength: Double = 12.0,
           var thickness: Double = armLength/3):
        Two.Path(
                // Twelve anchor points fully specify a '+'
                // The last one closes the shape
            Array(13, {_ -> anchor()}),
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
        vertices[12].set(-(armLength+halfThick), halfThick)
    }
}

class Tableau(var x: Double,
              var y: Double,
              val plusSize: Double = 30.0,
              val offset: Double = plusSize*2.5,
              val semiOffset: Double = plusSize*1.6,
              val middlePlus: Plus = Plus(0.0, 0.0, plusSize).apply { fill = tabOrange },

              var cornerScaling: Double = 0.87,
              val upperLeftPlus:  Plus = Plus(- semiOffset,  - semiOffset, plusSize * cornerScaling).apply { fill = tabOrangeLight},
              val upperRightPlus: Plus = Plus(+ semiOffset,  - semiOffset, plusSize * cornerScaling).apply { fill = tabBlueLight},
              val lowerLeftPlus:  Plus = Plus(- semiOffset,  + semiOffset, plusSize * cornerScaling).apply { fill = tabRed},
              val lowerRightPlus: Plus = Plus(+ semiOffset,  + semiOffset, plusSize * cornerScaling).apply { fill = tabBlue},

              var tertiaryScaling: Double = 0.6,
              val bottomPlus:        Plus = Plus(0.0,  - offset, plusSize * tertiaryScaling ).apply { fill = tabBlueLight},
              val leftPlus:       Plus = Plus(- offset, 0.0,plusSize * tertiaryScaling ).apply { fill = tabBlueLight},
              val rightPlus:      Plus = Plus(+ offset, 0.0,plusSize * tertiaryScaling ).apply { fill = tabBluePale},
              val topPlus:     Plus = Plus(0.0, + offset,plusSize * tertiaryScaling ).apply { fill = tabBluePale}
): Two.Group() {
    companion object {
        const val tabOrange = "#E9762D"
        const val tabOrangeLight = "#EC912D"
        const val tabRed = "#C92035"
        const val tabBlue = "#1C447E"
        const val tabBluePale = "#5A6591"
        const val tabBlueLight = "#59879B"
    }

    val allShapes: List<Plus> = listOf(middlePlus, upperLeftPlus, upperRightPlus,lowerLeftPlus,
            lowerRightPlus, topPlus,leftPlus, rightPlus,bottomPlus)
    val cornerShapes: List<Plus> = listOf(upperLeftPlus, upperRightPlus,lowerLeftPlus, lowerRightPlus)
    val tertiaryShapes: List<Plus> = listOf(topPlus, bottomPlus, leftPlus, rightPlus)
    init {
        this.add(*allShapes.toTypedArray())
        translation.set(x, y)
    }
}

/**
 * An equilateral triangle
 * x and y provide coordinates of the midpoint
 *
 *        / \
 *      /     \
 *    /  (x,y)  \
 *  /_____________\
 *         L
 */
class Triangle(x: Double, y: Double, var sideLength: Double) : Two.Path(
        Array(4, {_ -> anchor()}), true, false) {


    init {
        update()
        translation.set(x, y)
    }
    fun update() {
        val halfL = 0.5 * sideLength

        /**
         * The distance from one of the corner points to the midpoint (x,y) is given by R
         * and the shortest distance to an edge is given by r
         *   (x,y)
         *     | \
         *     |   \ R
         *   r |      \
         *     |_________\
         *    0.5 * sideLength
         */
        val R: Double = sideLength / sqrt(3.0)
        val r: Double = R / 2

        vertices[0].command = Two.Commands.move
        vertices[0].set(-halfL, -r)
        vertices[1].set(0.0, R)
        vertices[2].set(halfL, r)
        vertices[3].set(-halfL, -r)
    }
}