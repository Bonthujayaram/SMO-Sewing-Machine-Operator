package com.example.smo1.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.smo1.R


class WorkerAdapter(private val context: Context, private val workerNames: List<String>) :
    RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_worker, parent, false)
        return WorkerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val workerName = workerNames[position]
        holder.workerName.text = workerName

        // Handle item click
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked on $workerName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = workerNames.size

    class WorkerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val workerName: TextView = view.findViewById(R.id.workerName)
    }
}
