package com.shashank.maytheforcebewith_shashankyadav.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.shashank.maytheforcebewith_shashankyadav.R
import com.shashank.maytheforcebewith_shashankyadav.databinding.LayoutCharacterDetailBinding
import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetHashMap
import com.shashank.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processFilmsValues
import com.shashank.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processSpeciesValues
import com.shashank.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processStarshipsValues
import com.shashank.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processVehiclesValues
import com.shashank.maytheforcebewith_shashankyadav.responses.Results1

/*CharacterDetail Activity displays the detailed data of selected character inside a new activity*/
class CharacterDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Receive data object of selected item from  MainActivity's list
        val characterDetail = intent.extras?.get("character") as Results1
        // create binding object
        val binding = DataBindingUtil.setContentView<LayoutCharacterDetailBinding>(this, R.layout.layout_character_detail)


        binding.lifecycleOwner = this
        binding.layoutItem.result = characterDetail
        binding.layoutItem.ivArrow.isVisible = false
        binding.layoutItem.rlExpand.visibility = View.VISIBLE


        // process arraylist of films, species, vehicles and starships to retrieve data for the selected character
        if (characterDetail.films?.size != 0) {
            val filmsValue = processFilmsValues(characterDetail.films)
            binding.layoutItem.result?.filmsValues = filmsValue
        }
        if (characterDetail.species?.size != 0) {
            val speciesValue = processSpeciesValues(characterDetail.species)
            binding.layoutItem.result?.speciesValue = speciesValue
        }
        if (characterDetail.vehicles?.size != 0) {
            val vehiclesValue = processVehiclesValues(characterDetail.vehicles)
            binding.layoutItem.result?.vehiclesValue = vehiclesValue
        }
        if (characterDetail.starships?.size != 0) {
            val starshipsValue = processStarshipsValues(characterDetail.starships)
            binding.layoutItem.result?.starshipsValue = starshipsValue
        }

        val pos = characterDetail.homeworld.replace(
            Regex("[\\D.]"), "").toInt()
        binding.layoutItem.result?.homeWorldValue = planetHashMap?.get(pos) as String

        binding.executePendingBindings()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // finish the activity when back by user
        finish()
    }
}