package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.model.SiswaBelum

class BelumMengerjakanAdapter(private var listSiswa: List<SiswaBelum>) :
    RecyclerView.Adapter<BelumMengerjakanAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama: TextView = itemView.findViewById(R.id.txtNamaSiswa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_siswa_belum, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val siswa = listSiswa[position]
        holder.txtNama.text = siswa.nama
    }

    override fun getItemCount(): Int {
        return listSiswa.size
    }

    fun setData(newList: List<SiswaBelum>) {
        listSiswa = newList
        notifyDataSetChanged()
    }
}
