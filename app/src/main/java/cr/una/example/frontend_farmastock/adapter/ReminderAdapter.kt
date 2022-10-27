package cr.una.example.frontend_farmastock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.ReminderItemBinding
import cr.una.example.frontend_farmastock.model.ReminderResponse

class ReminderAdapter: RecyclerView.Adapter<ReminderAdapter.MainViewHolder>(),
    Filterable {
    var reminderFilterList = mutableListOf<ReminderResponse>()
    private var reminderList = mutableListOf<ReminderResponse>()

    fun setReminderList(reminderResponseList: List<ReminderResponse>) {
        this.reminderList.clear()
        this.reminderList.addAll(reminderResponseList.toMutableList())
        this.reminderFilterList.addAll(reminderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReminderItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val reminder = reminderFilterList[position]
        val date = (reminder.createDate.toString()).reversed().substringAfter(':').reversed()
        holder.binding.DescriptionValue.text = reminder.description
        holder.binding.DateTimeValue.text = date
        holder.binding.MedicineValue.text = reminder.medicine?.name

        holder.itemView.setOnClickListener() {
            val bundle = bundleOf(REMINDER_ID to reminderFilterList[position].id.toString())

//            holder.itemView.findNavController().navigate(
////                R.id.action_reminderMainFragment_to_reminderDetailsFragment, bundle
//            )
        }

    }

    // NUEVO
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (!charSearch.isEmpty()) {
                    val resultList = mutableListOf<ReminderResponse>()
                    for (row in reminderList) {
                        if (row.description?.lowercase()?.contains(charSearch.lowercase()) == true || row.createDate?.toString()?.contains(charSearch) == true)
                        {
                            resultList.add(row)
                        }
                    }
                    reminderFilterList= resultList
                }else{
                    reminderFilterList = reminderList
                }
                val filterResults = FilterResults()
                filterResults.values = reminderFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                reminderFilterList = results?.values as MutableList<ReminderResponse>
                notifyDataSetChanged()
            }

        }
    }
    //

    override fun getItemCount(): Int {
        return reminderFilterList.size
    }

    companion object {
        const val REMINDER_ID = "reminder_id"
    }
    class MainViewHolder(
        val binding: ReminderItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}