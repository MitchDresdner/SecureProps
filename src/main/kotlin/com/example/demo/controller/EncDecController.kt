package com.example.demo.controller

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class EncDecController : Controller() {

    var conversionRes  = SimpleStringProperty("")

    init {
    }

    fun runExec(cmd: String) {

        val process: Process = Runtime.getRuntime().exec(cmd)

        val sc = Scanner(process.inputStream)
        conversionRes = SimpleStringProperty(sc.nextLine())

        //println(res)
        sc.close()
    }

    fun String.runCommand(workingDir: File): String? {
        try {
            val parts = this.split("\\s".toRegex())
            val proc = ProcessBuilder(*parts.toTypedArray())
                    .directory(workingDir)
                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
                    .redirectError(ProcessBuilder.Redirect.PIPE)
                    .start()

            proc.waitFor(60, TimeUnit.MINUTES)
            return proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}