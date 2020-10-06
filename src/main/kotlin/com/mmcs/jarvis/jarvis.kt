package com.mmcs.jarvis

import kotlin.math.abs
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double)

data class Line(val start: Point, val end: Point) {

    val length: Double
    get() {
        val dX = abs(end.x - start.x)
        val dY = abs(end.y - start.y)
        return sqrt(dX * dX + dY * dY)
    }
}

class JarvisConvexHull(points: List<Point>) {

    private val points: MutableList<Point>
    private val convexHull: MutableList<Point>

    init {
        this.points = ArrayList(points)
        this.convexHull = ArrayList(points.size)
    }

    fun build(): List<Point> {
        val firstPoint = findFirstPoint()
        convexHull.add(firstPoint)
        val secondPoint = findSecondPoint(firstPoint)
        convexHull.add(secondPoint)
        points.remove(secondPoint)
        while (true) {
            val nextPoint = getNextPoint()
            if (nextPoint != firstPoint) {
                convexHull.add(nextPoint)
                points.remove(nextPoint)
            } else {
                break
            }
        }
        return convexHull
    }

    private fun getNextPoint() =
        points.zip(points.map {
            val previousAddedEdge = Line(convexHull[convexHull.size - 2], convexHull[convexHull.size - 1])
            val lineCandidate = Line(convexHull[convexHull.size - 1], it)
            calcCosBetweenLines(previousAddedEdge, lineCandidate)
        }).minBy { it.second }!!.first

    private fun findFirstPoint() = points.sortedWith(compareBy({it.x}, {it.y}))[0]

    private fun findSecondPoint(firstPoint: Point): Point {
        val tempPoint = Point(firstPoint.x + 1, firstPoint.y)
        convexHull.add(0, tempPoint)
        val secondPoint = getNextPoint()
        convexHull.removeAt(0)
        return secondPoint
    }
}

fun calcCosBetweenLines(first: Line, second: Line): Double {
    if (first.end != second.start) {
        throw NotImplementedError("не реализован метод расчета угла, если начало однойлинии не совпадает с концом второй")
    }
    val third = Line(first.start, second.end)
    val b = first.length
    val c = second.length
    val a = third.length
    return (b * b + c * c - a * a) / (2 * b * c)
}