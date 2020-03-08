package com.example.demo.app

import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.layout.BackgroundPosition
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px
import java.net.URI
import kotlin.reflect.jvm.internal.impl.incremental.components.Position

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
    }

    init {
        root {
            backgroundImage += URI("/images/Tardis-133x200.jpg")
//            backgroundInsets +=   box(10.px, 10.px,200.px, 300.px)      // box(2.px)
            backgroundPosition += BackgroundPosition.DEFAULT
//            padding = box(10.px, 10.px,0.px, 300.px)
            backgroundRepeat += BackgroundRepeat.NO_REPEAT to BackgroundRepeat.NO_REPEAT
        }
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
    }
}