package cr.una.example.frontend_farmastock.view

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentReminderAddBinding
import cr.una.example.frontend_farmastock.model.ReminderRequest
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModel
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import kotlinx.android.synthetic.main.fragment_reminder_details.*
import kotlinx.android.synthetic.main.medicine_item.view.*
import okhttp3.internal.toHexString
import java.util.*


class ReminderAddFragment : Fragment() {
    // Definition of the binding variable
    private var _binding: FragmentReminderAddBinding? = null
    private val binding get() = _binding!!
    private var daysOfTheWeek = mutableListOf<TextView>()
    private var specificDate = ""
    private var timeSelected = ""


    private val reminderViewModel: ReminderViewModel by activityViewModels()

    private val medicineViewModel: MedicineViewModel by activityViewModels{
        MedicineViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderAddBinding.inflate(inflater, container, false)

        // DATE PICKER
        binding.calendarPicker.setOnClickListener {
            onClickListenerDatePicker()
        }
        var days  = mutableListOf(
            binding.Sunday, binding.Monday, binding.Tuesday, binding.Wednesday,
            binding.Thursday, binding.Friday, binding.Saturday
        )
        for(day in days){
            onClickListenerWeekDays(day)
        }

        // MEDICINE SELECT
        val medicineId: String = arguments?.getString(MedicineAdapter.MEDICINE_ID) ?: "0"
        if(medicineId!="0") medicineViewModel.getMedicine(medicineId.toLong())

        medicineViewModel.state.observe(viewLifecycleOwner) { state ->
            with(binding.root) {
                when (state) {
                    StateMedicine.Loading -> {}
                    is StateMedicine.Error -> {}
                    is StateMedicine.Success -> {
                        state.medicine?.let {
                            binding.medicineSelectInfo.text = it.name.toString()
                        }
                    }
                    else -> {}
                }
            }
        }

        binding.Save.setOnClickListener {
            if(validate()){
                // Repetitivo por dias de la semana a la hora que tenga
                // el time picker
                if(daysOfTheWeek.size != 0){

                }
                // Por fecha especifica que tenga el date picker
                // y la hora que tenga time picker
                else{

                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderAddBinding.bind(view)
        binding.buttonPickMedicine.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_reminderAddFragment_to_medicineSelectFragment)
        }

    }

    private fun onClickListenerDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(parentFragmentManager, "DatePicker")
        var date = ""


        // Setting up the event for when ok is clicked
        datePicker.addOnPositiveButtonClickListener {
            // formatting date in dd-mm-yyyy format.
            date = Date(it).toString().reversed().substringAfter(':').substringAfter(' ').reversed()

            // formatting date in dd-mm-yyyy format.
//            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
//            val date = dateFormatter.format(Date(it))

            binding.AlarmDescription.text = "On $date"
        }
    }

    private fun onClickListenerWeekDays(tv: TextView){
        tv.setOnClickListener {
            if (tv !in daysOfTheWeek){
                daysOfTheWeek.add(tv)
                tv.setBackgroundResource(R.color.blue_farmastock)
                tv.setHintTextColor(Color.WHITE)
            }
            else{
                daysOfTheWeek.remove(tv)
                if(tv == binding.Sunday){
                    tv.setBackgroundColor(Color.WHITE)
                    tv.setHintTextColor(Color.RED)
                }
                else{
                    tv.setBackgroundColor(Color.WHITE)
                    tv.setHintTextColor(Color.LTGRAY)
                }
            }
            var desc = binding.AlarmDescription
            if(daysOfTheWeek.size == 0){
                desc.text = "Set an Alarm"
            }else if(daysOfTheWeek.size == 7){
                desc.text = "Everyday"
            }
            else{
                var aux = "Every "
                for(day in daysOfTheWeek){
                    aux += switchDay(day)
                    if(day != daysOfTheWeek.last()) aux += ", "
                }
                desc.text  = aux
            }
        }
    }

    private fun switchDay(tv: TextView): String{
        var day = ""
        when (tv) {
            binding.Sunday -> day = "Sun"
            binding.Monday -> day = "Mon"
            binding.Tuesday -> day = "Tue"
            binding.Wednesday -> day = "Wed"
            binding.Thursday -> day = "Thu"
            binding.Friday -> day = "Fri"
            binding.Saturday -> day = "Sat"
            else -> { // Note the block
                print("ERROR")
            }
        }
        return  day
    }

    private fun validate():Boolean{
        // Que la fecha del time picker no este en el pasado

        // Que haya una medicina seleccionada

        // Que AlarmDescription.text != "Set an Alarm"
        return true
    }

}

