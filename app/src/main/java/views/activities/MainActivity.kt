package views.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviesnews.BaseApplication
import com.example.moviesnews.Callback
import com.example.moviesnews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


private const val ID = "id"
@AndroidEntryPoint
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
        bottom_nav.setupWithNavController(navController)
    }

    override fun onMovieClicked(movieId: Long) {
        val movieIntent = Intent(this, MovieActivity::class.java)
        movieIntent.putExtra(ID, movieId)
        startActivity(movieIntent)
    }





}