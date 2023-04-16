package com.shashank.maytheforcebewith_shashankyadav.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.maytheforcebewith_shashankyadav.R
import com.shashank.maytheforcebewith_shashankyadav.adapters.CustomAdapter
import com.shashank.maytheforcebewith_shashankyadav.presenters.IPresenter
import com.shashank.maytheforcebewith_shashankyadav.presenters.MainPresenter
import com.shashank.maytheforcebewith_shashankyadav.globals.RecyclerItemClickListener
import kotlin.collections.ArrayList
import android.text.Editable
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.shashank.maytheforcebewith_shashankyadav.databinding.ActivityMainBinding

import com.shashank.maytheforcebewith_shashankyadav.globals.ApplicationConstant.Companion.peopleArticles

import com.shashank.maytheforcebewith_shashankyadav.responses.FavData1
import com.shashank.maytheforcebewith_shashankyadav.responses.Percentage
import com.shashank.maytheforcebewith_shashankyadav.responses.Results1
import com.shashank.maytheforcebewith_shashankyadav.utils.NetworkCheck.Companion.verifyAvailableNetwork

/*MainActivity shows Expandable list and allows user to Set favourite to any character*/
class MainActivity : AppCompatActivity(), IPresenter {

    private var activatedOnce: Boolean? = null
    private var isLoading: Boolean = true
    private var pastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var visbileItem: Int = 0
    private var previousItem: Int = 0
    private var viewThreshold: Int = 7
    private lateinit var mAdapter: CustomAdapter
    private lateinit var mlayoutManager: LinearLayoutManager
    private lateinit var mMainPresenter: MainPresenter
    private var peopleArticles1: ArrayList<Results1>? = ArrayList()
    private var searchArticles: ArrayList<Results1>? = null
    private var recyclerItemClickListener: RecyclerItemClickListener? = null
    private var params: RelativeLayout.LayoutParams? = null
    private var pageNumber: Int = 1
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Creating binding object
        activityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        activityMainBinding.lifecycleOwner = this

        initPresenter()
    }

    private fun initPresenter() {
        // Initializing Presenter, adapter and recyclerview
        mMainPresenter = MainPresenter(this)

        mAdapter = CustomAdapter()

        mlayoutManager = LinearLayoutManager(this)
        params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        activityMainBinding.list.apply {
            layoutManager = mlayoutManager
            adapter = mAdapter
        }
    }

    override fun onSuccess(articles: ArrayList<Results1>, request: String?) {
        peopleArticles1 = articles
        runOnUiThread {
            setAdapterData(peopleArticles1)
        }

    }

    override fun isApiLoading(isLoading: Boolean?) {
        runOnUiThread{
            if (isLoading as Boolean) {
                activityMainBinding.mainPb.visibility = View.VISIBLE
                activityMainBinding.list.isClickable = false
            } else {
                activityMainBinding.mainPb.visibility = View.GONE
            }
        }
    }

    override fun onFailure() {}
    override fun onApiPercentage(percent: Percentage?) {}
    override fun onSuccessCode(code: Int?) {}

    private fun activateListeners() {

        // to search for each character in the string and load the adapter everytime if matched string is found
        activityMainBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {
                if (peopleArticles1?.size != 0) {

                    searchArticles = ArrayList()
                    for (d in peopleArticles1 as ArrayList) {
                        if (d.name.lowercase().contains(arg0.toString().lowercase())
                        ) {
                            searchArticles?.add(d)
                        }
                    }
                    if (searchArticles?.size as Int > 0) {
                        setAdapterData(searchArticles)
                    } else {
                        setAdapterData(peopleArticles1)
                    }
                }
            }
        }).also {

            activityMainBinding.list.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    visbileItem = mlayoutManager.childCount
                    totalItemCount = mlayoutManager.itemCount
                    pastVisibleItem = mlayoutManager.findFirstVisibleItemPosition()
                    // check if the listview is scrolled downwards
                    if (dy > 0) {
                        if (verifyAvailableNetwork(this@MainActivity)) {
                            if (isLoading && totalItemCount > previousItem) {
                                // if total items are more than previously recorded items then don't call api
                                isLoading = false
                                previousItem = totalItemCount
                            }
                            if (!isLoading && (totalItemCount - visbileItem) <= (pastVisibleItem + viewThreshold) && pageNumber < 10) {
                                /* if total items list reach the threshold within the context of visible items and past visible item
                               then call api */
                                mMainPresenter.getPeopleApis(++pageNumber)

                                isLoading = true
                                activatedOnce = null
                            }
                        } else {
                            if (activatedOnce()) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Please connect to Internet",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isLoading = true
                            }
                        }
                    }
                }
            })
        }.also {
            recyclerItemClickListener = object : RecyclerItemClickListener {
                override fun onItemClick(fav: FavData1?, position: Int, view: View) {
                    /* On clicking an item from list will bring the header part of list to top, for better viewing
                       of character details*/
                    if (fav != null) {
                        mMainPresenter.postFavApi(fav)
                    } else {
                        val itemToScroll: Int =
                            activityMainBinding.list?.getChildLayoutPosition(view) as Int
                        val centerOfScreen: Int =
                            activityMainBinding.list?.width as Int / 2 - view.width / 2
                        mlayoutManager.scrollToPositionWithOffset(itemToScroll, centerOfScreen)
                    }
                }
            }
        }.also {
            // set the data to the adapters as soon as all listeners are initialized
            peopleArticles1 = peopleArticles
            setAdapterData(peopleArticles1)
        }
    }

    private fun activatedOnce(): Boolean {
        activatedOnce = when (activatedOnce) {
            null -> true
            else -> false
        }
        return activatedOnce as Boolean
    }

    fun setAdapterData(data: ArrayList<Results1>?) {
        mAdapter.setData(
            data as ArrayList<Results1>,
            this@MainActivity,
            recyclerItemClickListener as RecyclerItemClickListener
        )
    }

    override fun onResume() {
        super.onResume()
        activateListeners()
        activityMainBinding.etSearch.setText("")
        activityMainBinding.etSearch.clearFocus()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}