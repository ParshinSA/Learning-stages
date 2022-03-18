package com.example.weatherapplication.app.framework.datasource_impl.statistical

import com.example.weatherapplication.app.framework.database.models.report.FieldValue
import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.data_source.statistical.CalculationOfAverageWeatherStatisticsDataSource
import com.example.weatherapplication.domain.ReportingPeriod
import io.reactivex.Observable

class CalculationOfAverageWeatherStatisticsDataSourceImpl() :
    CalculationOfAverageWeatherStatisticsDataSource {

    override fun calculate(
        listOfWeatherStatistics: List<WeatherStatistic>,
        period: ReportingPeriod
    ): WeatherStatistic {
        val sumStatistics = calculateSumWeatherStatistics(listOfWeatherStatistics)

        return WeatherStatistic(
            temperature = FieldValue(sumStatistics.temperature.medianValue / period.quantity),
            pressure = FieldValue(sumStatistics.pressure.medianValue / period.quantity),
            humidity = FieldValue(sumStatistics.humidity.medianValue / period.quantity),
            wind = FieldValue(sumStatistics.wind.medianValue / period.quantity),
            precipitation = FieldValue(sumStatistics.precipitation.medianValue / period.quantity),
        )
    }

    private fun calculateSumWeatherStatistics(list: List<WeatherStatistic>): WeatherStatistic {
        return Observable.fromIterable(list)
            .scan { accumulator: WeatherStatistic, itemDataHistory: WeatherStatistic ->
                WeatherStatistic(
                    temperature = FieldValue(accumulator.temperature.medianValue + itemDataHistory.temperature.medianValue),
                    pressure = FieldValue(accumulator.pressure.medianValue + itemDataHistory.pressure.medianValue),
                    humidity = FieldValue(accumulator.humidity.medianValue + itemDataHistory.humidity.medianValue),
                    wind = FieldValue(accumulator.wind.medianValue + itemDataHistory.wind.medianValue),
                    precipitation = FieldValue(accumulator.precipitation.medianValue + itemDataHistory.precipitation.medianValue)
                )
            }.blockingLast()
    }

}
