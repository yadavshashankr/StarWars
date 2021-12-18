package com.example.maytheforcebewith_shashankyadav.presenters


import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.filmArticles
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.peopleArticles
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetArticles
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.speciesArticles
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.starshipsArticles
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.vehiclesArticles
import com.example.maytheforcebewith_shashankyadav.network.ApiCall
import com.example.maytheforcebewith_shashankyadav.network.FavApiCall
import com.example.maytheforcebewith_shashankyadav.responses.*
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext


class MainPresenter: CoroutineScope{


    private var planetsTask: Response<Planets1>? = null
    private var peopleTask: Response<People1>? = null
    private var filmsTask: Response<Films1>? = null
    private var speciesTask: Response<Species1>? = null
    private var vehiclesTask: Response<Vehicles1>? = null
    private var starshipsTask: Response<Starships1>? = null
    private lateinit  var getPlanetsTask: Deferred<Response<Planets1>>
    private lateinit var getPeopleTask: Deferred<Response<People1>>
    private var getFilmsTask: Deferred<Response<Films1>>? = null
    private var getSpeciesTask: Deferred<Response<Species1>>? = null
    private var getVehiclesTask: Deferred<Response<Vehicles1>>? = null
    private var getStarshipsTask: Deferred<Response<Starships1>>? = null
    var PLANET_START_PAGE :Int = 1
    var PLANET_END_PAGE: Int = 0
    var planet_firstStart: Boolean? = null
    var people_firstStart: Boolean? = null
    var FILMS_START_PAGE :Int = 1
    var FILMS_END_PAGE: Int = 0
    var films_firstStart: Boolean? = null
    var SPECIES_START_PAGE :Int = 1
    var SPECIES_END_PAGE: Int = 0
    var species_firstStart: Boolean? = null
    var VEHICLES_START_PAGE :Int = 1
    var VEHICLES_END_PAGE: Int = 0
    var vehicles_firstStart: Boolean? = null
    var STARSHIPS_START_PAGE :Int = 1
    var STARSHIPS_END_PAGE: Int = 0
    var starships_firstStart: Boolean? = null
    var iPresenter: IPresenter? = null
    var planetPercent: Int? = null
    var peoplePercent: Int? = null
    var filmsPercent: Int? = null
    var speciesPercent: Int? = null
    var vehiclesPercent: Int? = null
    var starshipsPercent: Int? = null

    var percent: Percentage? = null

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    constructor(presenter : IPresenter){
        if (iPresenter == null){
            iPresenter  = presenter
        }

    }

