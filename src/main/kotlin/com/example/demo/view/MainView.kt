package com.example.demo.view

import com.example.demo.controller.EncDecController
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*

class MainView : View("SecureProps EncDec") {
    private val controller: EncDecController by inject()


    private val cryptoAlg: ObservableList<String> = FXCollections.observableArrayList(
            "AES",
            "Blowfish",
            "DES",
            "DESede",
            "RC2",
            "RCA"
    )

    private val cryptoCipher: ObservableList<String> = FXCollections.observableArrayList(
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
            style {
                fontWeight = FontWeight.BOLD
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
                                runAsync {
                                    controller.runExec(
                                            String.format(
                                                    "java -jar /tools/lib/secure-properties-tool.jar string %s %s %s %s \"%s\"",
                                                    encDec,
                                                    selectedAlg.value,
                                                    selectedCipher.value,
                                                    secretField.value,
                                                    textField.value
                                            ))
                                } ui {
                                    val res = controller.conversionRes.value
                                    textFieldRes.set(res)
                                    println("Clicked ${selectedAlg.value} |  ${selectedCipher.value} | ${encdecGrp.selectedToggle.userData}")
                                }
                            }
                        }
                    }
                }
            }
        }

        label {
            vboxConstraints {
                margin = Insets(10.0,20.0,2.0,20.0)
                vGrow = Priority.ALWAYS
            }
            style {
                fontWeight = FontWeight.BOLD
            }
            text = "Password"
        }
        passwordfield {
            vboxConstraints {
                margin = Insets(2.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            prefHeight = 25.0
            prefWidth = 321.0
            promptText = "Secret"
            this.textProperty().bindBidirectional(secretField)
        }

        label {
            vboxConstraints {
                margin = Insets(2.0,20.0,2.0,20.0)
                vGrow = Priority.ALWAYS
            }
            style {
                fontWeight = FontWeight.BOLD
            }
            text = "Secret (encrypted secret when decoding)"
        }
        textarea {
            vboxConstraints {
                margin = Insets(2.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            promptText="Enter secret to encode, or result"
            this.textProperty().bindBidirectional(textField)
        }

        label {
            vboxConstraints {
                margin = Insets(2.0,20.0,2.0,20.0)
                vGrow = Priority.ALWAYS
            }
            style {
                fontWeight = FontWeight.BOLD
            }
            text = "Encoded secret (decrypted secret when decoding)"
        }
        textarea {
            vboxConstraints {
                margin = Insets(2.0,20.0,20.0,20.0)
                vGrow = Priority.ALWAYS
            }
            promptText="Result"
            this.textProperty().bindBidirectional(textFieldRes)
        }

    }
}