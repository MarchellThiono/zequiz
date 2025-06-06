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

class ListKuisAdapter(
    private var listKuis: List<ListKuisResItem>,
    private val showStatus: Boolean = true,
    private val onItemClick: (ListKuisResItem) -> Unit
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

        // Judul kuis
        holder.tvJudulKuis.text = kuis.namaKuis ?: "Kuis ke-${position + 1}"

        // Topik kuis
        holder.tvTopikKuis.text = kuis.namaTopik ?: "Topik Tidak Tersedia"

        // Format tanggal
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id"))
            val date: Date = inputFormat.parse(kuis.tanggal ?: "") ?: Date()
            holder.tvTanggalKuis.text = outputFormat.format(date)
        } catch (e: Exception) {
            holder.tvTanggalKuis.text = kuis.tanggal ?: "Tanggal Tidak Tersedia"
        }

        // Status pengerjaan (opsional)
        if (showStatus) {
            holder.tvStatusKuis.visibility = View.VISIBLE
            holder.tvStatusKuis.text = "Belum dikerjakan"
        } else {
            holder.tvStatusKuis.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onItemClick(kuis)
        }
    }

    fun updateList(newList: List<ListKuisResItem>) {
        listKuis = newList
        notifyDataSetChanged()
    }
}
