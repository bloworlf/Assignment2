package io.drdroid.assignment2.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import io.drdroid.assignment2.R
import io.drdroid.assignment2.base.BaseActivity
import io.drdroid.assignment2.databinding.ActivityHomeBinding
import io.drdroid.assignment2.utils.Utils

@AndroidEntryPoint
class Home : BaseActivity() {

    private lateinit var bind: ActivityHomeBinding
    private lateinit var controller: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.navigation, null)
        Utils.setLightStatusBar(this)

        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        toolbar = bind.toolbar
        setSupportActionBar(toolbar)

        navView = bind.btmView
        controller = findNavController(R.id.fragment)
        val navConfig = AppBarConfiguration(
            setOf(R.id.menu_home, R.id.menu_search)
        )

        setupActionBarWithNavController(controller, navConfig)
        navView.setupWithNavController(controller)

        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.menu_home, R.id.menu_search -> {
                    navView.visibility = View.VISIBLE
                }

                else -> {
                    navView.visibility = View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!onSupportNavigateUp()) {
            super.onBackPressed()
        }
    }

    fun showToolbar() {
        bind.appBar.setExpanded(true, true)
    }

    fun titleColor(color: Int) {
        toolbar.setTitleTextColor(color)

        toolbar.navigationIcon?.let {
            val wrapDrawable = DrawableCompat.wrap(toolbar.navigationIcon!!)
            DrawableCompat.setTint(wrapDrawable, color)
            toolbar.navigationIcon = wrapDrawable
        }

        for (i in 0 until toolbar.menu.size()) {
            try {
                val wDrawable = DrawableCompat.wrap(toolbar.menu.getItem(i).icon!!)
                DrawableCompat.setTint(wDrawable, color)
                toolbar.menu.getItem(i).icon = wDrawable
            } catch (e: Exception) {
            }
        }
    }
}