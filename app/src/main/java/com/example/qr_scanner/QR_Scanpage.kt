package com.example.qr_scanner
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qr_scanner.databinding.ActivityQrScanpageBinding


class QR_Scanpage : AppCompatActivity() {

    private lateinit var binding: ActivityQrScanpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
    }

    private fun initViews() {
        binding.btnScan.setOnClickListener {
            val i = Intent(this@QR_Scanpage, ScanActivity::class.java)
            startActivity(i)
            this.finish()
        }
    }

    private fun initBinding() {
        binding = ActivityQrScanpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onDestroy()
    }
}