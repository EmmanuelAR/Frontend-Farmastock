package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.model.UserResponse
import cr.una.example.frontend_farmastock.databinding.FragmentHomeBinding
import cr.una.example.frontend_farmastock.utils.MyApplication
import cr.una.example.frontend_farmastock.viewmodel.StateUser
import cr.una.example.frontend_farmastock.viewmodel.UserViewModel
import cr.una.example.frontend_farmastock.viewmodel.UserViewModelFactory
import java.util.*


class HomeFragment : Fragment() {


    // Definition of the binding variable
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels{
        UserViewModelFactory()
    }
    private lateinit var userResponse: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        var id = MyApplication.sessionManager?.fetchUserIdLogged()
        if (id != null) {
            userViewModel.getUser(id)
        }
        // Observer method to bind data of task into text views
        userViewModel.state.observe(viewLifecycleOwner) { state ->
            with(binding.root) {
                when (state) {
                    // just checking equality because Loading is a -singleton object instance-
                    StateUser.Loading -> {
                        // TODO: If you need do something in loading
                    }
                    // Error and Success are both -classes- so we need to check their type with 'is'
                    is StateUser.Error -> {
                        // TODO: If you need do something in error
                        Toast.makeText(getActivity(), "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                    is StateUser.Success -> {
                        state.user?.let {
                            userResponse = it
                            binding.UsernameText.setText("Welcome Back "+ it.firstName+"!")
                        }
                    }
                    else -> {
                        // TODO: Not state loaded
                    }
                }
            }
        }
        val currentHour = Calendar.getInstance().get(Calendar.HOUR)
        val am_pm = Calendar.getInstance().get(Calendar.AM_PM)
        if(currentHour in 1..11 && am_pm == Calendar.AM){
            binding.timeImage.setImageResource(R.drawable.ic_good_morning_foreground)
            binding.timeText.setText("Good Morning!")
        }else if(currentHour in 12..5 && am_pm == Calendar.PM){
            binding.timeImage.setImageResource(R.drawable.ic_good_morning_foreground)
            binding.timeText.setText("Good Afternoon!")
        }else if(currentHour in 6..11 && am_pm == Calendar.PM){
            binding.timeImage.setImageResource(R.drawable.ic_good_evening_foreground)
            binding.timeText.setText("Good Evening!")
        }

        return binding.root
    }

}