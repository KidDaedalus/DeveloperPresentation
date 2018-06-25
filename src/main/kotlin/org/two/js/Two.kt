package org.two.js

import kotlin.js.*
import org.w3c.dom.*
import org.w3c.dom.css.CSSImportRule
import kotlin.math.round

/**
 * https://two.js.org/#documentation
 */
@JsModule("two.js")
@JsNonModule
open external class Two (params: TwoConstructionParams /* null */) {
    /**
     * A string representing which type of renderer the instance has implored
     */
    open var type: Types
    /**
     * A number representing how many frames have elapsed.
     */
    open var frameCount: Double

    /**
     * A number representing how much time has elapsed since the last frame in milliseconds.
     */
    open var timeDelta: Double

    /**
     * The width of the instance's dom element.
     */
    open var width: Double

    /**
     * The height of the instance's dom element.
     */
    open var height: Double

    /**
     * A boolean representing whether or not the instance is being updated through the automatic requestAnimationFrame.
     */
    open var playing: Boolean

    /**
     * The instantiated rendering class for the instance. For a list of possible rendering types check out Two.Types.
     */
    open var renderer: Types

    /**
     * The base level Two.Group which houses all objects for the instance.
     * Because it is a Two.Group transformations can be applied to it that will affect all objects in the instance.
     * This is handy as a makeshift camera.
     */
    open var scene: Group

    /**
     * A convenient method to append the instance's dom element to the page.
     * It's required to add the instance's dom element to the page in order to see anything drawn.
     */
    fun appendTo(element: HTMLElement): Unit

    /**
     * This method adds the instance to the requestAnimationFrame loop. In affect enabling animation for this instance.
     */
    fun play(): Unit

    /**
     * This method removes the instance from the requestAnimationFrame loop. In affect halting animation for this instance.
     */
    fun pause(): Unit

    /**
     * This method updates the dimensions of the drawing space, increments the tick for animation, and finally calls
     * two.render(). When using the built-in requestAnimationFrame hook, two.play(), this method is invoked for you
     * automatically.
     */
    fun update(): Unit

    /**
     * This method makes the instance's renderer draw. It should be unnecessary to invoke this yourself at anytime.
     */
    fun render(): Unit

    /**
     * Add one or many shapes / groups to the instance.
     * Objects can be added as arguments, two.add(o1, o2, oN), or as an array depicted above.
     */
    fun add(vararg objects: Any): Unit

    /**
     * Remove one or many shapes / groups from the instance. Objects can be removed as arguments,
     * two.remove(o1, o2, oN), or as an array depicted above.
     */
    fun remove(vararg objects: Any): Unit

    /**
     * Removes all objects from the instance's scene. If you intend to have the browser garbage collect this,
     * don't forget to delete the references in your application as well.
     */
    fun clear(): Unit

    /**
     * Draws a line between two coordinates to the instance's drawing space where x1, y1 are the x, y values for the
     * first coordinate and x2, y2 are the x, y values for the second coordinate. It returns a Two.Line object.
     */
    fun makeLine(x1: Double, y1: Double, x2: Double, y2: Double): Line

    /**
     * Draws a rectangle to the instance's drawing space where x, y are the x, y values for the center point of the
     * rectangle and width, height represents the width and height of the rectangle. It returns a Two.Rectangle object.
     */
    fun makeRectangle(x: Double, y: Double, width: Double, height: Double): Rectangle

    /**
     * Draws a rounded rectangle to the instance's drawing space where x, y are the x, y values for the center point of
     * the rectangle and width, height represents the width and height of the rectangle. Lastly, the radius parameter
     * defines what the miter radius of the rounded corner is. It returns a Two.RoundedRectangle object.
     */
    fun makeRoundedRectangle(x: Double, y: Double, width: Double, height: Double, radius: Double): RoundedRectangle

    /**
     *Draws a circle to the instance's drawing space where x, y are the x, y values for the center point of the circle
     * and radius is the radius of the circle. It returns a Two.Circle object.
     */
    fun makeCircle(x: Double, y: Double, radius: Double): Circle

    /**
     * Draws an ellipse to the instance's drawing space where x, y are the x, y values for the center point of the
     * ellipse and width, height are the dimensions of the ellipse. It returns a Two.Ellipse object.
     */
    fun makeEllipse(x: Double, y: Double, width: Double, height: Double): Ellipse

    /**
     * Draws a star to the instance's drawing space where ox, oy are the x, y values for the center point of the star
     * and or, ir are the outer and inner radii for the star, and sides are how many points the star has.
     * It returns a Two.Star object.
     */
    fun makeStar(ox:Double, oy:Double, or: Double, ir: Double, sides: Long): Star

    /**
     * Draws a polygon to the instance's drawing space where ox, oy are the x, y values for the center of the polygon,
     * r is the radius, and sides are how many sides the polygon has. It returns a Two.Polygon object.
     */
    fun makePolygon(x1: Double, y1: Double, x2: Double, y2: Double, open: Boolean): Polygon

    /**
     * Draws an arc segment from center point ox, oy with an inner and outer radius of ir, or. Lastly, you need to
     * supply a start and ending angle sa, ea. Optionally, pass the resolution for how many points on the arc are
     * desired. It returns a Two.ArcSegment object.
     */
    //TODO: declare type bindings for ArcSegment
    fun makeArcSegment(ox: Double, oy: Double, innerRadius: Double, outerRadius: Double, startingAngle : Double, endingAngle: Double, resolution: Double): Path

    /**
     * Draws a curved path to the instance's drawing space. The arguments are a little tricky. It returns a Two.Path
     * object.
     *
     *  The method accepts any amount of paired x, y values as denoted by the series above.
     *  It then checks to see if there is a final argument, a boolean open, which marks whether or not the shape should
     *  be open. If true the curve will have two clear endpoints, otherwise it will be closed.
     *  This method also recognizes the format two.makeCurve(points, open) where points is an array of Two.Anchor's and
     *  open is an optional boolean describing whether or not to expose endpoints. It is imperative if you generate
     *  curves this way to make the list of points Two.Anchor's.
     */
    //TODO: declare type bindings for Path
    fun makeCurve(vararg coordinates: Double): Path
    fun makeCurve(vararg coordinates: Double, open: Boolean): Path

    /**
     *  Draws a path to the instance's drawing space. The arguments are a little tricky. It returns a Two.Path object.
     *  The method accepts any amount of paired x, y values as denoted by the series above. It then checks to see if
     *  there is a final argument, a boolean open, which marks whether or not the shape should be open.
     *  If true the path will have two clear endpoints, otherwise it will be closed.
     *  This method also recognizes the format two.makePath(points, open) where points is an array of Two.Anchor's and
     *  open is an optional boolean describing whether or not to expose endpoints. It is imperative if you generate
     *  curves this way to make the list of points Two.Anchor's.
     */
    //TODO: declare type bindings for Path
    fun makePath(vararg coordinates: Double): Path

    /**
     * Adds a group to the instance's drawing space. While a group does not have any visible features when rendered it
     * allows for nested transformations on shapes. See Two.Group for more information. It accepts an array of objects,
     * Two.Paths or Two.Groups. As well as a list of objects as the arguments, two.makeGroup(o1, o2, oN).
     * It returns a Two.Group object.
     */
    fun makeGroup(objects: Array<Any>): Group

    /**
     * Reads an svg node and draws the svg object by creating Two.Paths and Two.Groups from the reference. It then adds
     * it to the instance's drawing space. It returns a Two.Group object.
     */
    //TODO: declare type bindings for SvgNode
    fun interpret(svgNode: Any): Group

    /**
     * Bind an event, string, to a callback function. Passing "all" will bind the callback to all events.
     * Inherited from Backbone.js.
     */
    fun bind(event: Events, callback: (framecount:Double /*= null*/) -> Unit): Two

    /**
     * Remove one or many callback functions. If callback is null it removes all callbacks for an event.
     * If the event name is null, all callback functions for the instance are removed. This is highly discouraged.
     * Inherited from Backbone.js.
     */
    fun unbind(event: Events, callback: (framecount:Double /*= null*/) -> Unit): Two

    /**
     * It would make sense to me if this were an enum, but it isn't
     */
    enum class Types {
        webgl,
        svg,
        canvas,
    }
    enum class Events {
        play,
        pause,
        update,
        render,
        resize,
        change,
        remove,
        insert
    }

    /**
     *  This is the base class for creating all drawable shapes in two.js. Unless specified methods return their instance
     *  of Two.Path for the purpose of chaining.
     *
     *      construction var path = new Two.Path(vertices, closed, curved, manual);
     *
     *  A path takes an array of vertices which are made up of Two.Anchors. This is essential for the two-way
     *  databinding. It then takes two booleans, closed and curved which delineate whether the shape should be closed
     *  (lacking endpoints) and whether the shape should calculate curves or straight lines between the vertices.
     *  Finally, manual is an optional argument if you'd like to override the default behavior of two.js and handle
     *  anchor positions yourself. Generally speaking, this isn't something you need to deal with, although some great
     *  usecases arise from this customability, e.g: advanced curve manipulation.
     */
    open class Path(verticies: Array<Anchor>, closed: Boolean, curved: Boolean) {
        /**
         * The id of the path. In the svg renderer this is the same number as the id attribute given to the
         * corresponding node. i.e: if path.id = 4 then document.querySelector('#two-' + group.id) will return the
         * corresponding svg node.
         */
        open var id: String
        /**
         * A string representing the color for the stroke of the path. All valid css representations of color are
         * accepted.
         */
        open var stroke: String
        /**
         * A string representing the color for the area of the vertices. All valid css representations of color are
         * accepted.
         */
        open var fill: String
        /**
         * A number representing the thickness the path's strokes. Must be a positive number.
         */
        open var linewidth: Double
        /**
         * A number representing the opacity of the path. Use strictly for setting. Must be a number 0-1.
         */
        open var opactiy: Double

        /**
         * A string representing the type of stroke cap to render.
         * All applicable values can be found on the w3c spec. https://www.w3.org/TR/SVG/images/painting/linecap.svg
         * Defaults to "round".
         */
        open var cap: String

        /**
         * A string representing the type of stroke join to render. All applicable values can be found on the w3c spec.
         * https://www.w3.org/TR/SVG/images/painting/linejoin.svg
         * Defaults to "round".
         */
        open var join: String

        /**
         * A number representing the miter limit for the stroke. Defaults to 1.
         */
        open var miter: Double

        /**
         * A number that represents the rotation of the path in the drawing space, in radians.
         */
        open var rotation: Double

        /**
         * A number that represents the uniform scale of the path in the drawing space.
         */
        open var scale: Double

        /**
         * A Two.Vector that represents x, y translation of the path in the drawing space.
         */
        open var translation: Vector

        /**
         * A reference to the Two.Group that contains this instance.
         */
        open var parent: Group

        /**
         * A Two.Utils.Collection of Two.Anchors that is two-way databound. Individual vertices may be manipulated.
         */
        open var vertices : Array<Anchor>

        /**
         * Boolean that describes whether the path is closed or not.
         */
        open var closed: Boolean

        /**
         * Boolean that describes whether the path is curved or not.
         */
        open var curved: Boolean

        /**
         * Boolean that describes whether the path should automatically dictate how Two.Anchors behave.
         * This defaults to true.
         */
        open var automatic: Boolean

        /**
         * A number, 0-1, that is mapped to the layout and order of vertices. It represents which of the vertices from
         * beginning to end should start the shape. Exceedingly helpful for animating strokes. Defaults to 0.
         */
        open var beginning: Double

        /**
         * A number, 0-1, that is mapped to the layout and order of vertices. It represents which of the vertices from
         * beginning to end should end the shape. Exceedingly helpful for animating strokes. Defaults to 1.
         */
        open var ending: Double

        /**
         * A boolean describing whether to render this shape as a clipping mask. This property is set automatically in
         * correspondence with Two.Group.mask. Defaults to false.
         */
        open var clip: Boolean

        /**
         * Returns a new instance of a Two.Path with the same settings.
         */
        fun clone(): Path

        /**
         * Anchors all vertices around the centroid of the group.
         */
        fun center()

        /**
         * Adds the instance to a Two.Group.
         */
        fun addTo(group: Group)

        /**
         * If added to a two.scene this method removes itself from it.
         */
        fun remove()

        /**
         * Returns an object with top, left, right, bottom, width, and height parameters representing the bounding box
         * of the path. Pass true if you're interested in the shallow positioning,
         * i.e in the space directly affecting the object and not where it is nested.
         */
        fun getBoundingClientRect(): BoundingRect

        /**
         * Removes the fill
         */
        fun noFill()

        /**
         * Removes the stroke
         */
        fun noStroke()

        /**
         * If curved goes through the vertices and calculates the curve. If not, then goes through the vertices and
         * calculates the lines.
         */
        fun plot()

        /**
         * Creates a new set of vertices that are lineTo anchors. For previously straight lines the anchors remain the
         * same. For curved lines, however, Two.Utils.subdivide is used to generate a new set of straight lines that are
         * perceived as a curve.
         */
        fun subdivide()
    }

    /**
     * Strangely, two.js documentation mentions but does not define this class
     * Parameters retrieved directly from the source:
     *  https://github.com/jonobr1/two.js/blob/dev/src/shapes/circle.js
     */
    open class Circle(ox: Double, oy: Double, r: Double, res: Double): Path

    /**
     * This is a class for creating a line in two.js. It inherits from Two.Path, so it has all the same properties and
     * functions as Two.Path.
     *
     *   construction var line = new Two.Line(x1, y1, x2, y2);
     *
     *  A line takes two sets of x, y coordinates. x1, y1 to define the left endpoint and x2, y2 to define the right
     *  endpoint.
     */
    open class Line(x1: Double, y1: Double, x2: Double, y2: Double): Path

    /**
     *  This is a class for creating a rectangle in two.js. It inherits from Two.Path, so it has all the same properties
     *  and functions as Two.Path.
     *
     *  construction var rectangle = new Two.Rectangle(x, y, width, height);
     *
     *  A rectangle takes a set of x, y coordinates as its origin (the center of the rectangle by default) and width,
     *  height parameters to define the width and height of the rectangle.
     */
    open class Rectangle(x: Double, y: Double, width: Double, height: Double): Path

    /**
     * This is a class for creating a rounded rectangle in two.js. It inherits from Two.Path, so it has all the same
     * properties and functions as Two.Path.
     *
     * construction var roundedRect = new Two.RoundedRectangle(x, y, width, height, radius);
     *
     *  A rounded rectangle takes a set of x, y coordinates as its origin (the center of the rounded rectangle by
     *  default) and width, height parameters to define the width and height of the rectangle. Lastly, it takes an
     *  optional radius number representing the radius of the curve along the corner of the rectangle. radius defaults
     *  to 1/12th the of the smaller value between width, height.
     */
    open class RoundedRectangle(x: Double, y: Double, width: Double, height: Double, radius: Double): Path

    /**
     * This is a class for creating an ellipse in two.js. It inherits from Two.Path, so it has all the same properties
     * and functions as Two.Path.
     *
     *  construction var ellipse = new Two.Ellipse(x, y, width, height);
     *
     *  An ellipse takes a set of x, y coordinates as its origin (the center of the ellipse by default) and width,
     *  height parameters to define the width and height of the ellipse.
     */
    open class Ellipse(x: Double, y: Double, width: Double, height: Double): Path

    /**
     *  This is a class for creating a star in two.js. It inherits from Two.Path, so it has all the same properties and functions as Two.Path.
     *
     *   construction var star = new Two.Star(x, y, or, ir, sides);
     *
     *  A star takes a set of x, y coordinates as its origin (the center of the star by default) and or parameter to
     *  define the outer radius of the star. Optionally you can define an ir inner radius for the star and sides for how
     *  many sides the star has. By default he ir is half the or and there are 5 sides.
     */
    open class Star(x:Double, y:Double, or:Double, ir:Double, sides:Long): Path

    /**
     * This is a class for creating a polygon, symmetrically multi-sided shape, in two.js. It inherits from Two.Path, so
     * it has all the same properties and functions as Two.Path.
     *
     * construction var polygon = new Two.Polygon(x, y, radius, sides);
     *
     * A polygon takes a set of x, y coordinates as its origin (the center of the polygon by default) and radius, sides
     * parameters to define the radius of the polygon and how many sides the polygon has. By default there are 3 sides,
     * a triangle.
     */
    open class Polygon(x:Double, y:Double, radius:Double, sides:Long): Path

    /**
     *  This is a container object for two.js — it can hold shapes as well as other groups. At a technical level it can
     *  be considered an empty transformation matrix. It is recommended to use two.makeGroup() in order to add groups to
     *  your instance of two, but it's not necessary. Unless specified methods return their instance of Two.Group for
     *  the purpose of chaining.
     *
     *  construction var group = new Two.Group();
     *
     *  If you are constructing groups this way instead of two.makeGroup(), then don't forget to add the group to the
     *  instance's scene, two.add(group).
     */
    open class Group {
        /**
         * The id of the group. In the svg renderer this is the same number as the id attribute given to the
         * corresponding node. i.e:
         * if group.id = 5 then document.querySelector('#two-' + group.id) will return the corresponding node.
         */
        open var id: String

        /**
         *A string representing the color for the stroke of all child shapes. Use strictly for setting. All valid css
         * representations of color are accepted.
         */
        open var stroke: String

        /**
         * A string representing the color for the area of all child shapes. Use strictly for setting. All valid css
         * representations of color are accepted.
         */
        open var fill: String

        /**
         * A number representing the thickness of all child shapes' strokes. Use strictly for setting.
         * Must be a positive number.
         */
        open var linewidth: Double

        /**
         * A number representing the opacity of all child shapes. Use strictly for setting. Must be a number 0-1.
         */
        open var opactiy: Double

        /**
         * A string representing the type of stroke cap to render for all child shapes. Use strictly for setting.
         * All applicable values can be found on the w3c spec. Defaults to "round".
         */
        open var cap: String

        /**
         * A string representing the type of stroke join to render for all child shapes. Use strictly for setting.
         * All applicable values can be found on the w3c spec. Defaults to "round".
         */
        open var join: String

        /**
         * A number representing the miter limit for the stroke of all child objects. Use strictly for setting.
         * Defaults to 1.
         */
        open var miter: Double

        /**
         * A number that represents the rotation of the group in the drawing space, in radians.
         */
        open var rotation: Double

        /**
         * A number that represents the uniform scale of the group in the drawing space.
         */
        open var scale: Double

        /**
         * A Two.Vector that represents x, y translation of the group in the drawing space.
         */
        open var translation: Vector

        /**
         * A map of all the children of the group.
         */
        open var children: Array<Any>

        /**
         * A reference to the Two.Group that contains this instance.
         */
        open var parent: Group

        /**
         * A reference to the Two.Path that masks the content within the group. Automatically sets the referenced
         * Two.Path.clip to true.
         */
        open var mask: Path

        /**
         * Returns a new instance of a Two.Group with the same settings.
         */
        fun clone(): Group

        /**
         * Anchors all children around the centroid of the group.
         */
        fun center()

        /**
         * Adds the instance to a Two.Group. In many ways the inverse of two.add(object).
         */
        fun addTo(group:Group)

        /**
         * Add one or many shapes / groups to the instance. Objects can be added as arguments, two.add(o1, o2, oN), or
         * as an array depicted above.
         */
        fun add(vararg items: Any)

        /**
         * Remove one or many shapes / groups to the instance. Objects can be removed as arguments,
         * two.remove(o1, o2, oN), or as an array depicted above.
         */
        fun remove(vararg items: Any)

        /**
         * Returns an object with top, left, right, bottom, width, and height parameters representing the bounding box
         * of the path. Pass true if you're interested in the shallow positioning, i.e in the space directly affecting
         * the object and not where it is nested.
         */
        fun getBoundingClientRect(shallow: Boolean):BoundingRect

        /**
         * Remove the fill from all children of the group.
         */
        fun noFill()

        /**
         * Remove the stroke from all children of the group.
         */
        fun noStroke()
    }

    /**
     * This is the atomic coordinate representation for two.js. A Two.Vector is different and specific to two.js because
     * its main properties, x and y, trigger events which allow the renderers to efficiently change only when they need
     * to. Unless specified methods return their instance of Two.Vector for the purpose of chaining.
     */
    open class Vector {
        /**
         * The x value of the vector.
         */
        var x: Double

        /**
         * The y value of the vector.
         */
        var y: Double

        /**
         * Set the x, y properties of the vector to the arguments x, y.
         */
        fun set(x: Double, y: Double)

        /**
         * Set the x, y properties of the vector from another vector, v.
         */
        fun copy(v: Vector)

        /**
         * Set the x, y properties of the vector to 0.
         */
        fun clear()

        /**
         * Add to vectors together. The sum of the x, y values will be set to the instance.
         */
        fun clone()

        /**
         * Add to vectors together. The sum of the x, y values will be set to the instance.
         */
        fun add(v1: Vector, v2: Vector)

        /**
         * Add the x, y values of the instance to the values of another vector. Set the sum to the instance's values.
         */
        fun addSelf(v: Vector)

        /**
         * Subtract two vectors. Set the difference to the instance.
         */
        fun sub(v: Vector)

        /**
         * Subtract a vector, v, from the instance.
         */
        fun subSelf(v: Vector)

        /**
         * Multiply the x, y values of the instance by another vector's, v, x, y values.
         */
        fun multiplySelf(v: Vector)

        /**
         * Multiply the x, y values of the instance by another number, value.
         */
        fun multiplyScalar(value: Double)

        /**
         * Divide the x, y values of the instance by another number, value.
         */
        fun divideScalar(value: Double)

        /**
         * Toggle the sign of the instance's x, y values.
         */
        fun negate()

        /**
         * Return the dot product of the instance and a vector, v.
         */
        fun dot(v: Vector)

        /**
         * Return the length of the vector squared.
         */
        fun lengthSquared()

        /**
         * Return the length of the vector.
         */
        fun length()

        /**
         * Reduce the length of the vector to the unit circle.
         */
        fun normalize()

        /**
         * Return the distance from the instance to another vector, v.
         */
        fun distanceTo(v: Vector)

        /**
         * Return the distance squared from the instance to another vector, v.
         */
        fun distanceToSquared(v: Vector)

        /**
         * Set the length of a vector to a specified distance, length.
         */
        fun setLength(length: Double)

        /**
         * Return a boolean representing whether or not the vectors are within 0.0001 of each other. This fuzzy equality
         * helps with Physics libraries.
         */
        fun equals(v: Vector): Boolean

        /**
         * Linear interpolation of the instance's current x, y values to the destination vector, v, by an amount, t. Where t is a value 0-1.
         */
        fun lerp(v: Vector, t: Double)

        /**
         * Returns a boolean describing the length of the vector less than 0.0001.
         */
        fun isZero(): Boolean
    }


    /**
     *  Taken from the Adobe Illustrator nomenclature a Two.Anchor represents an anchor point in two.js. This class
     *  delineates to the renderer what action to take when plotting points. It inherits all properties and methods from
     *  Two.Vector. As a result, Two.Anchor can be used as such. Depending on its command, anchor points may or may not
     *  have corresponding control points to describe how their bezier curves should be rendered.
     *
     *  construction var anchor = new Two.Anchor(x, y, lx, ly, rx, ry, command);
     *
     *  A Two.Anchor can take initial positions of x and y to orient the point. lx and ly describe where the left
     *  control point will reside. Likewise rx and ry describe where the right control point will reside. Finally, the
     *  command describes what action the renderer will take once rendering. A more detailed description of commands can
     *  be found on the w3c and the available commands in two.js can be found under Two.Commands.
     */
    open class Anchor(x: Double, y: Double, lx: Double, ly: Double, rx: Double, ry: Double, command: Commands): Vector {
        var command: Commands

        /**
         *  An object that exists only when anchor.command is Two.Commands.curve. It holds the anchor's control point
         *  Two.Vectors and describes what the make up of the curve will be.
         *
         *  right anchor.controls.right
         *
         *  A Two.Vector that represents the position of the control point to the “right” of the anchor's position.
         *  To further clarify, if you were to orient the anchor so that it was normalized and facing up, this control
         *  point would be to the right of it.
         *
         *  left anchor.controls.left
         *
         *  A Two.Vector that represents the position of the control point to the “left” of the anchor's position.
         *  To further clarify, if you were to orient the anchor so that it was normalized and facing up, this control
         *  point would be to the left of it.

         */
        //TODO: Represent acutal type
        var controls: Any?

        /**
         * Convenience method to add event bubbling to an attached path.
         */
        fun listen()

        /**
         * Convenience method to remove event bubbling to an attached path.
         */
        fun ignore()
    }


    /**
     *  This is a class for defining how gradients are colored in two.js. By itself a Two.Stop doesn't render anything
     *  specifically to the screen.
     *
     *  construction var stop = new Two.Stop(offset, color, opacity);
     *
     *  A stop takes a 0 to 1 offset value which defines where on the trajectory of the gradient the full color is
     *  rendered. It also takes a color which is a css string representing the color value and an optional opacity
     *  which is also a 0 to 1 value.
     */
    open class Stop(offset: Double, color: String, opactiy: Double) {
        /**
         * A 0 to 1 offset value which defines where on the trajectory of the gradient the full color is rendered.
         */
        var offset: Double
        /**
         * A css string that represents the color of the stop.
         */
        var color: String
        /**
         * A 0 to 1 value which defines the opacity of the stop.
         */
        var opactiy: Double
        /**
         * Clones a stop. Returns a new Two.Stop.
         */
        fun clone(): Stop
    }

    /**
     *  This is a class for creating a LinearGradient in two.js. By itself a Two.LinearGradient doesn't render anything
     *  specifically to the screen. However, in conjunction with a Two.Path you can style Two.Path.fill or
     *  Two.Path.stroke with a Two.LinearGradient to render a gradient for that part of the Two.Path. Check the examples
     *  page for exact usage.
     *
     *  construction var linearGradient = new Two.LinearGradient(x1, y1, x2, y2, stops);
     *
     *  A linear gradient takes two sets of x, y coordinates to define the endpoints of the styling. These coordinates
     *  are relative to the origin of a Two.Path. This typically means you'll want to go from a negative quadrant to a
     *  positive quadrant in order for the gradient to render correctly. Lastly it takes an array of Two.Stops which
     *  represent the color value along the gradient's trajectory.
     */
    open class LinearGradient(x1: Double, y1: Double, x2: Double, y2: Double, stops: Array<Stop>) {
        /**
         * A Two.Vector that represents the position of the x, y coordinates to the “left” of the gradient's two end points.
         */
        var left: Vector
        /**
         * A Two.Vector that represents the position of the x, y coordinates to the “right” of the gradient's two end points.
         */
        var right: Vector
        /**
         * Defines how the gradient is rendered by the renderer. For more details see the w3c svg spec.
         */
        var spread: String
        /**
         * A Two.Utils.Collection of Two.Stops that is two-way databound. Individual stops may be manipulated.
         */
        var stops: Array<Stop>
        /**
         * A function to clone a linearGradient. Also, clones each Two.Stop in the linearGradient.stops array.
         */
        fun clone(): LinearGradient
    }

    /**
     *  This is a class for creating a RadialGradient in two.js. By itself a Two.RadialGradient doesn't render anything
     *  specifically to the screen. However, in conjunction with a Two.Path you can style Two.Path.fill or
     *  Two.Path.stroke with a Two.RadialGradient to render a gradient for that part of the Two.Path. Check the examples
     *  page for exact usage.
     *
     *  construction var radialGradient = new Two.radialGradient(x, y, radius, stops, fx, fy);
     *
     *  A radial gradient takes a set of x, y coordinates to define the center of the styling. These coordinates are
     *  relative to the origin of a Two.Path. This typically means you'll want to set these to 0, 0. Next define how
     *  large the radius for the radial gradient is. Lastly, pass an array of Two.Stops to define the coloring of the
     *  radial gradient. Optionally, you can pass a set of x, y coordinates to define the focal position of the radial
     *  gradient's trajectory.
     */
    open class RadialGradient(x: Double, y: Double, radius: Double, stops: Array<Stop>, fx: Double, fy: Double) {
        /**
         * A Two.Vector that represents the position of the x, y coordinates at the center of the gradient.
         */
        var center: Vector
        /**
         * A Double representing the radius of the radialGradient.
         */
        var radius: Double
        /**
         * A Two.Vector that represents the position of the x, y coordinates as the focal point for the gradient's trajectory.
         */
        var focal: Vector
        /**
         * Defines how the gradient is rendered by the renderer. For more details see the w3c svg spec.
         * https://www.w3.org/TR/SVG/pservers.html#LinearGradientElementSpreadMethodAttribute
         */
        var spread: String
        /**
         * A Two.Utils.Collection of Two.Stops that is two-way databound. Individual stops may be manipulated.
         */
        var stops: Array<Stop>
        /**
         * A function to clone a radialGradient. Also, clones each Two.Stop in the radialGradient.stops array.
         */
        fun clone(): RadialGradient
    }

    /**
     *  This is a class for creating, manipulating, and rendering text dynamically in Two.js. As such it is rather
     *  primitive. You can use custom fonts through @Font Face spec. However, you do not have control over the glyphs
     *  themselves. If you'd like to mainpulate that specifically it is recommended to use SVG Interpreter. A text
     *  object extends Two.Shape.
     *
     *  construction var text = new Two.Text(message, x, y, styles);
     *
     *  A text object takes in a message, the string representation of what will be displayed. It then takes an x and y
     *  number where the text object will be placed in the group. Finally, an optional styles object to apply any other
     *  additional styles. Applicable properties to affect can be found in Two.Text.Properties.
     */
    open class Text(message: String, x: Double, y: Double, styles:Any?): Path {
        /**
         * A string representing the text that will be rendered to the stage.
         */
        var value: String
        /**
         * A string representing the css font-family to be applied to the rendered text. Default value is 'sans-serif'.
         */
        var family: String
        /**
         * A number representing the text's size to be applied to the rendered text. Default value is 13.
         */
        var size: Double
        /**
         * A number representing the leading, a.k.a. line-height, to be applied to the rendered text. Default value is
         * 17.
         */
        var leading: Double
        /**
         * A string representing the horizontal alignment to be applied to the rendered text. e.g: 'left', 'right', or
         * 'center'. Default value is 'middle'.
         */
        var alignment: String
        /**
         * A number representing the linewidth to be applied to the rendered text. Default value is 1.
         */
        var style: String
        /**
         * A string representing the font style to be applied to the rendered text. e.g: 'normal' or 'italic'.
         * Default value is 'normal'.
         */
        var weight: Double
        /**
         * A number or string representing the weight to be applied to the rendered text. e.g: 500 or 'normal'. For more
         * information see the Font Weight Specification. Default value is 500.
         */
        var decoration: String
        /**
         * A string representing the text decoration to be applied to the rendered text. e.g: 'none', 'underlined', or
         * 'strikethrough'. Default value is 'none'
         */
        var baseline: String
    }

    enum class Commands {
        move, line, curve
    }
}

external interface TwoConstructionParams {

    var type: Two.Types?
    /**
     * Set the width of the drawing space. Disregarded if params.fullscreen is set to true. Default width is 640 pixels.
     */
    var width: Double?
    /**
     * Set the height of the drawing space. Disregarded if params.fullscreen is set to true. Default height is 480 pixels.
     */
    var height: Double?
    /**
     * A boolean to automatically add the instance to draw on requestAnimationFrame.
     * This is a convenient substitute so you don't have to call two.play().
     */
    var autostart: Boolean?

    /**
     * A boolean to set the drawing space of the instance to be fullscreen or not.
     * If set to true then width and height parameters will not be respected.
     */
    var fullscreen: Boolean?

    /**
     * Set the resolution ratio for canvas and webgl renderers.
     * If left blank two.js automatically infers the ratio based on the devicePixelRatio api.
     */
    var raiot: Double?
}

external interface BoundingRect {
    var top: Double
    var left: Double
    var right: Double
    var bottom: Double
    var width: Double
    var height: Double
}