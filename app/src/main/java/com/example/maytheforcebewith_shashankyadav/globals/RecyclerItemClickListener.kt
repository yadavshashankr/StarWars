package com.example.maytheforcebewith_shashankyadav.globals

import android.view.View
import com.example.maytheforcebewith_shashankyadav.responses.FavData1

interface RecyclerItemClickListener {
    fun onItemClick(notice: FavData1?, position: Int, view: View)
}