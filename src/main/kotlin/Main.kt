package com.volodya

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import javax.swing.JFrame
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

val M = 2300f
val v0 = 35f
val g = 1.62f
val h0 = 1800f
val u = 3660f
val mu = 15f
val deltaT = 1f / 10000f

fun v1(h: Float): Float {
    return sqrt(v0.pow(2) + 2 * g * (h0 - h))
}
fun v1Init(): Float {
    return v1(0f)
}

fun v2a(mass: Float): Float {
    return deltaT * ((mu * u / mass) - g)
}

fun findT(): Float {
    var v1 = v1Init()
    var t1 = (v1 - v0) / g
    var h = h0 - (v1 + v0) * t1 / 2
    var v2 = v(0f, v1)
    var mass = M
    var ans = 0f
    while (v2 <= 0f) {
        if ((h <= 0 && !valid(v2))) {
            v1 -= nextFallV()
            t1 = (v1 - v0) / g
            h = h0 - (v1 + v0) * t1 / 2
            v2 = v(0f, v1)
            mass = M
            continue
        }
        if (h <= 0 && valid(v2)) {
            ans = t1
            v1 -= nextFallV()
            t1 = (v1 - v0) / g
            h = h0 - (v1 + v0) * t1 / 2
            v2 = v(0f, v1)
            mass = M
            continue
        }
        h += v2 * deltaT
        v2 += v2a(mass)
        mass -= deltaT * mu
    }
    print(h0 - ((v0 + v1) / 2) * t1)
    return t1
}

fun valid(v: Float): Boolean {
    return v <= 0 && v >= -3
}

fun v(t: Float, v1: Float): Float {
    return u * ln(M / (M - mu * t)) - g * t - v1
}

fun nextFallV(): Float {
    return g * deltaT
}

fun nextReactV(mass: Float): Float {
    return deltaT * (u * mu) / mass
}

fun simulate(tMin: Float) {
    val seriesHT = XYSeries("h от t")
    val seriesAT = XYSeries("a от t")
    val seriesVT = XYSeries("v от t")
    var h = h0
    var t = 0f
    var v = -v0
    var mass = M
    while (h > 0 && h <= h0) {
        if (v > 0) {
            break
        }
        seriesHT.add(t, h)
        seriesVT.add(t, v)
        h += v * deltaT
        var a = -g
        v -= nextFallV()
        if (t >= tMin) {
            v += nextReactV(mass)
            a += u * mu / mass
            mass -= mu * deltaT
        }
        seriesAT.add(t, a)
        t += deltaT
    }

    val ds1 = XYSeriesCollection(seriesHT)
    val ds2 = XYSeriesCollection(seriesVT)
    val ds3 = XYSeriesCollection(seriesAT)

    val chart1 = ChartFactory.createXYLineChart("График h от t", "t, с", "h, м", ds1)
    val frame1 = ChartFrame("График h от t", chart1)
    frame1.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame1.setSize(666, 500)
    frame1.setLocationRelativeTo(null)
    frame1.isVisible = true

    val chart2 = ChartFactory.createXYLineChart("График v от t", "t, с", "v, м/с", ds2)
    val frame2 = ChartFrame("График v от t", chart2)
    frame2.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame2.setSize(666, 500)
    frame2.setLocationRelativeTo(null)
    frame2.isVisible = true

    val chart3 = ChartFactory.createXYLineChart("График a от t", "t, с", "a, м/с²", ds3)
    val frame3 = ChartFrame("График a от t", chart3)
    frame3.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame3.setSize(666, 500)
    frame3.setLocationRelativeTo(null)
    frame3.isVisible = true
}

fun main() {
    val series = simulate(findT())
}