package com.example.demo.app

import com.example.demo.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import java.nio.file.Paths


class MyApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with (stage) {
            width = 500.0
            height = 490.0
        }

        setStageIcon(Image("images/Tardis-133x200.jpg"))

        super.start(stage)
    }
}