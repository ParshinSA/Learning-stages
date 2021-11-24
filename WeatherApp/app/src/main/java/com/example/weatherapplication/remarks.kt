package com.example.weatherapplication

//Комментарии по коду приложения:

//activity_app.xml - в данном случае ConstraintLayout избыточен, так как вложенный view всего один. Можно использовать более легковесный FramaeLayout
/**
 * Понял, принял, с этой точки данный момент не изучал.
 * исправил
 */

//activity_app.xml - если кладешь что-то в ConstraintLayout, не забывай указывать constraints для вложенного view
/**
 * navhost fragment добавлялся автоматически, он match_parent, может убежать за пределы экрана??
 *  после замены FramaeLayout на ConstraintLayout отпала необходимость в данном исправлении
 *  но инфу принял
 */

//ListOfCityFragment#getForecast - загрузку данных лучше осуществлять только после того, как view создана, в методе onViewCreated, а не в onCreateView
/**
 * исправил
 */

//ListOfCityFragment#checkInternet - здесь лучше прокинуть событие во viewModel и уже оттуда показывать диалог с ошибкой
/**
 * исправил
 */

//ListOfCityAdapterRV.WeatherForecastHolder#bind - ".load("https://openweathermap.org/img/wn/${item.weather[0].sectionURL.toString()}@2x.png")" - вынести путь в строковый ресурс
/**
 * исправил
 */

//ListOfCityFragment#onDestroy - все действия в этом методе нужно выполнять перед вызовом super.onDestroy()
/**
 * исправил
 */

//ForecastViewModel#getForecastListInInternetOrDatabase - "var currentList = listOf<Forecast>()", почему не использовал "val currentList = mutableListOf<Forecast>()"?
/**
 * не знаю, где-то видел))) при переделки на coroutines + retrofit переменная самоликвидировась)))
 */

//SaveForecast - лучше переименовать, сейчас это больше похоже на название функции(действие), а должно отображать сущность, которую ты сохраняешь
/**
 * ResponseForecast
 */

//SaveForecast.kt:15 - "forecastJson" - а какой смысл в БД, также можем и SharedPreferences сохранять JSON по ID города. Давай ка переделаем на таблицу с полями из com.example.weatherapplication.data.models.forecast.Forecast
/**
 * исправил
 */

//CityInfoFragment#bindViewItems - также, сетить информацию во View нужно в методе onViewCreated()
/**
 * исправил
 */

//CityInfoFragment - view не должна себя сама сетить, данные должны идти из ViewModel. Нужно добавить для этого экрана.
/**
 * исправил
 */

//CityInfoFragment#setIcon - .load("https://openweathermap.org/img/wn/${args.weather[0].sectionURL.toString()}@2x.png") - вынести путь в строковый ресурс
/**
 * исправил
 */

//CityInfoFragment#args - стоит переименоват в forecastInfo например
/**
 * исправил
 */