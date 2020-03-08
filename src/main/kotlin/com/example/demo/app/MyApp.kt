package com.example.demo.app

import com.example.demo.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*
import java.nio.file.Paths
import javax.imageio.ImageIO

class MyApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with (stage) {
            width = 500.0
            height = 480.0
        }

        var myPath = Paths.get("").toAbsolutePath().toString()
        //setStageIcon(Image("file:///${myPath}/images/Tardis-133x200.jpg"))      // Favico.png
        // Good ./images: setStageIcon(Image("file:images/Tardis-133x200.jpg"))
        //val image = ImageIO.read(javaClass.getResource("images/Tardis-133x200.jpg").toString())
        setStageIcon(Image("images/Tardis-133x200.jpg"))

        super.start(stage)
    }
}