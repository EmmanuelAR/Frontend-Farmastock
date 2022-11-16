package cr.una.example.frontend_farmastock.view


import android.app.Activity
import com.google.gson.Gson
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.ActivityLoginBinding
import cr.una.example.frontend_farmastock.databinding.ActivityRegisterBinding
import cr.una.example.frontend_farmastock.model.LoginRequest
import cr.una.example.frontend_farmastock.model.RoleDetails
import cr.una.example.frontend_farmastock.model.UserRequest
import cr.una.example.frontend_farmastock.viewmodel.UserViewModel
import cr.una.example.frontend_farmastock.viewmodel.UserViewModelFactory
//import kotlinx.android.synthetic.main.activity_login.*
//import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user1 = intent.extras?.get("user1")
        if(user1 != null){
            val gson = Gson()
            val userRequest = gson.fromJson(user1.toString(), UserRequest::class.java)
            fillForm(userRequest)
        }

        userViewModel =
            ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]

        binding.RgtButton.setOnClickListener {
         var x = false
            x = validateEmptySpaces()
            if(!x){
                userViewModel.createUser(
                    UserRequest(
                        firstName =  binding.etFirstName.text.toString() ,
                        lastName = binding.etLastName.text.toString(),
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString(),
                        enabled = true,
                        roleList = List<RoleDetails>(1){RoleDetails(1,null,null)}
                    )
                )
                gettingBackToLoginActivity()
            }

        }
        binding.terms.setOnClickListener {
            termsandConditionsActivity()
        }

        }
    private fun fillForm(userRequest: UserRequest){
        binding.etFirstName.setText(userRequest.firstName)
        binding.etLastName.setText(userRequest.lastName)
        binding.etEmail.setText(userRequest.email)
        binding.etPassword.setText(userRequest.password)
        binding.validateTermsAndServices.isChecked = true


    }
    private fun termsandConditionsActivity(){
        val intent = Intent(this, Terms_and_conditions::class.java)
        val json = Gson()
        val userRequest = UserRequest()
        userRequest.firstName = binding.etFirstName.text.toString()
        userRequest.lastName = binding.etLastName.text.toString()
        userRequest.email = binding.etEmail.text.toString()
        userRequest.password = binding.etPassword.text.toString()
        var x = json.toJson(userRequest)
        intent.putExtra("user",x)
        finish()
        startActivity(intent)

    }
    private fun gettingBackToLoginActivity() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun validateEmptySpaces(): Boolean {
        if(TextUtils.isEmpty(binding.etFirstName.text.toString())){
            Toast.makeText(this, "Empty field (First Name) not allowed",Toast.LENGTH_SHORT).show()
            return true
        }
        if(TextUtils.isEmpty(binding.etLastName.text.toString())){
            Toast.makeText(this, "Empty field (Last Name) not allowed",Toast.LENGTH_SHORT).show()
            return true
        }
        if(TextUtils.isEmpty(binding.etEmail.text.toString())){
            Toast.makeText(this, "Empty field (Email )not allowed",Toast.LENGTH_SHORT).show()
            return true
        }
        if(TextUtils.isEmpty(binding.etPassword.text.toString())){
            Toast.makeText(this, "Empty field (password) not allowed",Toast.LENGTH_SHORT).show()
            return true
        }
        if(!binding.validateTermsAndServices.isChecked){
            Toast.makeText(this, "You need to agree with the terms and conditions",Toast.LENGTH_SHORT).show()
            return true
        }
        Toast.makeText(this, "The user has been created successfully...",Toast.LENGTH_SHORT).show()
        return false
    }

    }


