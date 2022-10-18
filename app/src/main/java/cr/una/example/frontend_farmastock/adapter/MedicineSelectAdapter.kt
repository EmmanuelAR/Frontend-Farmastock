package cr.una.example.frontend_farmastock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cr.una.example.frontend_farmastock.R
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineMainBinding
import cr.una.example.frontend_farmastock.databinding.MedicineItemBinding
import cr.una.example.frontend_farmastock.model.MedicineResponse

class MedicineSelectAdapter : RecyclerView.Adapter<MedicineSelectAdapter.MainViewHolder>(),
    Filterable {

    var medicineFilterList = mutableListOf<MedicineResponse>()
    private var medicineList = mutableListOf<MedicineResponse>()

    fun setVehicleList(vehicleResponseList: List<MedicineResponse>) {
        this.medicineList.clear()
        this.medicineList.addAll(vehicleResponseList.toMutableList())
        this.medicineFilterList.addAll(medicineList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MedicineItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val medicine = medicineFilterList[position]
        holder.binding.name.text = medicine.name.toString()
        holder.binding.cantidad.text = medicine.quantity.toString()
        holder.binding.dosis.text = medicine.dose.toString()

        holder.itemView.setOnClickListener() {
            val bundle = bundleOf(MEDICINE_ID to medicineFilterList[position].id.toString())

            holder.itemView.findNavController().navigate(
                R.id.action_medicineSelectFragment_to_medicineDetailsFragment, bundle
            )
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (!charSearch.isEmpty()) {
                    val resultList = mutableListOf<MedicineResponse>()
                    for (row in medicineList) {
                        if (row.id.toString().lowercase()?.contains(charSearch.lowercase()) == true ||
                            row.name?.lowercase()?.contains(charSearch.lowercase()) == true )
                        {
                            resultList.add(row)
                        }
                    }
                    medicineFilterList= resultList
                }else{
                    medicineFilterList = medicineList
                }
                val filterResults = FilterResults()
                filterResults.values = medicineFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                medicineFilterList = results?.values as MutableList<MedicineResponse>
                notifyDataSetChanged()
            }

        }
    }

    override fun getItemCount(): Int {
        return medicineFilterList.size
    }

    companion object {
        const val MEDICINE_ID = "id"
    }
    class MainViewHolder(
        val binding: MedicineItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
