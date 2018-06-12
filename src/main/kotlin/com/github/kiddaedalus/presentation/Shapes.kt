package com.github.kiddaedalus.presentation

import org.two.js.Two

class Plus(var x: Double,
           var y: Double): Two.Text("+", x, y, undefined)

class Tableau(var x: Double,
              var y: Double,
              val fontSize: Double = 300.0,
              val offset: Double = fontSize / 2,
              val middlePlus: Plus = Plus(x, y).apply { fill = tabOrange; size = fontSize },

              var cornerScaling: Double = 0.87,
              val upperLeftPlus:  Plus = Plus(x - offset, y + offset).apply { fill = tabOrangeLight; size = fontSize * cornerScaling},
              val upperRightPlus: Plus = Plus(x + offset, y + offset).apply { fill = tabBlueLight; size = fontSize * cornerScaling},
              val lowerLeftPlus:  Plus = Plus(x - offset, y - offset).apply { fill = tabRed; size = fontSize * cornerScaling },
              val lowerRightPlus: Plus = Plus(x + offset, y - offset).apply { fill = tabBlue; size = fontSize * cornerScaling },

              var tertiaryScaling: Double = 0.6,
              val topPlus:        Plus = Plus(x, y + offset).apply { fill = tabBlueLight; size = fontSize * tertiaryScaling },
              val leftPlus:       Plus = Plus(x - offset, y).apply { fill = tabBlueLight; size = fontSize * tertiaryScaling},
              val rightPlus:      Plus = Plus(x + offset, y).apply { fill = tabBluePale; size = fontSize * tertiaryScaling},
              val bottomPlus:     Plus = Plus(x, y - offset).apply { fill = tabBluePale; size = fontSize * tertiaryScaling},

              val allShapes: Array<Plus> = arrayOf(middlePlus, upperLeftPlus, upperRightPlus,lowerLeftPlus,
                      lowerRightPlus, topPlus,leftPlus, rightPlus,bottomPlus)

): Two.Group() {
    companion object {
        const val tabOrange = "#E9762D"
        const val tabOrangeLight = "#EC912D"
        const val tabRed = "#C92035"
        const val tabBlue = "#1C447E"
        const val tabBluePale = "#5A6591"
        const val tabBlueLight = "#59879B"
    }

    init {
        this.add(allShapes)
        println("Tableau!")
    }
}