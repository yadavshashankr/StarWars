package com.example.maytheforcebewith_shashankyadav.presenters

import com.example.maytheforcebewith_shashankyadav.responses.Percentage
import com.example.maytheforcebewith_shashankyadav.responses.Results1


interface IPresenter {

    // gives api success code
    fun onSuccessCode(code: Int?)

    // gives list of people, plants, species, films, vehicles and starships
    fun onSuccess(articles: ArrayList<Results1>, request: String?)

    // triggers when data is not received from server
    fun onFailure()

    // provides percentage completion of all apis
    fun onApiPercentage(percent: Percentage?)

    // provides state whether api call is in progress
    fun isApiLoading(isLoading: Boolean?)

}