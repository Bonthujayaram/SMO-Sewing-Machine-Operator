package com.example.smo1
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.data.model.Machine
import com.example.smo1.ui.MachineAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MachineDashboardFragment : Fragment() {

    private lateinit var machineRecyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var machineAdapter: MachineAdapter
    private var machineList = mutableListOf<Machine>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_machine_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        machineRecyclerView = view.findViewById(R.id.machineRecyclerView)
        backButton = view.findViewById(R.id.backButton)

        machineRecyclerView.layoutManager = LinearLayoutManager(context)
        machineAdapter = MachineAdapter(requireContext(), machineList) { selectedMachine ->
            Toast.makeText(requireContext(), "Clicked on ${selectedMachine.machinename}", Toast.LENGTH_SHORT).show()
        }
        machineRecyclerView.adapter = machineAdapter

        fetchMachines()  // Fetch real data from backend

        backButton.setOnClickListener {
            machineRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun fetchMachines() {
        RetrofitClient.api.getMachines().enqueue(object : Callback<List<Machine>> {
            override fun onResponse(call: Call<List<Machine>>, response: Response<List<Machine>>) {
                if (response.isSuccessful) {
                    machineList.clear()
                    response.body()?.let { machineList.addAll(it) }
                    machineAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Failed to load machines", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Machine>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
