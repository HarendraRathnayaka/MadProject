package com.example.mynew.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynew.R
import com.example.mynew.models.CompanyModel

class CompanyAdapter(private var companyList: ArrayList<CompanyModel>) :
    RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    private lateinit var cListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        cListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.company_list_item, parent, false)
        return ViewHolder(itemView, cListener)
    }

    fun searchDataList(searchList: List<CompanyModel>) {
        companyList = searchList as ArrayList<CompanyModel>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCompany = companyList[position]
        holder.tvCompName.text = currentCompany.companyName
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvCompName : TextView = itemView.findViewById(R.id.tvcompanyName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}