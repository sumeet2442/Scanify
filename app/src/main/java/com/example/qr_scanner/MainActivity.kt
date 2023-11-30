package com.example.qr_scanner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(Runnable {
            val i = Intent(this@MainActivity, QR_Scanpage::class.java)
            startActivity(i)
        }, 2800)
    }
}