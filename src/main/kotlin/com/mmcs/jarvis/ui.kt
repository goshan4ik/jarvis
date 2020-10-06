package com.mmcs.jarvis;

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane
import javafx.scene.paint.Color

import tornadofx.*

class JarvisView : View("Jarvis") {

    override val root = Pane()

    private var canvas: Canvas by singleAssign()
    private val points = ArrayList<Point>()

    init {

        val imageContainer = vbox {
            hbox {
                button("Построить оболочку") {
                    action {
                        val convexHull = JarvisConvexHull(points).build()
                        val gc = canvas.graphicsContext2D
                        gc.beginPath()
                        val firstPoint = convexHull[0]
                        gc.moveTo(firstPoint.x, canvas.height - firstPoint.y)
                        for (i in 1 until convexHull.size) {
                            gc.lineTo(convexHull[i].x, canvas.height - convexHull[i].y)
                        }
                        gc.lineTo(firstPoint.x, canvas.height - firstPoint.y)
                        gc.closePath()
                        gc.stroke()
                    }
                    hboxConstraints {
                        margin = Insets(15.0)
                    }
                }

                button("Очистить") {
                    action {
                        prepareCanvasForDrawing()
                        points.clear()
                    }
                    hboxConstraints {
                        margin = Insets(15.0)
                    }
                }
            }

            hbox {
                vboxConstraints {
                    margin = Insets(15.0)
                }
                canvas = canvas {
                    onMouseClicked = EventHandler {
                        drawPoint(it.x, it.y)
                        points.add(Point(it.x, height - it.y))
                    }
                    width = 1275.0
                    height = 700.0
                }
            }
        }
        with(root) {
            addChildIfPossible(imageContainer, 0)
        }
        prepareCanvasForDrawing()
    }

    private fun drawPoint(x: Double, y: Double) {
        canvas.graphicsContext2D.fillOval(x - 2.5, y - 2.5, 5.0, 5.0)
    }

    private fun prepareCanvasForDrawing() {
        val gc = canvas.graphicsContext2D
        gc.fill = Color.RED
        gc.fillRect(0.0, 0.0, canvas.width, canvas.height)
        gc.fill = Color.BLACK
        gc.lineWidth = 5.0
    }
}