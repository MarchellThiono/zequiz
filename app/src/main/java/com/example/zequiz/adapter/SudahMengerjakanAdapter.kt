package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.model.SiswaSudah

class SudahMengerjakanAdapter(private var listSiswa: List<SiswaSudah>) :
    RecyclerView.Adapter<SudahMengerjakanAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama: TextView = itemView.findViewById(R.id.txtNamaSiswa)
        val txtSkor: TextView = itemView.findViewById(R.id.txtSkor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_siswa_sudah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val siswa = listSiswa[position]
        holder.txtNama.text = siswa.nama
        holder.txtSkor.text = "Score: ${siswa.skor}"
    }

    override fun getItemCount(): Int {
        return listSiswa.size
    }

    fun setData(newList: List<SiswaSudah>) {
        listSiswa = newList
        notifyDataSetChanged()
    }
}
