package com.example.weatherapplication.data.objects

import androidx.room.TypeConverter
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.coord.Coordination
import com.example.weatherapplication.data.models.forecast.main.Main
import com.example.weatherapplication.data.models.forecast.sys.Sys
import com.example.weatherapplication.data.models.forecast.weather.Weather
import com.example.weatherapplication.data.models.forecast.wind.Wind
import com.example.weatherapplication.utils.Converter

object AppTypeConverter {

    @TypeConverter
    fun cityToString(city: City): String {
        return Converter.objectToString(city)
    }

    @TypeConverter
    fun stringToCity(string: String): City {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun coordinationToString(coordination: Coordination): String {
        return Converter.objectToString(coordination)
    }

    @TypeConverter
    fun stringToCoordination(string: String): Coordination {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun mainToString(main: Main): String {
        return Converter.objectToString(main)
    }

    @TypeConverter
    fun stringToMain(string: String): Main {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun sysToString(sys: Sys): String {
        return Converter.objectToString(sys)
    }

    @TypeConverter
    fun stringToSys(string: String): Sys {
        return Converter.stringToObject(string)
    }

    @TypeConverter
    fun weatherToString(weather: List<Weather>): String {
        return Converter.objectToString(weather[0])
    }

    @TypeConverter
    fun stringToWeather(string: String): List<Weather> {
        return listOf(Converter.stringToObject(string))
    }

    @TypeConverter
    fun windToString(wind: Wind): String {
        return Converter.objectToString(wind)
    }

    @TypeConverter
    fun stringToWind(string: String): Wind {
        return Converter.stringToObject(string)
    }
}