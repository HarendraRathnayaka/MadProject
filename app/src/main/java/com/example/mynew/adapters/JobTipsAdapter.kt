package com.example.mynew.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynew.R
import com.example.mynew.activities.DeleteJobTipsActivity
import com.example.mynew.activities.DetailActivity
import com.example.mynew.models.JobTipsModel

class JobTipsAdapter (private val context: Context, private var JobTipsList: List<JobTipsModel>) : RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Glide.with(context).load(locationList[position].locationImage)
        //  .into(holder.recImage)
        holder.recTitle.text = JobTipsList[position].JobTitle
        holder.recDesc.text = JobTipsList[position].JobTipsName
        holder.recPriority.text = JobTipsList[position].Description
        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            //intent.putExtra("Image", locationList[holder.adapterPosition].locationImage)
            intent.putExtra("Description", JobTipsList[holder.adapterPosition].JobTitle)
            intent.putExtra("Title", JobTipsList[holder.adapterPosition].JobTipsName)
            intent.putExtra("Priority", JobTipsList[holder.adapterPosition].Description)
            context.startActivity(intent)
        }
        holder.recUpdate.setOnClickListener{
         //   val intent1 = Intent(context, UpdateActivity::class.java)
        //    context.startActivity(intent1)
        }

        holder.recDelete.setOnClickListener{
           val intent2 = Intent(context,DeleteJobTipsActivity ::class.java)
            context.startActivity(intent2)
        }


    }
    override fun getItemCount(): Int {
        return JobTipsList.size
    }
    fun searchDataList(searchList: List<JobTipsModel>) {
        JobTipsList = searchList
        notifyDataSetChanged()
    }


}
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recImage: ImageView
    var recTitle: TextView
    var recDesc: TextView
    var recPriority: TextView
    var recCard: CardView
    var recUpdate: Button
    var recDelete: Button
    init {
        recImage = itemView.findViewById(R.id.recImage)
        recTitle = itemView.findViewById(R.id.recTitle)
        recDesc = itemView.findViewById(R.id.recDesc)
        recPriority = itemView.findViewById(R.id.recPriority)
        recCard = itemView.findViewById(R.id.recCard)
        recUpdate = itemView.findViewById(R.id.recUpdate)
        recDelete = itemView.findViewById(R.id.recDelete)
    }


}