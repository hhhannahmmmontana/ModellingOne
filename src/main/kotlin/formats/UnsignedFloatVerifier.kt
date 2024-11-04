package com.volodya.formats

import javax.swing.InputVerifier
import javax.swing.JComponent
import javax.swing.JTextField

class UnsignedFloatVerifier(private val defaultValue: Float) : InputVerifier() {
    override fun verify(input: JComponent?): Boolean {
        if (input == null || input !is JTextField) {
            return false
        }
        val text = input.text.toFloatOrNull()
        if (text == null) {
            input.text = defaultValue.toString()
            return false
        } else {
            input.text = text.toString()
            return true
        }
    }
}