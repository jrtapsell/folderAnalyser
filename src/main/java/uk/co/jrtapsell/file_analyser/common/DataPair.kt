package uk.co.jrtapsell.file_analyser.common

import javafx.scene.chart.PieChart

data class DataPair(val name: String, val value:Double) {
    fun makePieData(): javafx.scene.chart.PieChart.Data {
        return javafx.scene.chart.PieChart.Data(name, value)
    }

    override fun toString(): String {
        return String.format("%30s %10.0f", name, value)
    }
}