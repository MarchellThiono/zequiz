package com.example.zequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zequiz.R
import com.example.zequiz.model.Soal

class SoalAdapter(
    private val listSoal: MutableList<Soal>,
    private val onDeleteClick: (Soal) -> Unit
) : RecyclerView.Adapter<SoalAdapter.SoalViewHolder>() {

    inner class SoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pertanyaan: TextView = itemView.findViewById(R.id.pertanyaan)
        val opsiA: TextView = itemView.findViewById(R.id.opsiA)
        val opsiB: TextView = itemView.findViewById(R.id.opsiB)
        val opsiC: TextView = itemView.findViewById(R.id.opsiC)
        val opsiD: TextView = itemView.findViewById(R.id.opsiD)
        val gambarSoal: ImageView = itemView.findViewById(R.id.gambar_soal)
        val iconTrash: ImageButton = itemView.findViewById(R.id.iconTrash)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bank_soal, parent, false)
        return SoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoalViewHolder, position: Int) {
        val soal = listSoal[position]

        holder.pertanyaan.text = soal.pertanyaan
        holder.opsiA.text = soal.pilihanA
        holder.opsiB.text = soal.pilihanB
        holder.opsiC.text = soal.pilihanC
        holder.opsiD.text = soal.pilihanD

        if (soal.gambar.isNotEmpty()) {
            holder.gambarSoal.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load("http://IP-ADDRESS-API/uploads/${soal.gambar}") // GANTI sesuai link servermu
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.gambarSoal)
        } else {
            holder.gambarSoal.visibility = View.GONE
        }

        holder.iconTrash.setOnClickListener {
            onDeleteClick(soal)
        }
    }

    override fun getItemCount(): Int = listSoal.size

    fun addSoal(soal: Soal) {
        listSoal.add(0, soal)
        notifyItemInserted(0)
    }

    fun removeSoal(soal: Soal) {
        val position = listSoal.indexOf(soal)
        if (position != -1) {
            listSoal.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