     fun getPlanetsApis(page: Int): Percentage {



             jobPlanets = CoroutineScope(Dispatchers.IO).launch {


                     supervisorScope {
                         val planetsService = ApiCall.create()
                         getPlanetsTask = async(handler) {
                             planetsService.getPlanets(page)
                         }

                         try {
                             planetsTask = getPlanetsTask.await()

                             withContext(Dispatchers.Main + jobPlanets as Job) {
                                 try {
                                     if (planetsTask?.isSuccessful as Boolean) {

                                         // calculate First Page and Last Page on basis of total item counts and 10 items per page
                                         if (planetsTask?.body()?.count as Int % 10 != 0 && planet_firstStart == null) {
                                             // increase End Page by 1 if remainder of items is found
                                             PLANET_END_PAGE =
                                                 (planetsTask?.body()?.count as Int / 10) + 1
                                             planet_firstStart = true
                                             planetArticles = ArrayList()
                                         } else if (planetsTask?.body()?.count as Int % 10 == 0 && planet_firstStart == null) {
                                             PLANET_END_PAGE =
                                                 (planetsTask?.body()?.count as Int / 10)
                                             planet_firstStart = true
                                         }

                                         // add received results in the Global arraylist
                                         for (i in planetsTask?.body()?.results as ArrayList<Results1>) {
                                             planetArticles?.add(i)
                                         }

                                         // calculating percentage of api completion on the basis of END Page
                                         planetPercent = (page / PLANET_END_PAGE) * 100

                                         // create object and send it to the requiring classes
                                         percent = Percentage(
                                             peoplePercent,
                                             planetPercent,
                                             filmsPercent,
                                             speciesPercent,
                                             vehiclesPercent,
                                             starshipsPercent
                                         )
                                         percent.let { iPresenter?.onApiPercentage(it) }


                                         /* if further pages need to be traversed then set firstStart value as true
                                     and if current page reaches last page then change the value of firstStart to false
                                      */
                                         if (planetsTask?.body()?.count as Int % 10 != 0) {
                                             if (PLANET_START_PAGE == PLANET_END_PAGE) {
                                                 planet_firstStart = false
                                             }
                                         } else {
                                             if (PLANET_START_PAGE == PLANET_END_PAGE) {
                                                 planet_firstStart = false
                                             }
                                         }

                                         // increment page number after every successful api call
                                         PLANET_START_PAGE++

                                         // if pages are still being visited, cancel the coroutine job and restart it
                                         if (PLANET_START_PAGE <= PLANET_END_PAGE) {
                                             if (jobPlanets?.isActive as Boolean) {
                                                 jobPlanets?.cancel()
                                                 jobPlanets = null
                                                 planetsTask = null
                                                 try {
                                                     getPlanetsApis(PLANET_START_PAGE)
                                                 } catch (e: Exception) {
                                                     e.printStackTrace()
                                                 }


                                             }
                                         }
                                         /* if firstStart is false that means all the pages has been traversed time to finally
                                     quit making api calls and cancel the coroutine job
                                      */
                                         if (planet_firstStart == false) {
                                             jobPlanets?.cancel()
                                             jobPlanets = null
                                             planetsTask = null
                                             planet_firstStart = null
                                             PLANET_START_PAGE = 1
                                             planetArticles?.let {
                                                 iPresenter?.onSuccess(
                                                     it,
                                                     "Planets"
                                                 )
                                             }

                                         }
                                     } else if (!planetsTask?.isSuccessful!!) {
                                         iPresenter?.onFailure()
                                     }
                                 } catch (e: Exception) {
                                     e.printStackTrace()
                                 }

                             }
                         } catch (e: IOException) {
                             e.printStackTrace()
                             iPresenter?.onFailure()
                         }

                         // Starting coroutine with coroutine's scope and add job to it

                     }

             }


         return Percentage(peoplePercent,
             planetPercent, filmsPercent, speciesPercent, vehiclesPercent, starshipsPercent)
        }

     fun getPeopleApis(page: Int) {

         iPresenter?.isApiLoading(true)


             jobPeople = CoroutineScope(Dispatchers.IO).launch {


                     supervisorScope {
                         val peopleService = ApiCall.create()
                         getPeopleTask = async(handler) {
                             peopleService.getPeople(page)
                         }
                         try {
                             peopleTask = getPeopleTask.await()

                             withContext(Dispatchers.Main + jobPeople as Job) {
                                 try {
                                     if (peopleTask?.isSuccessful as Boolean) {

                                         for (i in peopleTask?.body()?.results as ArrayList<Results1>) {
                                             peopleArticles?.add(i)
                                         }

                                         peoplePercent = 100
                                         percent = Percentage(
                                             peoplePercent,
                                             planetPercent,
                                             filmsPercent,
                                             speciesPercent,
                                             vehiclesPercent,
                                             starshipsPercent
                                         )
                                         percent.let { iPresenter?.onApiPercentage(it) }

                                         jobPeople?.cancel()
                                         jobPeople = null
                                         peopleTask = null
                                         iPresenter?.onSuccess(
                                             peopleArticles as ArrayList<Results1>,
                                             "People"
                                         )
                                         iPresenter?.isApiLoading(false)


                                     } else {
                                         iPresenter?.isApiLoading(false)
                                         iPresenter?.onFailure()
                                     }
                                 } catch (e: Exception) {
                                     e.printStackTrace()
                                 }

                             }
                         } catch (e: IOException) {
                             e.printStackTrace()
                             iPresenter?.onFailure()
                         }

                     }

             }

     }



