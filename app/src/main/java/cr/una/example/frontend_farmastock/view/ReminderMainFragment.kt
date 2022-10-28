package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.adapter.ReminderAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineMainBinding
import cr.una.example.frontend_farmastock.databinding.FragmentReminderMainBinding
import cr.una.example.frontend_farmastock.viewmodel.*


class ReminderMainFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentReminderMainBinding? = null
    private val binding get() = _binding!!


    private val reminderViewModel: ReminderViewModel by activityViewModels{
        ReminderViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = ReminderAdapter()
        _binding = FragmentReminderMainBinding.inflate(inflater, container, false)
        binding.reminderList.adapter = adapter
        binding.reminderList.layoutManager = LinearLayoutManager(activity)
        reminderViewModel.findAllReminders()
        reminderViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                // just checking equality because Loading is a -singleton object instance-
                StateReminder.Loading -> {
                    // TODO: If you need do something in loading
                }
                // Error and Success are both -classes- so we need to check their type with 'is'
                is StateReminder.Error -> {
                    // TODO: If you need do something in error
                }
                is StateReminder.SuccessList -> {
                    state.reminderList?.let { adapter.setReminderList(it) }
                }
                else -> {
                    // TODO: Not state loaded
                }
            }
        }
        // NUEVO
        binding.SearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReminderMainBinding.bind(view)
        binding.buttonMainAddReminder.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_reminderMainFragment_to_reminderAddFragment)
        }
    }
}