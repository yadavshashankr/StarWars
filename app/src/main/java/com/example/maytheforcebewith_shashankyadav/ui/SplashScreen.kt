package com.example.maytheforcebewith_shashankyadav.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.maytheforcebewith_shashankyadav.R
import com.example.maytheforcebewith_shashankyadav.databinding.ActivitySplashScreenBinding
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant
import com.example.maytheforcebewith_shashankyadav.presenters.IPresenter
import com.example.maytheforcebewith_shashankyadav.presenters.MainPresenter
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData
import com.example.maytheforcebewith_shashankyadav.responses.Percentage
import com.example.maytheforcebewith_shashankyadav.responses.Results1
import com.example.maytheforcebewith_shashankyadav.utils.NetworkCheck.Companion.verifyAvailableNetwork


/* SplashScreen acts as a splash activity and also displays data received by simultaneous api calls
using coroutines*/
class SplashScreen : AppCompatActivity(), IPresenter {

    private var planets: Boolean = false
    private var films: Boolean = false
    private var species: Boolean = false
    private var vehicles: Boolean = false
    private var starships: Boolean = false
    private var people: Boolean = false
    private var peopleArticles1: ArrayList<Results1>? = ArrayList()
    private lateinit var mMainPresenter: MainPresenter
    lateinit var bindingSplash: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // creating data binding object
        bindingSplash = DataBindingUtil.setContentView<ActivitySplashScreenBinding>(
            this,
            R.layout.activity_splash_screen
        )
        mMainPresenter = MainPresenter(this)

        bindingSplash.lifecycleOwner = this

        bindingSplash.ivRefresh.setOnClickListener {
            restart()
        }

        starWarsApiCall()
    }

    private fun restart() {
        val intentSplashScreen = intent
        finish()
        startActivity(intentSplashScreen)
    }

    private fun starWarsApiCall(){
        if (verifyAvailableNetwork(this)) {
            mMainPresenter.initApis()
        } else {
            Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSuccess(articles: ArrayList<Results1>, request: String?) {
        // receiving responses and checking if all are received
        when (request) {
            ApplicationConstant.PEOPLE_REQUEST -> {
                this.peopleArticles1 = articles
                people = true
            }
            ApplicationConstant.PLANETS_REQUEST -> {
                planets = ProcessApiData.processPlanets()
            }
            ApplicationConstant.FILMS_REQUEST -> {
                films = ProcessApiData.processFilms()
            }
            ApplicationConstant.SPECIES_REQUEST -> {
                species = ProcessApiData.processSpecies()
            }
            ApplicationConstant.VEHICLES_REQUEST -> {
                vehicles = ProcessApiData.processVehicles()
            }
            ApplicationConstant.STARSHIPS_REQUEST -> {
                starships = ProcessApiData.processStarships()
            }
        }
        dataLoadComplete()
    }

    private fun dataLoadComplete() {
        if (people && planets && films && species && vehicles && starships) {
            // if all responses are received then moving to Main Activity
            runOnUiThread {
                bindingSplash.ivRefresh.isVisible = true
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
            }
        }
    }

    override fun onApiPercentage(percent: Percentage?) {
        // load determinate progress bars with percentage data received from simultaneous api calls
        bindingSplash.percent = percent
    }

    override fun onFailure() {
        runOnUiThread {
            bindingSplash.ivRefresh.isVisible = true
            Toast.makeText(this, "Error Retrieving Data from Server !", Toast.LENGTH_SHORT).show()
        }
    }

    override fun isApiLoading(isLoading: Boolean?) {}
    override fun onSuccessCode(code: Int?) {}

}