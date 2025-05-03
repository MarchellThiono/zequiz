package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.model.ListKuisResItem
import java.text.SimpleDateFormat
import java.util.*

class ListKuisGuruAdapter(
    private var listKuis: List<ListKuisResItem>,
    private val onItemClick: (ListKuisResItem) -> Unit
) : RecyclerView.Adapter<ListKuisGuruAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaKuis: TextView = itemView.findViewById(R.id.tv_name)
        val tvTopik: TextView = itemView.findViewById(R.id.tv_topik)
        val tvTanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
        val tvJumlahSoal: TextView = itemView.findViewById(R.id.tv_status)  // Bisa diubah ID-nya sesuai layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_soal, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listKuis.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kuis = listKuis[position]

        holder.tvNamaKuis.text = kuis.namaKuis ?: "Kuis ke-${position + 1}"
        holder.tvTopik.text = kuis.namaTopik ?: "Topik Tidak Tersedia"

        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
            val date: Date = inputFormat.parse(kuis.tanggal ?: "") ?: Date()
            holder.tvTanggal.text = outputFormat.format(date)
        } catch (e: Exception) {
            holder.tvTanggal.text = kuis.tanggal ?: "Tanggal Tidak Tersedia"
        }

        holder.tvJumlahSoal.text = "${kuis.jumlahSoal ?: 0} soal • ${kuis.timer} detik"

        holder.itemView.setOnClickListener {
            onItemClick(kuis)
        }
    }

    fun updateList(newList: List<ListKuisResItem>) {
        listKuis = newList
        notifyDataSetChanged()
    }
}
