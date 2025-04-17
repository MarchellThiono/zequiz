package com.example.zequiz.UI
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.adapter.ListTopikAdapter
import com.example.zequiz.model.Topik


class HomeSiswaFragment : Fragment(), View.OnClickListener{
    private lateinit var  rvTopik: RecyclerView
    private lateinit var list: ArrayList<Topik>
    private lateinit var emptyStateText: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_siswa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTopik = view.findViewById(R.id.tv_topik)
        emptyStateText = view.findViewById(R.id.tv_empty_state)

        rvTopik.setHasFixedSize(true)
        rvTopik.layoutManager = LinearLayoutManager(requireContext())

        list = ArrayList()

        // Simulasi: Ambil data dari server, kosong dulu
        fetchTopikFromServer()
    }

    private fun fetchTopikFromServer() {
        // Simulasi backend kosong
        list.clear() // data kosong, seolah belum ada soal dari guru

        if (list.isEmpty()) {
            rvTopik.visibility = View.GONE
            emptyStateText.visibility = View.VISIBLE
        } else {
            rvTopik.visibility = View.VISIBLE
            emptyStateText.visibility = View.GONE

            val adapter = ListTopikAdapter(list) { topik ->  
                
            }
            rvTopik.adapter = adapter
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}