package com.openclassrooms.realestatemanager.activities.estate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.activities.login.EstateContract
import com.openclassrooms.realestatemanager.activities.main.MainActivity
import com.openclassrooms.realestatemanager.adapters.SlideshowAdapter
import com.openclassrooms.realestatemanager.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_estate.*
import kotlinx.android.synthetic.main.slideshow_list.*
import timber.log.Timber


class EstateActivity(override val activityLayout: Int = R.layout.activity_estate) : BaseActivity(), EstateContract.EstateViewInterface, NavigationView.OnNavigationItemSelectedListener {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: SlideshowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureNavigationView()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //  Navigation Drawer item settings
        val id = item.itemId

        when (id) {
            R.id.drawer_first -> {
                Timber.i("Click first")
                /**
                 *
                val lunch = Intent(this, PlacesDetailActivity::class.java)
                mIntent = getString(R.string.lunch)
                lunch.putExtra(getString(R.string.detail_intent), mIntent)
                this.startActivity(lunch)
                Timber.i("Click Your Lunch")
                 */
            }
            R.id.drawer_second -> {
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                Timber.i("Click second")
            }
            R.id.drawer_third -> {
                val intent = Intent(baseContext, EstateActivity::class.java)
                startActivity(intent)
                Timber.i("Click third")
            }
            else -> {
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    fun configureNavigationView() {
        description_nav_view.setNavigationItemSelectedListener(this)
    }

    /**
     * Initialize the recyclerview for history.
     */
    fun configureRecyclerView() {
        mRecyclerView = slideshow_recyclerview
        //   mAdapter = SlideshowAdapter(imageList, this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
    }
}
