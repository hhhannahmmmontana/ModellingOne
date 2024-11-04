package com.volodya

import com.volodya.Fabrics.TextFieldFabric
import com.volodya.formats.UnsignedFloatVerifier
import org.jfree.chart.*
import org.jfree.data.general.Dataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.general.DefaultValueDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.*
import java.awt.geom.Rectangle2D
import javax.swing.*

fun main() {
    val frame = JFrame("Моделирование")
    frame.layout = FlowLayout()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(400, 400)
    frame.setLocationRelativeTo(null)

    val series = XYSeries("Data")
    series.add(0f, 0f)
    series.add(20f, 20f)
    series.add(50f, 50f)
    val dataset = XYSeriesCollection(series)
    val chart = ChartFactory.createXYLineChart("any", "gay", "porn", dataset)

    frame.add(ChartPanel(chart))
    frame.pack()
    frame.isVisible = true
}