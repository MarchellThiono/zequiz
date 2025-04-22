package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.model.Topik
import java.text.SimpleDateFormat
import java.util.*

class ListTopikAdapter(
    private val listTopik: List<Topik>,
    private val onItemClick: (Topik) -> Unit
) : RecyclerView.Adapter<ListTopikAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_soal, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listTopik.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val kuis = listTopik[position]
        holder.tvJumlah.text = "${kuis.jumlah} Soal"
        holder.tvTopik.text = kuis.topik
        holder.tvStatus.text = kuis.status

        // Format tanggal dari yyyy-MM-dd ke dd MMMM yyyy
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id"))

            val date: Date = inputFormat.parse(kuis.tanggal) ?: Date()
            holder.tvTanggal.text = outputFormat.format(date)
        } catch (e: Exception) {
            holder.tvTanggal.text = kuis.tanggal // fallback kalau parsing gagal
        }

        // Pasang event klik ke item
        holder.itemView.setOnClickListener {
            onItemClick(kuis)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvJumlah: TextView = itemView.findViewById(R.id.tv_name)
        val tvTopik: TextView = itemView.findViewById(R.id.tv_topik)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
        val tvTanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
    }
}
