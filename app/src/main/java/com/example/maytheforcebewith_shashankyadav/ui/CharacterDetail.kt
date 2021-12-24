package com.example.maytheforcebewith_shashankyadav.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.maytheforcebewith_shashankyadav.R
import com.example.maytheforcebewith_shashankyadav.databinding.ItemBinding
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetHashMap
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processFilmsValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processSpeciesValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processStarshipsValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processVehiclesValues
import com.example.maytheforcebewith_shashankyadav.responses.Results1

/*CharacterDetail Activity displays the detailed data of selected character inside a new activity*/
class CharacterDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Receive data object of selected item from  MainActivity's list
        val characterDetail = intent.extras?.get("character") as Results1
        // create binding object
        val binding = DataBindingUtil.setContentView<ItemBinding>(this, R.layout.item)


        binding.lifecycleOwner = this
        binding.result = characterDetail
        binding.ivFav.visibility = View.GONE
        binding.ivArrow.visibility = View.GONE
        binding.rlExpand.visibility = View.VISIBLE

        //Changing the header layout to white and textview to black
        binding.llHead.setBackgroundResource(R.color.white)
        binding.name.setTextColor(resources.getColor(R.color.black))
        binding.tvName.setTextColor(resources.getColor(R.color.black))
        binding.birth.setTextColor(resources.getColor(R.color.black))
        binding.tvBirth.setTextColor(resources.getColor(R.color.black))

        // process arraylist of films, species, vehicles and starships to retrieve data for the selected character
        if (characterDetail.films?.size != 0) {
            val filmsValue = processFilmsValues(characterDetail.films)
            binding.result?.filmsValues = filmsValue
        }
        if (characterDetail.species?.size != 0) {
            val speciesValue = processSpeciesValues(characterDetail.species)
            binding.result?.speciesValue = speciesValue
        }
        if (characterDetail.vehicles?.size != 0) {
            val vehiclesValue = processVehiclesValues(characterDetail.vehicles)
            binding.result?.vehiclesValue = vehiclesValue
        }
        if (characterDetail.starships?.size != 0) {
            val starshipsValue = processStarshipsValues(characterDetail.starships)
            binding.result?.starshipsValue = starshipsValue
        }

        val pos = characterDetail.homeworld?.replace(
            Regex("[\\D.]"), ""
        )?.toInt()
        binding.result?.homeWorldValue = planetHashMap?.get(pos)

        binding.executePendingBindings()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // finish the activity when back by user
        finish()
    }
}