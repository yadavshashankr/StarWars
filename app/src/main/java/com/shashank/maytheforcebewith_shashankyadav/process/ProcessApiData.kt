package com.shashank.maytheforcebewith_shashankyadav.process

import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.filmArticles
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.filmsHashMap
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetArticles
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetHashMap
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.speciesArticles
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.speciesHashMap
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.starshipsArticles
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.starshipsHashMap
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.vehiclesArticles
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.vehiclesHashMap
import com.shashank.maytheforcebewith_shashankyadav.responses.Results1

class ProcessApiData {

    companion object {

        /* save the position of planets, films, species, vehicles, starships along with their names in respective
        HashMaps
         */
        fun processPlanets(): Boolean {
            for (planet in planetArticles as ArrayList<Results1>) {

                planetHashMap?.put(processPosition(planet), planet.name as String)

            }
            return true
        }

        fun processFilms(): Boolean {
            for (film in filmArticles as ArrayList<Results1>) {

                filmsHashMap?.put(processPosition(film), film.title as String)

            }

            return true
        }

        fun processSpecies(): Boolean {
            for (species in speciesArticles as ArrayList<Results1>) {

                speciesHashMap?.put(processPosition(species), species.name as String)

            }

            return true
        }

        fun processVehicles(): Boolean {
            for (vehicle in vehiclesArticles as ArrayList<Results1>) {

                vehiclesHashMap?.put(processPosition(vehicle), vehicle.name as String)

            }

            return true
        }

        fun processStarships(): Boolean {
            for (starship in starshipsArticles as ArrayList<Results1>) {

                starshipsHashMap?.put(processPosition(starship), starship.name as String)

            }

            return true
        }

        // process position for storing names of species, starships, vehicles, planets, films belonging to the character
        fun processPosition(result: Results1): Int {
            var pos: String? = ""
            try {
                pos = result.url?.replace(Regex("[\\D.]"), "")

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Integer.parseInt(pos as String)
        }

        // returning string of names of starships, species, planets, films, vehicles in appended form
        fun processFilmsValues(films: ArrayList<String>?): String {
            var result: String = ""
            try {
                var count: Int = 1
                for (i in films as ArrayList<String>) {
                    val position1 = i.replace(
                        Regex("[\\D.]"), ""
                    ).toInt()

                    if (count < 2) {
                        result += "" + count + ". " + filmsHashMap?.get(position1)
                    } else {
                        result += "\n" + count + ". " + filmsHashMap?.get(position1)
                    }
                    count++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


            return result
        }

        fun processSpeciesValues(species: ArrayList<String>?): String {
            var result: String = ""
            try {
                var count: Int = 1
                for (i in species as ArrayList<String>) {
                    val position1 = i.replace(
                        Regex("[\\D.]"), ""
                    ).toInt()

                    if (count < 2) {
                        result += speciesHashMap?.get(position1)
                    } else {
                        result += speciesHashMap?.get(position1)
                    }
                    count++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        fun processVehiclesValues(vehicles: ArrayList<String>?): String {
            var result: String = ""
            try {
                var count: Int = 1
                for (i in vehicles as ArrayList<String>) {
                    val position1 = i.replace(
                        Regex("[\\D.]"), ""
                    ).toInt()

                    if (count < 2) {
                        result += "" + count + ". " + vehiclesHashMap?.get(position1)
                    } else {
                        result += "\n" + count + ". " + vehiclesHashMap?.get(position1)
                    }
                    count++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        fun processStarshipsValues(starships: ArrayList<String>?): String {
            var result: String = ""
            try {
                var count: Int = 1
                for (i in starships as ArrayList<String>) {
                    val position1 = i.replace(
                        Regex("[\\D.]"), ""
                    ).toInt()

                    if (count < 2) {
                        result += "" + count + ". " + starshipsHashMap?.get(position1)
                    } else {
                        result += "\n" + count + ". " + starshipsHashMap?.get(position1)
                    }
                    count++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }
    }
}