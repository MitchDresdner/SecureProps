package com.example.demo.view

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Toggle
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import org.omg.SendingContext.RunTime
import tornadofx.*
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class MainView : View("SecureProps EncDec") {

    val cryptoAlg = FXCollections.observableArrayList(
            "AES",
            "Blowfish",
            "DES",
            "DESede",
            "RC2",
            "RCA"
    )

    val cryptoCipher = FXCollections.observableArrayList(
            "CBC",
            "CFB",
            "ECB",
            "OFB"
    )

    private var encDec = "encrypt"
    private var secretField = SimpleStringProperty("mulesofttfoselum")
    private var textField = SimpleStringProperty("")
    private var textFieldRes = SimpleStringProperty("")

    override val root = vbox {

        //var textAreaRes: TextField by singleAssign()
        val encdecGrp = ToggleGroup()
        val selectedAlg = SimpleStringProperty()
        val selectedCipher = SimpleStringProperty()

        label {
            vboxConstraints {
                margin = Insets(20.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            alignment = Pos.CENTER
            text = "Secure Property Encode/Decode"
        }

        hbox {

            hboxConstraints {
                //marginLeft = 20.0
                padding = Insets(0.0,0.0,0.0,20.0)
                alignment = Pos.BASELINE_LEFT
                spacing = 20.0
                hGrow = Priority.ALWAYS
            }

            combobox(selectedAlg, cryptoAlg) {
                selectionModel.selectFirst()
            }

            combobox(selectedCipher, cryptoCipher) {
                selectionModel.selectFirst()
            }
        }

        hbox {

            hboxConstraints {
                marginLeft = 20.0
                alignment = Pos.BASELINE_CENTER
                spacing = 20.0
                hGrow = Priority.ALWAYS
            }

            vbox {
                vboxConstraints {
                    marginLeft = 20.0
                    vGrow = Priority.ALWAYS
                }

                vbox {
                    radiobutton("Encode", encdecGrp, "ENC" ) {
                        isSelected = true
                        setOnAction { encDec = "encrypt" }
                    }
                    radiobutton("Decode", encdecGrp, "DEC") {
                        setOnAction {  encDec ="decrypt" }
                    }
                    vbox {
                        vboxConstraints {
                            marginBottom = 20.0
                            vGrow = Priority.ALWAYS
                        }
                        button {
                            vboxConstraints {
                                margin = Insets(20.0,20.0,20.0,20.0)
                                vGrow = Priority.ALWAYS
                            }
                            tooltip("Encode or Decode secrets") {
                                font = Font.font("Verdana")
                            }
                            text = "Run"
                            action {
//                                var t: Toggle = encdecGrp.selectedToggle
//                                val t1 : ReadOnlyObjectProperty<Toggle>? = encdecGrp.selectedToggleProperty()
                                val res = runExec(
                                        String.format(
                                                "java -jar /tools/lib/secure-properties-tool.jar string %s %s %s %s \"%s\"",
                                                    encDec,
                                                    selectedAlg.value,
                                                    selectedCipher.value,
                                                    secretField.value,
                                                    textField.value

                                        ))
                                textFieldRes.set(res)
                                println("Clicked ${selectedAlg.value} |  ${selectedCipher.value} | ${encdecGrp.selectedToggle.userData}")
//                                print(t)
//                                print("Got $foo")
                            }
                        }
                    }
                }
            }
        }


        passwordfield {
            vboxConstraints {
                margin = Insets(20.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            prefHeight = 25.0
            prefWidth = 321.0
            promptText = "Secret"
            this.textProperty().bindBidirectional(secretField)
        }

        //textarea(textAreaRes)
        textarea {
            vboxConstraints {
                margin = Insets(20.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            promptText="Enter secret to encode, or result"
            this.textProperty().bindBidirectional(textField)
        }

        textarea {
            vboxConstraints {
                margin = Insets(20.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            promptText="Result"
            this.textProperty().bindBidirectional(textFieldRes)
        }

    }

    fun runExec(cmd: String): String? {
        val process: Process = Runtime.getRuntime().exec(cmd)

        val sc = Scanner(process.inputStream)
        val res = sc.nextLine()
        println(res)
        sc.close()

        return res
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