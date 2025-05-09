package com.example.zequiz.UI.Siswa


import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zequiz.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frame_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentManager = supportFragmentManager
        val homeSiswaFragment = HomeSiswaFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeSiswaFragment::class.java.simpleName)
        if (fragment !is HomeSiswaFragment) {
            Log.d("ZeQuiz", "Fragment Name :" + HomeSiswaFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, homeSiswaFragment, HomeSiswaFragment::class.java.simpleName)
                .commit()
        }
    }


}


