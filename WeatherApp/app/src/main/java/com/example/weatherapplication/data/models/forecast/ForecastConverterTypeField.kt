package com.example.weatherapplication.data.models.forecast

import androidx.room.TypeConverter
import com.example.weatherapplication.data.models.forecast.clouds.Clouds
import com.example.weatherapplication.data.models.forecast.main.Main
import com.example.weatherapplication.data.models.forecast.sys.Sys
import com.example.weatherapplication.data.models.forecast.weather.Weather
import com.example.weatherapplication.data.models.forecast.wind.Wind
import com.example.weatherapplication.utils.Converter

object ForecastConverterTypeField {

    @TypeConverter
    fun cloudsToString(mObject: Clouds): String {
        return Converter.objectToString(mObject)
    }

    @TypeConverter
    fun stringToClouds(string: String): Clouds {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun mainToString(mObject: Main): String {
        return Converter.objectToString(mObject)
    }

    @TypeConverter
    fun stringToMain(string: String): Main {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun sysToString(mObject: Sys): String {
        return Converter.objectToString(mObject)
    }

    @TypeConverter
    fun stringToSys(string: String): Sys {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun weatherToString(mObject: List<Weather>): String {
        return Converter.objectToString(mObject[0])
    }

    @TypeConverter
    fun stringToWeather(string: String): List<Weather> {
        return listOf(Converter.stringToObject(string))
    }

    @TypeConverter
    fun windToString(mObject: Wind): String {
        return Converter.objectToString(mObject)
    }

    @TypeConverter
    fun stringToWind(string: String): Wind {
        return Converter.stringToObject(string)
    }
}