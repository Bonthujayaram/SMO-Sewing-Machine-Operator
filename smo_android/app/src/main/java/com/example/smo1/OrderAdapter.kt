package com.example.smo1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smo1.data.model.GetOrder

class OrderAdapter(
    private val orders: List<GetOrder>,
    private val onItemClick: (GetOrder) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderText: TextView = itemView.findViewById(R.id.orderText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderText.text = "Order #${order.orderid} - Style: ${order.style}"
        holder.itemView.setOnClickListener { onItemClick(order) }
    }

    override fun getItemCount() = orders.size
}
