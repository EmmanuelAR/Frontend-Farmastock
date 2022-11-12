package cr.una.example.frontend_farmastock.view


import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter
import cr.una.example.frontend_farmastock.adapter.MedicineSelectAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentReminderAddBinding
import cr.una.example.frontend_farmastock.model.MedicineResponse
import cr.una.example.frontend_farmastock.utils.channelID
import cr.una.example.frontend_farmastock.utils.messageExtra
import cr.una.example.frontend_farmastock.utils.notificationID
import cr.una.example.frontend_farmastock.utils.titleExtra
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModel
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import cr.una.example.frontend_farmastock.utils.Notification


class ReminderAddFragment : Fragment() {
    private var _binding: FragmentReminderAddBinding? = null
    private val binding get() = _binding!!
    private var specificDate =""

    private val reminderViewModel: ReminderViewModel by activityViewModels()
    private val medicineViewModel: MedicineViewModel by activityViewModels{
        MedicineViewModelFactory()
    }

    private lateinit var notificationTitle : String
    private lateinit var messageMedicineName : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderAddBinding.inflate(inflater, container, false)


        if(!(activity as MainActivity)!!.specificDate.isEmpty()){
            specificDate = (activity as MainActivity)!!.specificDate
            binding.AlarmDescription.text = "On $specificDate"
        }

        // DATE PICKER
        binding.calendarPicker.setOnClickListener {
            onClickListenerDatePicker()
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
                            notificationTitle = "Remember to take your pill"
                            messageMedicineName= "Medicine: ${it.name.toString()}"
                        }
                    }
                    else -> {}
                }
            }
        }

        createNotificationChannel()
        binding.Save.setOnClickListener {
            if(validate()) scheduleSpecificDateNotification()
        }
        return binding.root
    }

    private fun scheduleSpecificDateNotification() {

        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Notification::class.java)
        intent.action = "MyBroadcastReceiverAction"
        intent.putExtra(titleExtra, notificationTitle)
        intent.putExtra(messageExtra, messageMedicineName)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val time = getTime()
        val alarmTimeAtUTC: Long = time
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(alarmTimeAtUTC, pendingIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTimeAtUTC,
                pendingIntent
            )
        }
        Toast.makeText(activity,"Reminder created successfully.", Toast.LENGTH_SHORT).show();
    }

    private fun getTime(): Long {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val date = specificDate.split("-")
        val day = Integer.parseInt(date[0])
        val month = Integer.parseInt(date[1])
        val year = Integer.parseInt(date[2])

        val calendar = Calendar.getInstance()
        calendar.set(year, month-1, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = activity!!.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderAddBinding.bind(view)
        binding.buttonPickMedicine.setOnClickListener{
            (activity as MainActivity)!!.specificDate = specificDate
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
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormatter.format(Date(it))
            specificDate = date

            binding.AlarmDescription.text = "On $date"
        }
    }

    private fun validate():Boolean{
        var errors: MutableList<String> = mutableListOf()

        // Otras validaciones?

        if(binding.AlarmDescription.text == "Select a Date"){
            errors.add("You must select a date first")
        }

        if(binding.medicineSelectInfo.text == "No medicine selected"){
            errors.add("You must select a Medicine")
        }

        if(!specificDate.isEmpty()){
            var currentTime = Calendar. getInstance().timeInMillis;
            var selectedTime = getTime()
            if( selectedTime < currentTime){
                errors.add("The Alarm can not be created in the past")
            }
        }

        if(errors.size != 0){
            for(error in errors){
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
            }
            return false
        }

        return true
    }

}

