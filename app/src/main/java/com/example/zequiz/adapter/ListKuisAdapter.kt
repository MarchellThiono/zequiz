package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.model.Kuis
import com.example.zequiz.model.ResponseAllKuisItem
import java.text.SimpleDateFormat
import java.util.*


class ListKuisAdapter(
    private var listKuis: List<ResponseAllKuisItem>,  // Menggunakan model ResponseAllKuisItem
    private val showStatus: Boolean = true,
    private val onItemClick: (ResponseAllKuisItem) -> Unit
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

        // Menampilkan judul kuis
        holder.tvJudulKuis.text = "Kuis ke-${position + 1}"

        // Menampilkan nama topik
        holder.tvTopikKuis.text = kuis.topik?.nama ?: "Topik Tidak Tersedia"

        // Mengonversi tanggal dengan format yang sesuai
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
            val date: Date = inputFormat.parse(kuis.tanggal ?: "") ?: Date()
            holder.tvTanggalKuis.text = outputFormat.format(date)
        } catch (e: Exception) {
            holder.tvTanggalKuis.text = kuis.tanggal ?: "Tanggal Tidak Tersedia"
        }

        // Tampilkan status jika diperlukan
        if (showStatus) {
            holder.tvStatusKuis.visibility = View.VISIBLE
            holder.tvStatusKuis.text = "Belum dikerjakan"
        } else {
            holder.tvStatusKuis.visibility = View.GONE
        }

        // Set onClick listener untuk item kuis
        holder.itemView.setOnClickListener {
            onItemClick(kuis)
        }
    }

    fun updateList(newList: List<ResponseAllKuisItem>) {
        listKuis = newList
        notifyDataSetChanged()
    }
}
