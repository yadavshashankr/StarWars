package com.example.maytheforcebewith_shashankyadav.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maytheforcebewith_shashankyadav.R
import com.example.maytheforcebewith_shashankyadav.databinding.ItemBinding
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetHashMap
import com.example.maytheforcebewith_shashankyadav.globals.RecyclerItemClickListener
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processFilmsValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processSpeciesValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processStarshipsValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processVehiclesValues
import com.example.maytheforcebewith_shashankyadav.responses.Results1
import com.example.maytheforcebewith_shashankyadav.ui.CharacterDetail
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.apiFavourite
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.cotractItemRow
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.expanditemRow
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.favourite
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.resetFavouite
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.setFavourite
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.setFavouriteIcon
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.setUnFavouriteIcon


class CustomAdapter : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    lateinit var context: Context
    var articles: ArrayList<Results1> = ArrayList<Results1>()
    private var recyclerItemClickListener: RecyclerItemClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        article: ArrayList<Results1>,
        context: Context,
        callback: RecyclerItemClickListener
    ) {
        this.context = context
        articles = article
        this.recyclerItemClickListener = callback
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val recyclerListRowBinding:
                ItemBinding = ItemBinding.inflate(inflater, parent, false)

        return MyViewHolder(recyclerListRowBinding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class MyViewHolder(var applicationBinding: ItemBinding) :
        RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(position : Int) {
            // if size of list is 1 that means its a search and show the view in expanded form and changing colors of header

            when (articles.size == 1) {
                true -> expanditemRow(
                    applicationBinding,
                    position,
                    itemView,
                    context,
                    recyclerItemClickListener
                )
                false -> cotractItemRow(applicationBinding, context)
            }
            /* clicking on fav icon will send data to the webhook, save the characters selected data on the
            data object of the list and also changing the icon's selected state
            */
            applicationBinding.ivFav.setOnClickListener {
                when (apiFavourite(applicationBinding)) {
                    false -> setFavourite(
                        applicationBinding,
                        position,
                        itemView,
                        context,
                        recyclerItemClickListener
                    )
                    true -> resetFavouite(
                        applicationBinding,
                        position,
                        itemView,
                        context,
                        recyclerItemClickListener
                    )
                }
            }
            // if data received within list already has favourites then it will be processed here
            when (favourite(applicationBinding)) {
                true -> setFavouriteIcon(applicationBinding)
                false -> setUnFavouriteIcon(applicationBinding)
            }
            // clicking on header of item of list will send user to next activity
            applicationBinding.llHead.setOnClickListener {
                context.startActivity(Intent(context, CharacterDetail::class.java).apply {
                    putExtra("character", articles[position])
                })
            }
        }
    }



    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {

        holder.applicationBinding.result = articles.get(position)

        // setting tag for Fav icon to identify its state
        holder.applicationBinding.ivFav.tag = R.drawable.ic_baseline_favorite_border_24


        // retrieving data from respective hashmaps to display names on the character detail list item
        val positionPlanet = holder.applicationBinding.result?.homeworld?.replace(
            Regex("[\\D.]"), ""
        )?.toInt()
        holder.applicationBinding.result?.homeWorldValue = planetHashMap?.get(positionPlanet) as String
        if (holder.applicationBinding.result?.species?.size != 0) {
            val speciesValue = processSpeciesValues(holder.applicationBinding.result?.species)
            holder.applicationBinding.result?.speciesValue = speciesValue
        } else {
            holder.applicationBinding.result?.speciesValue = "NA"
        }
        if (holder.applicationBinding.result?.films?.size != 0) {
            val filmsValue = processFilmsValues(holder.applicationBinding.result?.films)
            holder.applicationBinding.result?.filmsValues = filmsValue
        } else {
            holder.applicationBinding.result?.filmsValues = "NA"
        }
        if (holder.applicationBinding.result?.vehicles?.size != 0) {
            val vehiclesValue = processVehiclesValues(holder.applicationBinding.result?.vehicles)
            holder.applicationBinding.result?.vehiclesValue = vehiclesValue
        } else {
            holder.applicationBinding.result?.vehiclesValue = "NA"
        }
        if (holder.applicationBinding.result?.starships?.size != 0) {
            val starshipsValue = processStarshipsValues(holder.applicationBinding.result?.starships)
            holder.applicationBinding.result?.starshipsValue = starshipsValue
        } else {
            holder.applicationBinding.result?.starshipsValue = "NA"
        }
        holder.applicationBinding.executePendingBindings()
        holder.bind(position)
    }
}