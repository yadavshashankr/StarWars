package com.example.maytheforcebewith_shashankyadav.utils

import android.content.Context
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.example.maytheforcebewith_shashankyadav.R
import com.example.maytheforcebewith_shashankyadav.databinding.ItemBinding

class Tools {

    // Class to process views and Data

    companion object{

        fun rotateDown(imageView: ImageView){
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

        fun rotateUp(imageView: ImageView){
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

        fun setHeaderToBlack(context: Context?, applicationBinding: ItemBinding){
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

        fun setHeaderToWhite(context: Context?, applicationBinding: ItemBinding){

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

        fun isSpaceInString(string: String?): Boolean{
            if (string?.contains(" ") == true){
                return false
            }
            return true
        }
    }
}