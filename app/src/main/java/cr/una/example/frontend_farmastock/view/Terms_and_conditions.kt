package cr.una.example.frontend_farmastock.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.ActivityRegisterBinding
import cr.una.example.frontend_farmastock.databinding.ActivityTermsAndConditionsBinding
import cr.una.example.frontend_farmastock.model.UserRequest

class Terms_and_conditions : AppCompatActivity() {

    private lateinit var binding: ActivityTermsAndConditionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTermsAndConditionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.get("user")
        binding.btnAceptar.setOnClickListener{
            goBackToRegister(user.toString())
        }

    }
    private fun goBackToRegister(user:String){

        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("user1", user)
        finish()
        startActivity(intent)

    }


}
