package com.mmcs.jarvis

import javafx.application.Application
import javafx.stage.Stage
import tornadofx.App

fun main(args: Array<String>) {
    Application.launch(World::class.java, *args)
}

class World : App() {
    override val primaryView = JarvisView::class
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1300.0
        stage.height = 800.0
    }
}