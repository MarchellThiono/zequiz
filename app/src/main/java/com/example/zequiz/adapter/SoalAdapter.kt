//class SoalAdapter(
//    private var listSoal: MutableList<Soal>,
//    private val onDeleteClick: (Soal) -> Unit
//) : RecyclerView.Adapter<SoalAdapter.SoalViewHolder>() {
//
//    inner class SoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val pertanyaan: TextView = itemView.findViewById(R.id.pertanyaan)
//        val opsiA: TextView = itemView.findViewById(R.id.opsiA)
//        val opsiB: TextView = itemView.findViewById(R.id.opsiB)
//        val opsiC: TextView = itemView.findViewById(R.id.opsiC)
//        val opsiD: TextView = itemView.findViewById(R.id.opsiD)
//        val gambarSoal: ImageView = itemView.findViewById(R.id.gambar_soal)
//        val iconTrash: ImageButton = itemView.findViewById(R.id.iconTrash)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoalViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bank_soal, parent, false)
//        return SoalViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SoalViewHolder, position: Int) {
//        val soal = listSoal[position]
//
//        holder.pertanyaan.text = soal.pertanyaan
//        holder.opsiA.text = soal.opsiA
//        holder.opsiB.text = soal.opsiB
//        holder.opsiC.text = soal.opsiC
//        holder.opsiD.text = soal.opsiD
//
//        // Kalau ada gambar
//        if (!soal.gambarUrl.isNullOrEmpty()) {
//            holder.gambarSoal.visibility = View.VISIBLE
//            Glide.with(holder.itemView.context)
//                .load(soal.gambarUrl)
//                .into(holder.gambarSoal)
//        } else {
//            holder.gambarSoal.visibility = View.GONE
//        }
//
//        holder.iconTrash.setOnClickListener {
//            onDeleteClick(soal)
//        }
//    }
//
//    override fun getItemCount(): Int = listSoal.size
//}
