package com.github.kiddaedalus.presentation

import org.two.js.Two


/**
 * Create an anchor point at the origin
 */
fun anchor(): Two.Anchor = anchor(0.0, 0.0)
/**
 * Convenience function for making an anchor. Sets the control points for each vertex directly on the vertex itself
 */
fun anchor(x: Double, y: Double, command: Two.Commands = Two.Commands.line): Two.Anchor =
        Two.Anchor(x,y,x,y,x,y,command)