package cr.una.example.frontend_farmastock.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.ActivityMainBinding
import cr.una.example.frontend_farmastock.utils.Notification

class MainActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    val br: BroadcastReceiver = Notification()
    var specificDate:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottonNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.profileFragment,R.id.homeFragment,R.id.medicineMainFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)



//        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        registerReceiver(br, filter)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}