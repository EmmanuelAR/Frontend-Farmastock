package cr.una.example.frontend_farmastock.view


//----

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import cr.una.example.frontend_farmastock.*
import cr.una.example.frontend_farmastock.Notification
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentReminderAddBinding
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModel
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import java.util.*


class ReminderAddFragment : Fragment() {
    // Definition of the binding variable
    private var _binding: FragmentReminderAddBinding? = null
    private val binding get() = _binding!!
    private var daysOfTheWeek = mutableListOf<TextView>()
    private var specificDate =""
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
        createNotificationChannel()

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
            scheduleNotification()
            if(validate()){
                // Repetitivo por dias de la semana a la hora que tenga
                // el time picker
                if(daysOfTheWeek.size != 0){

                }
                // Por fecha especifica que tenga el date picker
                // y la hora que tenga time picker
                else{
//                    scheduleNotification()
                }
            }
        }

        return binding.root
    }

    private fun scheduleNotification() {
        val intent = Intent(context!!.applicationContext , Notification::class.java)
        val title = "test"
        val message = "tes"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context!!.applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(context!!.applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context!!.applicationContext)

        AlertDialog.Builder(activity)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
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
        val notificationManager = context!!.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
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
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormatter.format(Date(it))
            specificDate = date
            binding.AlarmDescription.text = "On $date"
        }
    }

    private fun onClickListenerWeekDays(tv: TextView){
        tv.setOnClickListener {
            specificDate = ""
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
        var errors: MutableList<String> = mutableListOf()

        // Otras validaciones?

        if(binding.AlarmDescription.text == "Set an Alarm"){
            errors.add("Select a at least 1 day or set a date first")
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

    private val broadCastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent)
        {
            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .build()

            val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)
        }
    }

}

