package com.customexoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //activity에서 직접 ExoFragment 생성하지않고 반환받아 사용
        supportFragmentManager.beginTransaction().replace(R.id.frame, ExoFragment.newInstance())
            .commit()
    }
}