package com.volodya.Fabrics

import javax.swing.InputVerifier
import javax.swing.JFrame
import javax.swing.JTextField

class TextFieldFabric(private val frame: JFrame) {
    fun build(verifier: InputVerifier): JTextField {
        val textField = JTextField("1.0")
        textField.addActionListener {
            verifier.verify(textField)
            frame.requestFocus()
        }
        return textField
    }
}