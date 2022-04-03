package com.example.wrg.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wrg.R
import com.example.wrg.model.Employee

class Adapter(): RecyclerView.Adapter<Adapter.AdapterVH>(), Filterable {

    var employeeList: List<Employee> = ArrayList()
    var employeeListFiltered: List<Employee> = ArrayList()
    lateinit var context: Context
    lateinit var itemClickListener: ItemClickListener

    fun addData(list: List<Employee>, context: Context, itemClickListener: ItemClickListener) {
        employeeList = list
        employeeListFiltered = employeeList
        this.context = context
        this.itemClickListener = itemClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVH {
        return AdapterVH(LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: Adapter.AdapterVH, position: Int) {
        val employee = employeeListFiltered[position]

        holder.tvName.text = employee.name
        holder.tvCompanyName.text = employee.company?.companyName

        Glide.with(context)
            .load(employee.profile_image)
            .placeholder(R.color.placeholder)
            .into(holder.imgPerson)

        holder.cdView.setOnClickListener {
            Toast.makeText(context, employee.name, Toast.LENGTH_SHORT).show()
            itemClickListener.onItemClick(employee.id)
        }
    }

    override fun getItemCount() = employeeListFiltered.size

    inner class AdapterVH(view: View): RecyclerView.ViewHolder(view) {
        val imgPerson: ImageView = view.findViewById(R.id.imgPerson)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvCompanyName: TextView = view.findViewById(R.id.tvCompanyName)
        val cdView: CardView = view.findViewById(R.id.cdView)
    }
    
    interface ItemClickListener {
        fun onItemClick(id: Int?)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) employeeListFiltered = employeeList else {
                    val filteredList = ArrayList<Employee>()
                    employeeList
                        .filter {
                            (it.name?.contains(constraint!!) == true) or (it.email?.contains(constraint!!) == true)
                        }
                        .forEach { filteredList.add(it) }
                    employeeListFiltered = filteredList

                }
                return FilterResults().apply { values = employeeListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                employeeListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Employee>
                notifyDataSetChanged()
            }
        }
    }


}