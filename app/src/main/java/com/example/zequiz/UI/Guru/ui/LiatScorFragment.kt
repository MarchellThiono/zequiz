package com.example.zequiz.UI.liatscor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zequiz.adapter.BelumMengerjakanAdapter
import com.example.zequiz.adapter.SudahMengerjakanAdapter
import com.example.zequiz.databinding.FragmentLiatScorBinding
import com.example.zequiz.model.SiswaBelum
import com.example.zequiz.model.SiswaSudah
import com.example.zequiz.dataApi.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiatScorFragment : Fragment() {

    private var _binding: FragmentLiatScorBinding? = null
    private val binding get() = _binding!!

    private lateinit var belumAdapter: BelumMengerjakanAdapter
    private lateinit var sudahAdapter: SudahMengerjakanAdapter

    private lateinit var apiService: ApiService
    private var kuisId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiatScorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kuisId = arguments?.getLong("kuisId") ?: 0L

        apiService = ApiService.getInstance()

        //  Tambahkan ini supaya TextView kuis ke-... ikut tampil
        binding.kuis.text = "Kuis ke-${kuisId}"

        setupRecyclerViews()
        loadData()
    }


    private fun setupRecyclerViews() {
        belumAdapter = BelumMengerjakanAdapter(emptyList())
        binding.recyclerBelumMengerjakan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = belumAdapter
        }

        sudahAdapter = SudahMengerjakanAdapter(emptyList())
        binding.recyclerSudahMengerjakan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sudahAdapter
        }
    }

    private fun loadData() {
        apiService.getSiswaBelum(kuisId).enqueue(object : Callback<List<SiswaBelum>> {
            override fun onResponse(call: Call<List<SiswaBelum>>, response: Response<List<SiswaBelum>>) {
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        belumAdapter.setData(list)
                    }
                }
            }

            override fun onFailure(call: Call<List<SiswaBelum>>, t: Throwable) {
                // Handle error
            }
        })

        apiService.getSiswaSudah(kuisId).enqueue(object : Callback<List<SiswaSudah>> {
            override fun onResponse(call: Call<List<SiswaSudah>>, response: Response<List<SiswaSudah>>) {
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        sudahAdapter.setData(list)
                    }
                }
            }

            override fun onFailure(call: Call<List<SiswaSudah>>, t: Throwable) {
                // Handle error
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
