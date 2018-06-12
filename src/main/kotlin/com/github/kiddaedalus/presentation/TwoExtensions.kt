package com.github.kiddaedalus.presentation

import org.two.js.Two

/**
 * Convenience function for making an anchor. Sets the control points directly on the point itself
 */
fun anchor(): Two.Anchor = anchor(0.0, 0.0)
fun anchor(x: Double, y: Double, command: Two.Commands = Two.Commands.line): Two.Anchor =
        Two.Anchor(x,y,x,y,x,y,command)