     fun getFilmsApis(page: Int) {

            jobFilms = CoroutineScope(Dispatchers.IO).launch {



                    supervisorScope {
                        val filmsService = ApiCall.create()
                        getFilmsTask = async(handler) {
                            filmsService.getFilms(page)
                        }
                        try {

                            filmsTask = getFilmsTask?.await()

                            withContext(Dispatchers.Main + jobFilms as Job) {
                                try {
                                    if (filmsTask?.isSuccessful as Boolean) {

                                        if (filmsTask?.body()?.count as Int % 10 != 0 && films_firstStart == null) {
                                            FILMS_END_PAGE =
                                                (filmsTask?.body()?.count as Int / 10) + 1
                                            films_firstStart = true
                                        } else if (filmsTask?.body()?.count as Int % 10 == 0 && films_firstStart == null) {
                                            FILMS_END_PAGE = (filmsTask?.body()?.count as Int / 10)
                                            films_firstStart = true
                                        }

                                        for (i in filmsTask?.body()?.results as ArrayList<Results1>) {
                                            filmArticles?.add(i)
                                        }

                                        filmsPercent = (page / FILMS_END_PAGE) * 100

                                        percent = Percentage(
                                            peoplePercent,
                                            planetPercent,
                                            filmsPercent,
                                            speciesPercent,
                                            vehiclesPercent,
                                            starshipsPercent
                                        )
                                        percent.let { iPresenter?.onApiPercentage(it) }

                                        if (filmsTask?.body()?.count as Int % 10 != 0) {
                                            if (FILMS_START_PAGE == FILMS_END_PAGE) {
                                                films_firstStart = false
                                            }
                                        } else {
                                            if (FILMS_START_PAGE == FILMS_END_PAGE) {
                                                films_firstStart = false
                                            }
                                        }
                                        FILMS_START_PAGE++
                                        if (FILMS_START_PAGE <= FILMS_END_PAGE) {
                                            if (jobFilms?.isActive as Boolean) {
                                                jobFilms?.cancel()
                                                jobFilms = null
                                                filmsTask = null
                                                try {
                                                    getFilmsApis(FILMS_START_PAGE)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }


                                            }
                                        }
                                        if (films_firstStart == false) {
                                            films_firstStart = null
                                            jobFilms?.cancel()
                                            jobFilms = null
                                            filmsTask = null
                                            FILMS_START_PAGE = 1
                                            filmArticles?.let { iPresenter?.onSuccess(it, "Films") }

                                        }
                                    } else if (!filmsTask?.isSuccessful!!) {
                                        iPresenter?.onFailure()
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            }


                        } catch (e: IOException) {
                            e.printStackTrace()
                            iPresenter?.onFailure()
                        }

                    }


            }



    }

     fun getSpeciesApis(page: Int) {


            jobSpecies = CoroutineScope(Dispatchers.IO).launch {


                    supervisorScope {
                        val speciesServices = ApiCall.create()
                        getSpeciesTask = async(handler) {
                            speciesServices.getSpecies(page)
                        }
                        try {
                            speciesTask = getSpeciesTask?.await()


                            withContext(Dispatchers.Main + jobSpecies as Job) {
                                try {
                                    if (speciesTask?.isSuccessful as Boolean) {

                                        if (speciesTask?.body()?.count as Int % 10 != 0 && species_firstStart == null) {
                                            SPECIES_END_PAGE =
                                                (speciesTask?.body()?.count as Int / 10) + 1
                                            species_firstStart = true
                                        } else if (speciesTask?.body()?.count as Int % 10 == 0 && species_firstStart == null) {
                                            SPECIES_END_PAGE =
                                                (speciesTask?.body()?.count as Int / 10)
                                            species_firstStart = true
                                        }

                                        for (i in speciesTask?.body()?.results as ArrayList<Results1>) {
                                            speciesArticles?.add(i)
                                        }

                                        speciesPercent = (page / SPECIES_END_PAGE) * 100

                                        percent = Percentage(
                                            peoplePercent,
                                            planetPercent,
                                            filmsPercent,
                                            speciesPercent,
                                            vehiclesPercent,
                                            starshipsPercent
                                        )
                                        percent.let { iPresenter?.onApiPercentage(it) }

                                        if (speciesTask?.body()?.count as Int % 10 != 0) {
                                            if (SPECIES_START_PAGE == SPECIES_END_PAGE) {
                                                species_firstStart = false
                                            }
                                        } else {
                                            if (SPECIES_START_PAGE == SPECIES_END_PAGE) {
                                                species_firstStart = false
                                            }
                                        }
                                        SPECIES_START_PAGE++
                                        if (SPECIES_START_PAGE <= SPECIES_END_PAGE) {
                                            if (jobSpecies?.isActive as Boolean) {
                                                jobSpecies?.cancel()
                                                jobSpecies = null
                                                speciesTask = null
                                                try {
                                                    getSpeciesApis(SPECIES_START_PAGE)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }
                                            }
                                        }
                                        if (species_firstStart == false) {
                                            species_firstStart = null
                                            jobSpecies?.cancel()
                                            jobSpecies = null
                                            speciesTask = null
                                            SPECIES_START_PAGE = 1
                                            speciesArticles?.let {
                                                iPresenter?.onSuccess(
                                                    it,
                                                    "Species"
                                                )
                                            }

                                        }
                                    } else if (!speciesTask?.isSuccessful!!) {
                                        iPresenter?.onFailure()
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            iPresenter?.onFailure()
                        }
                    }


            }


    }

     fun getVehiclesApis(page: Int) {


            jobVehicles = CoroutineScope(Dispatchers.IO).launch {


                    supervisorScope {

                        val vehiclesService = ApiCall.create()
                        getVehiclesTask = async(handler) {
                            vehiclesService.getVehicles(page)
                        }
                        try {
                            vehiclesTask = getVehiclesTask?.await()
                            withContext(Dispatchers.Main + jobVehicles as Job) {
                                try {
                                    if (vehiclesTask?.isSuccessful as Boolean) {

                                        if (vehiclesTask?.body()?.count as Int % 10 != 0 && people_firstStart == null) {
                                            VEHICLES_END_PAGE =
                                                (vehiclesTask?.body()?.count as Int / 10) + 1
                                            vehicles_firstStart = true
                                        } else if (vehiclesTask?.body()?.count as Int % 10 == 0 && vehicles_firstStart == null) {
                                            VEHICLES_END_PAGE =
                                                (vehiclesTask?.body()?.count as Int / 10)
                                            vehicles_firstStart = true
                                        }

                                        for (i in vehiclesTask?.body()?.results as ArrayList<Results1>) {
                                            vehiclesArticles?.add(i)
                                        }


                                        vehiclesPercent = (page / VEHICLES_END_PAGE) * 100

                                        percent = Percentage(
                                            peoplePercent,
                                            planetPercent,
                                            filmsPercent,
                                            speciesPercent,
                                            vehiclesPercent,
                                            starshipsPercent
                                        )
                                        percent.let { iPresenter?.onApiPercentage(it) }

                                        if (vehiclesTask?.body()?.count as Int % 10 != 0) {
                                            if (VEHICLES_START_PAGE == VEHICLES_END_PAGE) {
                                                vehicles_firstStart = false
                                            }
                                        } else {
                                            if (VEHICLES_START_PAGE == VEHICLES_END_PAGE) {
                                                vehicles_firstStart = false
                                            }
                                        }
                                        VEHICLES_START_PAGE++
                                        if (VEHICLES_START_PAGE <= VEHICLES_END_PAGE) {
                                            if (jobVehicles?.isActive as Boolean) {
                                                jobVehicles?.cancel()
                                                jobVehicles = null
                                                vehiclesTask = null
                                                try {
                                                    getVehiclesApis(VEHICLES_START_PAGE)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }


                                            }
                                        }

                                        if (vehicles_firstStart == false) {
                                            vehicles_firstStart = null
                                            jobVehicles?.cancel()
                                            jobVehicles = null
                                            vehiclesTask = null
                                            VEHICLES_START_PAGE = 1
                                            vehiclesArticles?.let {
                                                iPresenter?.onSuccess(
                                                    it,
                                                    "Vehicles"
                                                )
                                            }

                                        }
                                    } else if (!vehiclesTask?.isSuccessful!!) {
                                        iPresenter?.onFailure()
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            iPresenter?.onFailure()
                        }


                    }


            }




    }

     fun getStarshipsApis(page: Int) {


             jobStarships = CoroutineScope(Dispatchers.IO).launch {



        supervisorScope {
            val starshipsService = ApiCall.create()
            getStarshipsTask = async(handler) {
                starshipsService.getStarships(page)
            }
            try {
                starshipsTask = getStarshipsTask?.await()

                withContext(Dispatchers.Main + jobStarships as Job) {
                    try {
                        if (starshipsTask?.isSuccessful as Boolean) {

                            if (starshipsTask?.body()?.count as Int % 10 != 0 && starships_firstStart == null) {
                                STARSHIPS_END_PAGE = (starshipsTask?.body()?.count as Int / 10) + 1
                                starships_firstStart = true
                            } else if (starshipsTask?.body()?.count as Int % 10 == 0 && starships_firstStart == null) {
                                STARSHIPS_END_PAGE = (starshipsTask?.body()?.count as Int / 10)
                                starships_firstStart = true
                            }

                            for (i in starshipsTask?.body()?.results as ArrayList<Results1>) {
                                starshipsArticles?.add(i)
                            }

                            starshipsPercent = (page / STARSHIPS_END_PAGE) * 100

                            percent = Percentage(
                                peoplePercent,
                                planetPercent,
                                filmsPercent,
                                speciesPercent,
                                vehiclesPercent,
                                starshipsPercent
                            )
                            percent.let { iPresenter?.onApiPercentage(it) }

                            if (starshipsTask?.body()?.count as Int % 10 != 0) {
                                if (STARSHIPS_START_PAGE == STARSHIPS_END_PAGE) {
                                    starships_firstStart = false
                                }
                            } else {
                                if (STARSHIPS_START_PAGE == STARSHIPS_END_PAGE) {
                                    starships_firstStart = false
                                }
                            }
                            STARSHIPS_START_PAGE++
                            if (STARSHIPS_START_PAGE <= STARSHIPS_END_PAGE) {
                                if (jobStarships?.isActive as Boolean) {
                                    jobStarships?.cancel()
                                    jobStarships = null
                                    starshipsTask = null
                                    try {
                                        getStarshipsApis(STARSHIPS_START_PAGE)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                }
                            }

                            if (starships_firstStart == false) {
                                starships_firstStart = null
                                jobStarships?.cancel()
                                jobStarships = null
                                starshipsTask = null
                                STARSHIPS_START_PAGE = 1
                                starshipsArticles?.let { iPresenter?.onSuccess(it, "Starships") }

                            }
                        } else if (!starshipsTask?.isSuccessful!!) {
                            iPresenter?.onFailure()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            } catch (e: IOException) {
                e.printStackTrace()
                iPresenter?.onFailure()
            }

        }


             }




    }

    // post api call to send Fav data to webhook
    fun postFavApi(favData: FavData1){
        val apiService = FavApiCall.create()
        val call = apiService.store(favData)

        call.enqueue(object : Callback<FavResponse1?> {
            override fun onResponse(
                call: Call<FavResponse1?>,
                response: Response<FavResponse1?>
            ) {
                iPresenter?.onSuccessCode(response.code())
            }

            override fun onFailure(call: Call<FavResponse1?>, t: Throwable) {

                iPresenter?.onSuccessCode(t.hashCode())

            }
        })
    }
    private var jobPlanets: Job? = Job()
    private var jobPeople: Job? = Job()
    private var jobSpecies: Job? = Job()
    private var jobFilms: Job? = Job()
    private var jobVehicles: Job? = Job()
    private var jobStarships: Job? = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main



}