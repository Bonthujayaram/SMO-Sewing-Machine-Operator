package com.example.smo1.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.smo1.R
import com.example.smo1.data.model.Machine

class MachineAdapter(
    private val context: Context,
    private val machines: List<Machine>,
    private val onClick: (Machine) -> Unit
) : RecyclerView.Adapter<MachineAdapter.MachineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MachineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_machine, parent, false)
        return MachineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MachineViewHolder, position: Int) {
        val machine = machines[position]
        holder.machineName.text = machine.machinename

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked on ${machine.machinename}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = machines.size

    class MachineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val machineName: TextView = view.findViewById(R.id.MachineID)
    }
}
