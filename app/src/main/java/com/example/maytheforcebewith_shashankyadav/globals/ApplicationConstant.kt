package com.example.maytheforcebewith_shashankyadav.globals

import com.example.maytheforcebewith_shashankyadav.responses.Results1


class ApplicationConstant {
    companion object{
//        const val APP_URL: String = "https://swapi.dev/api/"
        const val APP_URL: String = "https://swapi.py4e.com/api/"
        const val APP_URL_FAV: String = "https://webhook.site/"
        var peopleArticles: ArrayList<Results1>? = null
        var planetArticles: ArrayList<Results1>? =  null
        var filmArticles: ArrayList<Results1>? =  null
        var speciesArticles: ArrayList<Results1>? =  null
        var vehiclesArticles: ArrayList<Results1>? =  null
        var starshipsArticles: ArrayList<Results1>? =  null
        var planetHashMap: HashMap<Int, String>? = null
        var filmsHashMap: HashMap<Int, String>? = null
        var speciesHashMap: HashMap<Int, String>? = null
        var vehiclesHashMap: HashMap<Int, String>? = null
        var starshipsHashMap: HashMap<Int, String>? = null
        var request: String? = ""
        var PEOPLE_REQUEST: String? = "People"
        var PLANETS_REQUEST: String? = "Planets"
        var SPECIES_REQUEST: String? = "Species"
        var FILMS_REQUEST: String? = "Films"
        var VEHICLES_REQUEST: String? = "Vehicles"
        var STARSHIPS_REQUEST: String? = "Starships"
    }
}
