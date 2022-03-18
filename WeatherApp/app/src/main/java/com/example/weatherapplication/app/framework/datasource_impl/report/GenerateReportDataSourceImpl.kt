package com.example.weatherapplication.app.framework.datasource_impl.report

import com.example.weatherapplication.app.common.toStringDoubleFormat
import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.data_source.report.GenerateReportDataSource
import com.example.weatherapplication.domain.ReportingPeriod

class GenerateReportDataSourceImpl: GenerateReportDataSource  {

    override fun generateReport(
        nameCity: String,
        reportingPeriod: ReportingPeriod,
        statisticData: WeatherStatistic
    ): String {
        return "Город: $nameCity\n" +
                "Средние значения за период \"${reportingPeriod.stringQuantity}\":\n" +
                "температура ${(statisticData.temperature.medianValue - 273.15).toStringDoubleFormat()} °C\n" +
                "влажность ${statisticData.humidity.medianValue.toStringDoubleFormat()} %\n" +
                "давление ${statisticData.pressure.medianValue.toStringDoubleFormat()} гПа\n" +
                "ветер ${statisticData.wind.medianValue.toStringDoubleFormat()} м/с\n" +
                "осадки ${statisticData.precipitation.medianValue.toStringDoubleFormat()} мм"
    }

}