package cr.una.example.frontend_farmastock.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
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
import cr.una.example.frontend_farmastock.viewmodel.LoginViewModel
import cr.una.example.frontend_farmastock.viewmodel.LoginViewModelFactory

class MainActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loginViewModel: LoginViewModel
    val br: BroadcastReceiver = Notification()
    var specificDate: String = ""
    private var loginState: MutableLiveData<Boolean>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment

        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottonNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.profileFragment, R.id.homeFragment, R.id.medicineMainFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottonNavigationView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            warningMessage()

        }
        // Setup state to manage log out option
        loginState=MutableLiveData<Boolean>()
        setLogOutObserver()


//        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
//            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
//        }
//        registerReceiver(br, filter)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun goToLoginActivity(): Boolean {
            loginViewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        return true
    }

    private fun setLogOutObserver(){
            val state: Observer<Boolean> = object : Observer<Boolean> {
                @Override
                override fun onChanged(@Nullable newState: Boolean?) {
                    if(newState!!){
                        goToLoginActivity()
                    }
                }
            }
            loginState!!.observe(this, state)
    }
    private fun warningMessage():Boolean {
        AlertDialog.Builder(this)
            .setTitle("Log out")
            .setIcon(R.drawable.ic_warning)
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, _ ->
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
               loginState!!.value=true
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                loginState!!.value=false
            }
            .create()
            .show()

        return true

    }

}