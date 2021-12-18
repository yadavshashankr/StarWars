package com.example.maytheforcebewith_shashankyadav.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maytheforcebewith_shashankyadav.R
import com.example.maytheforcebewith_shashankyadav.databinding.ItemBinding

import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.rotateDown
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.rotateUp
import com.example.maytheforcebewith_shashankyadav.globals.RecyclerItemClickListener
import com.example.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.planetHashMap
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processFilmsValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processSpeciesValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processStarshipsValues
import com.example.maytheforcebewith_shashankyadav.process.ProcessApiData.Companion.processVehiclesValues
import com.example.maytheforcebewith_shashankyadav.responses.FavData1
import com.example.maytheforcebewith_shashankyadav.responses.Results1
import com.example.maytheforcebewith_shashankyadav.ui.CharacterDetail
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.setHeaderToBlack
import com.example.maytheforcebewith_shashankyadav.utils.Tools.Companion.setHeaderToWhite


class CustomAdapter : RecyclerView.Adapter<CustomAdapter.MyViewHolder>(){

    var context: Context? = null
    var articles : ArrayList<Results1> = ArrayList<Results1>()
    private var recyclerItemClickListener: RecyclerItemClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(article: ArrayList<Results1>, context: Context, callback: RecyclerItemClickListener){
        this.context = context
        articles = ArrayList()
        for(items in article){
            articles.add(items)
        }
        this.recyclerItemClickListener = callback
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val recyclerListRowBinding :
                ItemBinding = ItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(recyclerListRowBinding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class MyViewHolder(var applicationBinding: ItemBinding) : RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind() {
            // if size of list is 1 that means its a search and show the view in expanded form and changing colors of header
            if (articles.size == 1){
                applicationBinding.rlExpand.visibility = View.VISIBLE
                applicationBinding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24_black)
                rotateDown(applicationBinding.ivArrow)
                setHeaderToWhite(context, applicationBinding)
            }else{
                applicationBinding.rlExpand.visibility = View.GONE
                applicationBinding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                rotateUp(applicationBinding.ivArrow)
                setHeaderToBlack(context, applicationBinding)
            }

            // clicking on the arrow on item in list will expand the view changing colors of header
            applicationBinding.ivArrow.setOnClickListener {
                if (applicationBinding.rlExpand.visibility == View.VISIBLE){
                    applicationBinding.rlExpand.visibility = View.GONE
                    applicationBinding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    rotateUp(applicationBinding.ivArrow)
                    setHeaderToBlack(context, applicationBinding)
                }else{
                    applicationBinding.rlExpand.visibility = View.VISIBLE
                    applicationBinding.ivArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24_black)
                    rotateDown(applicationBinding.ivArrow)
                    setHeaderToWhite(context, applicationBinding)
                    recyclerItemClickListener?.onItemClick(null, position, itemView)
                }
            }
            /* clicking on fav icon will send data to the webhook, save the characters selected data on the
            data object of the list and also changing the icon's selected state
            */
            applicationBinding.ivFav.setOnClickListener {
                if (applicationBinding.ivFav.tag == R.drawable.ic_baseline_favorite_border_24){
                    applicationBinding.ivFav.tag = 1
                    applicationBinding.ivFav.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_baseline_favorite_24))
                    val favData = FavData1(applicationBinding.result?.name, true)
                    recyclerItemClickListener?.onItemClick(favData, position, itemView)
                    applicationBinding.result?.fav = true

                }else{
                    applicationBinding.ivFav.tag = R.drawable.ic_baseline_favorite_border_24
                    applicationBinding.ivFav.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                    val favData = FavData1(applicationBinding.result?.name, false)
                    recyclerItemClickListener?.onItemClick(favData, position, itemView)
                    applicationBinding.result?.fav = false
                }
            }

            // if data received within list already has favourites then it will be processed here
            if (applicationBinding.result?.fav as Boolean){
                applicationBinding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                applicationBinding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            // clicking on header of item of list will send user to next activity
            applicationBinding.llHead.setOnClickListener {

                context?.startActivity(Intent(context, CharacterDetail::class.java).apply {
                    putExtra("character", articles.get(absoluteAdapterPosition))
                })
            }

        }
    }

    override fun onBindViewHolder(holder: CustomAdapter.MyViewHolder, position: Int) {

        holder.applicationBinding.result = articles.get(position)

        // setting tag for Fav icon to identify its state
        holder.applicationBinding.ivFav.tag = R.drawable.ic_baseline_favorite_border_24


        // retrieving data from respective hashmaps to display names on the character detail list item
        val positionPlanet = holder.applicationBinding.result?.homeworld?.replace(Regex("[\\D.]")
            , "")?.toInt()
        holder.applicationBinding.result?.homeWorldValue = planetHashMap?.get(positionPlanet)
        if (holder.applicationBinding.result?.species?.size != 0){
            val speciesValue = processSpeciesValues(holder.applicationBinding.result?.species)
            holder.applicationBinding.result?.speciesValue = speciesValue
        }else{
            holder.applicationBinding.result?.speciesValue = "NA"
        }
        if (holder.applicationBinding.result?.films?.size != 0){
            val filmsValue = processFilmsValues(holder.applicationBinding.result?.films)
            holder.applicationBinding.result?.filmsValues = filmsValue
        }else{
            holder.applicationBinding.result?.filmsValues = "NA"
        }
        if (holder.applicationBinding.result?.vehicles?.size != 0){
            val vehiclesValue = processVehiclesValues(holder.applicationBinding.result?.vehicles)
            holder.applicationBinding.result?.vehiclesValue = vehiclesValue
        }else{
            holder.applicationBinding.result?.vehiclesValue = "NA"
        }
        if (holder.applicationBinding.result?.starships?.size != 0){
            val starshipsValue = processStarshipsValues(holder.applicationBinding.result?.starships)
            holder.applicationBinding.result?.starshipsValue = starshipsValue
        }else{
            holder.applicationBinding.result?.starshipsValue = "NA"
        }
        holder.applicationBinding.executePendingBindings()
        holder.bind()
    }
}