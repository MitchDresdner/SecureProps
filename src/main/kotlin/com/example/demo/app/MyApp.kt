package com.example.demo.app

import com.example.demo.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import java.nio.file.Paths

class MyApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with (stage) {
            width = 640.0
            height = 480.0
        }

        var myPath = Paths.get("").toAbsolutePath().toString()
        setStageIcon(Image("file:///${myPath}/images/Mulesoft.png"))

        super.start(stage)
    }
}