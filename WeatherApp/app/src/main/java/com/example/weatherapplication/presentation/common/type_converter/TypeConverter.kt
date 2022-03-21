package com.example.weatherapplication.presentation.common.type_converter

import androidx.room.TypeConverter
import com.example.weatherapplication.data.database.models.forecast.coord.Coordination
import com.example.weatherapplication.data.database.models.forecast.main.Main
import com.example.weatherapplication.data.database.models.forecast.sys.Sys
import com.example.weatherapplication.data.database.models.forecast.weather.Weather
import com.example.weatherapplication.data.database.models.forecast.wind.Wind

/**
 * @author Parshin Sergey
 * Объект производящий конвертацию объектов в строку Json и обратно.
 * Конвертация производяится для хронения объектов в базах данных.
 * */

object TypeConverter {

    @TypeConverter
    fun coordinationToString(coordination: Coordination): String {
        return PatternTypeConverter.objectToString(coordination)
    }

    @TypeConverter
    fun stringToCoordination(string: String): Coordination {
        return PatternTypeConverter.stringToObject(string)
    }

    @TypeConverter
    fun mainToString(main: Main): String {
        return PatternTypeConverter.objectToString(main)
    }

    @TypeConverter
    fun stringToMain(string: String): Main {
        return PatternTypeConverter.stringToObject(string)
    }

    @TypeConverter
    fun sysToString(sys: Sys): String {
        return PatternTypeConverter.objectToString(sys)
    }

    @TypeConverter
    fun stringToSys(string: String): Sys {
        return PatternTypeConverter.stringToObject(string)
    }

    @TypeConverter
    fun weatherToString(weather: List<Weather>): String {
        return PatternTypeConverter.objectToString(weather[0])
    }

    @TypeConverter
    fun stringToWeather(string: String): List<Weather> {
        return listOf(PatternTypeConverter.stringToObject(string))
    }

    @TypeConverter
    fun windToString(wind: Wind): String {
        return PatternTypeConverter.objectToString(wind)
    }

    @TypeConverter
    fun stringToWind(string: String): Wind {
        return PatternTypeConverter.stringToObject(string)
    }
}