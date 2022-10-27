package cr.una.example.frontend_farmastock.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.MedicineAdapter
import cr.una.example.frontend_farmastock.adapter.ReminderAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineDetailsBinding
import cr.una.example.frontend_farmastock.databinding.FragmentReminderDetailsBinding
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModel
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import cr.una.example.frontend_farmastock.viewmodel.StateReminder


class ReminderDetailsFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentReminderDetailsBinding? = null
    private val binding get() = _binding!!

    // Shared view model
    private val reminderViewModel: ReminderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReminderDetailsBinding.inflate(inflater, container, false)

        val reminderId: String = arguments?.getString(ReminderAdapter.REMINDER_ID) ?: "0"

        reminderViewModel.getReminder(reminderId.toLong())
        // Observer method to bind data of task into text views
        reminderViewModel.state.observe(viewLifecycleOwner) { state ->
            // this lets us avoid repeating 'binding.frameNews' before everything
            with(binding.root) {
                when (state) {
                    // just checking equality because Loading is a -singleton object instance-
                    StateReminder.Loading -> {
                        // TODO: If you need do something in loading
                    }
                    // Error and Success are both -classes- so we need to check their type with 'is'
                    is StateReminder.Error -> {
                        // TODO: If you need do something in error
                    }
                    is StateReminder.Success -> {
                        state.reminderResponse?.let {

                            val date = (it.createDate.toString()).reversed().substringAfter(':').reversed()
                            binding.txtdatetime.text = date
                            binding.txtdescripcionReminder.text = it.description
                            binding.txtMedicineName.text = it.medicine?.name
                            binding.txtDose.text = it.medicine?.dose.toString()
                        }
                    }
                    else -> {
                        // TODO: Not state loaded
                    }
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            dialogBuilder.setMessage("Are you sure?")
                // if the dialog is cancelable
                .setCancelable(true)
                .setPositiveButton("YES") { dialog, _ ->
                    reminderViewModel.deleteReminderById(reminderId.toLong())

                    findNavController().navigate(R.id.reminderMainFragment)

                    dialog.dismiss()

                }
                .setNegativeButton("NO") { dialog, _ ->
                    dialog.dismiss()

                }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Reminder")
            alert.show()
        }

        binding.btnUpdate.setOnClickListener {
            val bundle = bundleOf(ReminderAdapter.REMINDER_ID to id)
            //findNavController().navigate(R.id.action_medicineDetailsFragment_to_medicineUpdateFragment, bundle)
        }
        // Inflate the layout for this fragment
        return binding.root

    }

}