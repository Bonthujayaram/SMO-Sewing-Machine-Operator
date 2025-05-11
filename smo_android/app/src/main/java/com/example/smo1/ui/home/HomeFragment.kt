package com.example.smo1.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smo1.OrderAdapter
import com.example.smo1.R
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.data.model.GetOrder
import com.example.smo1.ui.ViewOrderStatusActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val ordersList = mutableListOf<GetOrder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        ordersRecyclerView = rootView.findViewById(R.id.ordersRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        orderAdapter = OrderAdapter(ordersList) { selectedOrder ->

            val intent = Intent(requireContext(), ViewOrderStatusActivity::class.java)
            startActivity(intent)
        }

        ordersRecyclerView.adapter = orderAdapter

        fetchOrdersFromBackend() // Fetch orders from backend

        return rootView
    }

    private fun fetchOrdersFromBackend() {
        RetrofitClient.api.getOrders().enqueue(object : Callback<List<GetOrder>> {
            override fun onResponse(call: Call<List<GetOrder>>, response: Response<List<GetOrder>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        ordersList.clear()
                        ordersList.addAll(it)
                        orderAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<GetOrder>>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to load orders", Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onResume() {
        super.onResume()
        fetchOrdersFromBackend() // Refresh orders when returning to HomeFragment
    }
}


