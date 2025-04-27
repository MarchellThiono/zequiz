package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.model.Kuis
import java.text.SimpleDateFormat
import java.util.*

class ListKuisAdapter(
    private val listKuis: List<Kuis>,
    private val showStatus: Boolean = true,  // default true
    private val onItemClick: (Kuis) -> Unit
) : RecyclerView.Adapter<ListKuisAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJudulKuis: TextView = itemView.findViewById(R.id.tv_name)
        val tvTopikKuis: TextView = itemView.findViewById(R.id.tv_topik)
        val tvTanggalKuis: TextView = itemView.findViewById(R.id.tv_tanggal)
        val tvStatusKuis: TextView = itemView.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_soal, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listKuis.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kuis = listKuis[position]

        // 🏷️ Tampilkan Kuis ke-x
        holder.tvJudulKuis.text = "Kuis ke-${position + 1}"

        // 🏷️ Tampilkan Topik
        holder.tvTopikKuis.text = kuis.topikNama ?: "Topik Tidak Tersedia"

        // 🏷️ Format dan Tampilkan Tanggal
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))

            val date: Date = inputFormat.parse(kuis.tanggal) ?: Date()
            holder.tvTanggalKuis.text = outputFormat.format(date)
        } catch (e: Exception) {
            holder.tvTanggalKuis.text = kuis.tanggal
        }

        // 🏷️ Tampilkan Status kalau showStatus == true
        if (showStatus) {
            holder.tvStatusKuis.visibility = View.VISIBLE
            holder.tvStatusKuis.text = "Belum dikerjakan" // atau update sesuai API nanti
        } else {
            holder.tvStatusKuis.visibility = View.GONE
        }

        // 🏷️ Set klik listener
        holder.itemView.setOnClickListener {
            onItemClick(kuis)
        }
    }
}
