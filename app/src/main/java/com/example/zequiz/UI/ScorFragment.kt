import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.zequiz.R
import com.example.zequiz.UI.HomeSiswaFragment

class ScorFragment : Fragment(), View.OnClickListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scor,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnKembali : Button = view.findViewById(R.id.btn_Kembali)
        btnKembali.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val fragmentManager = parentFragmentManager
        when(v?.id){
            R.id.btn_Kembali ->{
                val homeSiswaFragment = HomeSiswaFragment()
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container,homeSiswaFragment,HomeSiswaFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }

    }
}
