package com.example.mynew.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynew.R
import com.example.mynew.activities.DetailActivity
import com.example.mynew.models.DataClass


class MyHomeAdapter(private val context: Context, private var dataList: List<DataClass>) : RecyclerView.Adapter<MyHomeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.jobsrecycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].dataImage).into(holder.recImage)

        holder.recTitle.text = dataList[position].dataTitle as CharSequence?
        holder.recDesc.text = dataList[position].dataDesc
        holder.recPriority.text = dataList[position].dataPriority
        holder.recCard.setOnClickListener {

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Image",dataList[holder.adapterPosition].dataImage)
            intent.putExtra("Description",dataList[holder.adapterPosition].dataDesc)
            intent.putExtra("Title",dataList[holder.adapterPosition].dataTitle)
            intent.putExtra("Priority",dataList[holder.adapterPosition].dataPriority)
            intent.putExtra("Key" ,dataList[holder.adapterPosition].dataKey)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchDataList(searchList: List<DataClass>){
        dataList = searchList
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recImage: ImageView = itemView.findViewById(R.id.recImage)
        var recTitle: TextView = itemView.findViewById(R.id.recTitle)
        var recDesc: TextView = itemView.findViewById(R.id.recDesc)
        var recPriority: TextView = itemView.findViewById(R.id.recPriority)
        var recCard: CardView = itemView.findViewById(R.id.recCard)
    }
}

