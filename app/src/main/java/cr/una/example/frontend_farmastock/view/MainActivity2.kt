package cr.una.example.frontend_farmastock.view

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import cr.una.example.frontend_farmastock.*
import cr.una.example.frontend_farmastock.Notification
import cr.una.example.frontend_farmastock.databinding.ActivityMain2Binding
import java.util.*


import cr.una.example.frontend_farmastock.adapter.MedicineAdapter

import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity2 : AppCompatActivity()
{
    private lateinit var binding : ActivityMain2Binding
    private var daysOfTheWeek = mutableListOf<TextView>()
    private var specificDate =0
    private var timeSelected = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.Save.setOnClickListener {
            scheduleNotification()
//            finish()
//
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//
/*            if(validate()){*/
                // Repetitivo por dias de la semana a la hora que tenga
                // el time picker
//                if(daysOfTheWeek.size != 0){
//                    finish()
//                    // Initiate successful logged in experience
//                    val intent = Intent(this,MainActivity::class.java)
//                    startActivity(intent)
//
//                }
//                // Por fecha especifica que tenga el date picker
//                // y la hora que tenga time picker
//                else{
//                    finish()
//                    // Initiate successful logged in experience
//                    val intent = Intent(this,MainActivity::class.java)
//                    startActivity(intent)
////                    scheduleNotification()
//                }
            //}
        }



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

//        // MEDICINE SELECT
//        val medicineId: String = arguments?.getString(MedicineAdapter.MEDICINE_ID) ?: "0"
//        if(medicineId!="0") medicineViewModel.getMedicine(medicineId.toLong())
//
//        medicineViewModel.state.observe(viewLifecycleOwner) { state ->
//            with(binding.root) {
//                when (state) {
//                    StateMedicine.Loading -> {}
//                    is StateMedicine.Error -> {}
//                    is StateMedicine.Success -> {
//                        state.medicine?.let {
//                            binding.medicineSelectInfo.text = it.name.toString()
//                        }
//                    }
//                    else -> {}
//                }
//            }
//        }




    }




    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "TEST"
        val message = "TEST"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
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
        val date = specificDate
        val day = 9
        val month = 10
        val year = 2022

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun onClickListenerDatePicker() {

        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                this@MainActivity2,
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

//        val datePicker = DatePickerDialog(this,)
//        datePicker.show()
//        var date = ""
//
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//
//        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//
//            // Display Selected date in textbox
//            //lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
//
//
//            // formatting date in dd-mm-yyyy format.
//
//            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
//            var newDate = LocalDate.parse("$dayOfMonth-$month-$year", formatter)
//
////            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
////            val date = dateFormatter.format(Date())
//            specificDate = newDate
//            binding.AlarmDescription.text = "On $dayOfMonth $month $year"
//
//        }, year, month, day)
//
//        datePicker.show()


        // Setting up the event for when ok is clicked
/*        datePicker.addOnPositiveButtonClickListener {
            // formatting date in dd-mm-yyyy format.
            date = Date().toString().reversed().substringAfter(':').substringAfter(' ').reversed()

            // formatting date in dd-mm-yyyy format.
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormatter.format(Date())
            specificDate = date
            binding.AlarmDescription.text = "On $date"
        }*/
    }

    private fun onClickListenerWeekDays(tv: TextView){
        tv.setOnClickListener {
            //specificDate = null
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

//        if(!specificDate.isEmpty()){
//            var currentTime = Calendar. getInstance().timeInMillis;
//            var selectedTime = getTime()
//            if( selectedTime < currentTime){
//                errors.add("The Alarm can not be created in the past")
//            }
//        }

        if(errors.size != 0){
            for(error in errors){
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
            return false
        }

        return true
    }







}