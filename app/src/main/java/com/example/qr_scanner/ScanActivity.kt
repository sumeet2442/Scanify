package com.example.qr_scanner

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.qr_scanner.databinding.ActivityScanBinding

private const val CAMERA_CODE = 101
class ScanActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupPermissions()
        initBinding()
        codeScanner()
    }

    private fun initBinding() {
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun codeScanner(){
        codeScanner = CodeScanner(this,binding.scanInterface)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread{
                    binding.tvResult.text = it.text
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread{
                    Log.e("Main","Camera initialization error: ${it.message}")
                }
            }
        }
        binding.scanInterface.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions(){
        val perm = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        if(perm != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"This app needs Camera permission to continue!",Toast.LENGTH_SHORT).show()
                }
                else{

                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val back = Intent(this@ScanActivity,QR_Scanpage::class.java)
        startActivity(back)
        this.finish()
    }
}