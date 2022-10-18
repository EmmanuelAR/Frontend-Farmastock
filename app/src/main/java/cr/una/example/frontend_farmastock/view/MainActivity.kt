package cr.una.example.frontend_farmastock.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import cr.una.example.frontend_farmastock.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nav_menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_item_profile-> {
                setToolbarTitle("Profile")
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
            }
            R.id.nav_item_home -> {
                setToolbarTitle("Inicio")
//                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProfileFragment()).commit()
            }
            R.id.nav_item_medicine -> {
                setToolbarTitle("Medicinas")
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,  MedicineMainFragment()).commit()
            }
            R.id.nav_item_medicine -> {
                setToolbarTitle("Medicinas")
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    MedicineMainFragment()).commit()
            }
        }
        return true
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun changeFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var navigationView = findViewById<NavigationView>(R.id.nav_menu)
        val headerView: View = navigationView.getHeaderView(0)
        val nombre = headerView.findViewById<TextView>(R.id.nav_header_nombre)
        //nombre.setText(usuario.nombre)

        //Gestion de roles
      /*  var nav_Menu = navigationView.getMenu()
        when (usuario.rol) {
            "ADM" -> {
                Administrador(nav_Menu)
            }
            "ClI" -> {
                Cliente(nav_Menu)
        }*/
        return true
    }
    //Gestionar acceso a secciones
    private fun Administrador(menu: Menu) {

    }

    private fun Cliente(menu: Menu) {

    }

    private fun logout() {

    }

    private fun salir() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}