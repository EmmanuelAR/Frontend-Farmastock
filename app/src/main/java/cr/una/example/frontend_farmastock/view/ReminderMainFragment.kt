package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineMainBinding
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModel
import cr.una.example.frontend_farmastock.viewmodel.ReminderViewModelFactory


class ReminderMainFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: ReminderMainFragment? = null
    private val binding get() = _binding!!

    private val reminderViewModel: ReminderViewModel by activityViewModels {
        ReminderViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder_main, container, false)
    }


}