package com.shashank.maytheforcebewith_shashankyadav.utils


import android.content.Context
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.shashank.maytheforcebewith_shashankyadav.R
import com.shashank.maytheforcebewith_shashankyadav.databinding.ItemBinding
import com.shashank.maytheforcebewith_shashankyadav.globals.RecyclerItemClickListener
import com.shashank.maytheforcebewith_shashankyadav.responses.FavData1


class Tools {

    // Class to process views and Data

    companion object {

        fun rotateDown(imageView: ImageView) {
            var mCurrRotation = 0f
            val fromRotation: Float = mCurrRotation
            var toRotation = 180f

            val rotateAnim = RotateAnimation(
                fromRotation, toRotation, imageView.width / 2f, imageView.height / 2f
            )
            rotateAnim.duration = 300
            rotateAnim.fillAfter = true
            imageView.startAnimation(rotateAnim)
        }

        fun rotateUp(imageView: ImageView) {
            var mCurrRotation = 180f
            val fromRotation: Float = mCurrRotation
            var toRotation = 360f

            val rotateAnim = RotateAnimation(
                fromRotation, toRotation, imageView.width / 2f, imageView.height / 2f
            )
            rotateAnim.duration = 300
            rotateAnim.fillAfter = true
            imageView.startAnimation(rotateAnim)

        }

        fun setHeaderToBlack(context: Context?, applicationBinding: ItemBinding) {
            context?.resources?.getColor(R.color.white)
                ?.let { it1 -> applicationBinding.tvName.setTextColor(it1) }
            context?.resources?.getColor(R.color.white)
                ?.let { it1 -> applicationBinding.name.setTextColor(it1) }
            context?.resources?.getColor(R.color.white)
                ?.let { it1 -> applicationBinding.tvBirth.setTextColor(it1) }
            context?.resources?.getColor(R.color.white)
                ?.let { it1 -> applicationBinding.birth.setTextColor(it1) }
            applicationBinding.llHead.setBackgroundResource(R.color.black)

        }

        fun setHeaderToWhite(context: Context?, applicationBinding: ItemBinding) {

            context?.resources?.getColor(R.color.black)
                ?.let { it1 -> applicationBinding.tvName.setTextColor(it1) }
            context?.resources?.getColor(R.color.black)
                ?.let { it1 -> applicationBinding.name.setTextColor(it1) }
            context?.resources?.getColor(R.color.black)
                ?.let { it1 -> applicationBinding.tvBirth.setTextColor(it1) }
            context?.resources?.getColor(R.color.black)
                ?.let { it1 -> applicationBinding.birth.setTextColor(it1) }
            applicationBinding.llHead.setBackgroundResource(R.color.white)
        }

        fun setUnFavouriteIcon(applicationBinding: ItemBinding) {
            applicationBinding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        fun setFavouriteIcon(applicationBinding: ItemBinding) {
            return applicationBinding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }

        fun favourite(applicationBinding: ItemBinding): Boolean {
            return applicationBinding.result?.fav as Boolean
        }

        fun expanditemRow(
            applicationBinding: ItemBinding, position: Int, itemView: View, context: Context?,
            recyclerItemClickListener: RecyclerItemClickListener?
        ) {
            applicationBinding.rlExpand.visibility = View.VISIBLE
            applicationBinding.ivArrow.setImageDrawable(ContextCompat.getDrawable(applicationBinding.ivArrow.context, R.drawable.ic_baseline_keyboard_arrow_down_24_black))
            recyclerItemClickListener?.onItemClick(null, position, itemView)
        }

        fun cotractItemRow(applicationBinding: ItemBinding, context: Context?) {
            applicationBinding.rlExpand.visibility = View.GONE
            applicationBinding.ivArrow.setImageDrawable(ContextCompat.getDrawable(applicationBinding.ivArrow.context, R.drawable.ic_baseline_keyboard_arrow_down_24))
        }

        fun rowItemExpanded(applicationBinding: ItemBinding): Boolean {
            return applicationBinding.rlExpand.visibility == View.VISIBLE
        }

        fun resetFavouite(
            applicationBinding: ItemBinding, position: Int, itemView: View, context: Context?,
            recyclerItemClickListener: RecyclerItemClickListener?
        ) {
            applicationBinding.ivFav.tag = R.drawable.ic_baseline_favorite_border_24
            applicationBinding.ivFav.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
            val favData = FavData1(applicationBinding.result?.name, false)
            recyclerItemClickListener?.onItemClick(favData, position, itemView)
            applicationBinding.result?.fav = false
        }

        fun setFavourite(
            applicationBinding: ItemBinding, position: Int, itemView: View, context: Context?,
            recyclerItemClickListener: RecyclerItemClickListener?
        ) {
            applicationBinding.ivFav.tag = 1
            applicationBinding.ivFav.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_baseline_favorite_24))
            val favData = FavData1(applicationBinding.result?.name, true)
            recyclerItemClickListener?.onItemClick(favData, position, itemView)
            applicationBinding.result?.fav = true
        }

        fun apiFavourite(applicationBinding: ItemBinding): Boolean {
            return applicationBinding.ivFav.tag == R.drawable.ic_baseline_favorite_border_24
        }


        fun isSpaceInString(string: String?): Boolean {
            if (string?.contains(" ") == true) {
                return false
            }
            return true
        }
    }
}