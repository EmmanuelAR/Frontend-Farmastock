package cr.una.example.frontend_farmastock.view

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentReminderAddBinding
import cr.una.example.frontend_farmastock.model.ReminderRequest
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReminderAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReminderAddFragment : Fragment() {
    // Definition of the binding variable
    private var _binding: FragmentReminderAddBinding? = null
    private val binding get() = _binding!!

    // View model
    private val reminderViewModel: ReminderViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReminderAddBinding.inflate(inflater, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderAddBinding.bind(view)
        binding.calendarPicker.setOnClickListener {

//            reminderViewModel.createReminder(
//                ReminderRequest(
//                    id =  8,
//                    name = binding.formName.text.toString(),
//                    dose = Integer.parseInt(binding.formDose.text.toString()),
//                    quantity =  Integer.parseInt(binding.formQuantity.text.toString())
//
//                )
//            )
//
//            Navigation.findNavController(view).navigate(R.id.action_reminderAddFragment_to_reminderMainFragment)
        }
        binding.calendarPicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(parentFragmentManager, "DatePicker")


            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))


            }
        }

    }

}

