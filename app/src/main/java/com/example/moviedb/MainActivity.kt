package com.example.moviedb

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val oldColor = window.statusBarColor
        val oldFlags = window.decorView.systemUiVisibility

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.detailFragment) {
                binding.toolbar.visibility = View.GONE
                applyStatusBarColor(false, oldColor, oldFlags)
            } else {
                binding.toolbar.visibility = View.VISIBLE
                applyStatusBarColor(true, oldColor, oldFlags)
            }
        }

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun applyStatusBarColor(defaultColor: Boolean, oldColor: Int, oldFlags: Int){
        if (defaultColor){
            window.statusBarColor = oldColor
            window.decorView.systemUiVisibility = oldFlags
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = getColor(R.color.black)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = oldFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

}