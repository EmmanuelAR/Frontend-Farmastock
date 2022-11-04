package cr.una.example.frontend_farmastock.view

import android.app.Activity
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

        userViewModel =
            ViewModelProvider(this, UserViewModelFactory())[UserViewModel::class.java]

        binding.RgtButton.setOnClickListener {
//            var x = false
//            x = validateEmptySpaces()
//            if(!x){
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
//            }

        }


        }
    private fun gettingBackToLoginActivity() {

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
//    private fun validateEmptySpaces(): Boolean {
//        if(TextUtils.isEmpty(etFirstName.text.toString())){
//            Toast.makeText(this, "Empty field (First Name) not allowed",Toast.LENGTH_SHORT).show()
//            return true
//        }
//        if(TextUtils.isEmpty(etLastName.text.toString())){
//            Toast.makeText(this, "Empty field (Last Name) not allowed",Toast.LENGTH_SHORT).show()
//            return true
//        }
//        if(TextUtils.isEmpty(etEmail.text.toString())){
//            Toast.makeText(this, "Empty field (Email )not allowed",Toast.LENGTH_SHORT).show()
//            return true
//        }
//        if(TextUtils.isEmpty(etPassword.text.toString())){
//            Toast.makeText(this, "Empty field (password) not allowed",Toast.LENGTH_SHORT).show()
//            return true
//        }
//        if(!binding.validateTermsAndServices.isChecked){
//            Toast.makeText(this, "You need to agree with the terms and conditions",Toast.LENGTH_SHORT).show()
//            return true
//        }
//        Toast.makeText(this, "The user has been created successfully...",Toast.LENGTH_SHORT).show()
//        return false
//    }

    }


