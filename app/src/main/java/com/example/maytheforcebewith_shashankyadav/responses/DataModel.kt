package com.example.maytheforcebewith_shashankyadav.responses

import java.io.Serializable


data class Percentage(var percentPeople: Int?, var percentPlanet: Int?, var percentFilms : Int?,
                      var percentSpecies: Int?, var percentVehicles: Int?, var percentStarships: Int?)
data class FavResponse1(val responseCode: Int? ,  val responseBody: String?)

data class FavData1(var name: String?, var fav: Boolean?)

data class People1(var count: Int?, var next: String?, var previous: String?, var results: ArrayList<Results1>?)
data class Planets1(var count: Int?, var next: String?, var previous: String?, var results: ArrayList<Results1>?)
data class Films1(var count: Int?, var next: String?, var previous: String?, var results: ArrayList<Results1>?)
data class Species1(var count: Int?, var next: String?, var previous: String?, var results: ArrayList<Results1>?)
data class Vehicles1(var count: Int?, var next: String?, var previous: String?, var results: ArrayList<Results1>?)
data class Starships1(var count: Int?, var next: String?, var previous: String?, var results: ArrayList<Results1>?)

data class Results1(var name: String? = "N/A", var created: String?= "N/A",
                    var edited: String?= "N/A", var url: String?= "N/A",
                    var fav: Boolean = false, var height: String?= "N/A",
                    var hair_color: String?= "N/A", var skin_color: String?= "N/A",
                    var eye_color: String?= "N/A", var birth_year: String?= "N/A",
                    var gender: String?= "N/A", var homeworld: String?= "N/A",
                    var films: ArrayList<String>?, var species: ArrayList<String>?,
                    var vehicles: ArrayList<String>?, var starships: ArrayList<String>?,
                    var orbital_period: String?= "N/A", var diameter: String?= "N/A",
                    var climate: String?= "N/A", var gravity: String?= "N/A",
                    var terrain: String?= "N/A", var surface_water: String?= "N/A",
                    var population: String?= "N/A", var title: String?= "N/A",
                    var episode_id: Int?, var opening_crawl: String?= "N/A",
                    var director: String?= "N/A", var producer: String?= "N/A",
                    var release_date: String?= "N/A", var classification: String?= "N/A",
                    var designation: String?= "N/A", var average_height: String?= "N/A",
                    var skin_colors: String?= "N/A", var hair_colors: String?= "N/A",
                    var eye_colors: String?= "N/A", var average_lifespan: String?= "N/A",
                    var language: String?= "N/A", var model: String?= "N/A",
                    var manufacturer: String?, var cost_in_credits: String?,
                    var length: String?= "N/A", var max_atmosphering_speed: String?= "N/A",
                    var crew: String?= "N/A", var passengers: String?= "N/A",
                    var cargo_capacity: String?= "N/A", var consumables: String?= "N/A",
                    var vehicle_class: String?= "N/A", var hyperdrive_rating: String?= "N/A",
                    var MGLT: String?= "N/A", var starship_class: String?= "N/A",
                    var rotation_period: String?= "N/A", var mass: String?= "N/A",
                    var speciesValue: String?= "N/A", var filmsValues: String?= "N/A",
                    var vehiclesValue: String?= "N/A", var starshipsValue: String?= "N/A",
                    var homeWorldValue: String?= "N/A") : Serializable