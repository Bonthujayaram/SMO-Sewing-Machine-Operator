package com.example.smo1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.ui.WorkerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkerDashboardFragment : Fragment() {

    private lateinit var workerRecyclerView: RecyclerView
    private lateinit var workerAdapter: WorkerAdapter
    private val workerNames = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_worker_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workerRecyclerView = view.findViewById(R.id.workerRecyclerView)
        workerAdapter = WorkerAdapter(requireContext(), workerNames) // Pass context
        workerRecyclerView.layoutManager = LinearLayoutManager(context)
        workerRecyclerView.adapter = workerAdapter

        fetchWorkerNamesFromBackend()
    }

    private fun fetchWorkerNamesFromBackend() {
        RetrofitClient.api.getWorkerNames().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful && response.body() != null) {
                    workerNames.clear()
                    workerNames.addAll(response.body()!!)
                    workerAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
