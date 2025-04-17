import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.zequiz.R


class Soal2Fragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_soal2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnlanjut: Button = view.findViewById(R.id.btn_Berikutnya)
        btnlanjut.setOnClickListener(this)
        val btnkembali: Button = view.findViewById(R.id.btn_Kembali)
        btnkembali.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_Berikutnya) {
            val soal3Fragment = Soal3Fragment()

            val fragmentManager = parentFragmentManager
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, soal3Fragment, Soal3Fragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        if (v?.id == R.id.btn_Kembali) {
            val soal1Fragment = Soal1Fragment()

            val fragmentManager = parentFragmentManager
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, soal1Fragment, soal1Fragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }

        }
    }
}

