package com.shashank.maytheforcebewith_shashankyadav.globals

import com.shashank.maytheforcebewith_shashankyadav.responses.Results1


class ApplicationConstant {
    companion object {
        const val APP_URL: String = "https://swapi.py4e.com/api/"
        const val APP_URL_FAV: String = "https://webhook.site/"
        var peopleArticles: ArrayList<Results1> = ArrayList()
        var planetArticles: ArrayList<Results1>? = ArrayList()
        var filmArticles: ArrayList<Results1>? = ArrayList()
        var speciesArticles: ArrayList<Results1>? = ArrayList()
        var vehiclesArticles: ArrayList<Results1>? = ArrayList()
        var starshipsArticles: ArrayList<Results1>? = ArrayList()
        var planetHashMap: HashMap<Int, String>? = HashMap()
        var filmsHashMap: HashMap<Int, String>? = HashMap()
        var speciesHashMap: HashMap<Int, String>? = HashMap()
        var vehiclesHashMap: HashMap<Int, String>? = HashMap()
        var starshipsHashMap: HashMap<Int, String>? = HashMap()
        var request: String? = ""
        var PEOPLE_REQUEST: String? = "People"
        var PLANETS_REQUEST: String? = "Planets"
        var SPECIES_REQUEST: String? = "Species"
        var FILMS_REQUEST: String? = "Films"
        var VEHICLES_REQUEST: String? = "Vehicles"
        var STARSHIPS_REQUEST: String? = "Starships"

        fun initData(){
            planetArticles = ArrayList()
            peopleArticles = ArrayList()
            speciesArticles = ArrayList()
            filmArticles = ArrayList()
            vehiclesArticles = ArrayList()
            starshipsArticles = ArrayList()
            planetHashMap = HashMap()
            filmsHashMap = HashMap()
            speciesHashMap = HashMap()
            vehiclesHashMap = HashMap()
            starshipsHashMap = HashMap()
        }
    }
}
