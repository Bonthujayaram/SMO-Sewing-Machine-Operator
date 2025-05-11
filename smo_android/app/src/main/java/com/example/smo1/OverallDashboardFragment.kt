package com.example.smo1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class OverallDashboardFragment : Fragment() {

    private lateinit var pieChart: PieChart
    private lateinit var barChartType2: BarChart
    private lateinit var barChartType3: BarChart
    private lateinit var candleChart: CandleStickChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overall_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Bind views from your XML
        pieChart = view.findViewById(R.id.pieChart)
        barChartType2 = view.findViewById(R.id.barChartType2)
        barChartType3 = view.findViewById(R.id.barChartType3)
        candleChart = view.findViewById(R.id.candleChart) // Make sure to add this in XML if needed

        // Setup charts directly
        setupCharts()
    }

    private fun setupCharts() {
        // Setup Pie Chart
        setupPieChart()

        // Setup Bar Charts
        setupBarChart(barChartType2, listOf(30f, 70f, 90f), "Workers", Color.BLUE)
        setupBarChart(barChartType3, listOf(25f, 50f, 95f), "Machines", Color.RED)

        // Setup Candle Chart (if needed)
        // setupCandleChart() // Uncomment if you want to keep the candle chart
    }

    private fun setupPieChart() {
        val pieEntries = listOf(
            PieEntry(30f, "30%"),
            PieEntry(70f, "Remaining")
        )
        val pieDataSet = PieDataSet(pieEntries, "Pie Chart").apply {
            colors = listOf(Color.GREEN, Color.LTGRAY)
        }
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.invalidate() // Refresh the chart
    }

    private fun setupBarChart(barChart: BarChart, values: List<Float>, label: String, color: Int) {
        val barEntries = values.mapIndexed { index, value -> BarEntry(index.toFloat(), value) }
        val barDataSet = BarDataSet(barEntries, label).apply { this.color = color }
        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.invalidate() // Refresh the chart
    }

    // Uncomment and implement this method if you want to add a candle chart
    /*
    private fun setupCandleChart() {
        // Implement candle chart setup here
    }
    */
}