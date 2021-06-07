package com.example.pig

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class ScoreLogAdapter( private var scoreLogList: List<ScoreLogItem> ) :
        RecyclerView.Adapter<ScoreLogAdapter.MyViewHolder>(){
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var item : TextView = view.findViewById(R.id.scoreLogItemText)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.score_log_item, parent, false)
        return MyViewHolder( itemView )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val scoreLogItem = scoreLogList[position]
        holder.item.text = scoreLogItem.toString()
        // Change the color of string based on something...

        if (scoreLogItem.winner == "Computer"){
            holder.item.setTextColor(Color.RED)
        }

    }

    override fun getItemCount(): Int {
        return scoreLogList.size
    }
}