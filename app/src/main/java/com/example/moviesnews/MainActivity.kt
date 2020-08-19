package com.example.moviesnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ID = "id"
private const val POSTER_PATH = "posterPath"
private const val BACK_DROP_PATH = "backdropPath"
private const val TITLE = "title"
private const val RELEASE_DATE = "releaseDate"
private const val OVERVIEW = "overview"

class MainActivity : AppCompatActivity(), Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return


        val navController = host.navController

        setupBottomNavMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav?.setupWithNavController(navController)

    }

    override fun onMovieClicked() {
        val movieIntent = Intent(this, MovieActivity::class.java)
        movieIntent.putExtra(ID,Utils.itemId)
        startActivity(movieIntent)
    }


}