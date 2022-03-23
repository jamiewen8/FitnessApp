package com.example.dashboard

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.budiyev.android.codescanner.CodeScanner
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.dashboard.ui.viewmodels.FoodViewModel


//private const val CAMERA_REQUEST_CODE = 101

class SecondActivity : AppCompatActivity() {
    private lateinit var codescanner: CodeScanner

    val viewModel: FoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanner)

        observerSetup()

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==
            PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 123)
        }else{

            startScanning()
        }
    }
    //todo make sure this links with the db with the content of the barcode number

    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codescanner = CodeScanner(this, scannerView)
        codescanner.camera = CodeScanner.CAMERA_BACK
        codescanner.formats = CodeScanner.ALL_FORMATS

        codescanner.autoFocusMode = AutoFocusMode.SAFE
        codescanner.scanMode = ScanMode.SINGLE
        codescanner.isAutoFocusEnabled = true
        codescanner.isFlashEnabled = false

        codescanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan Result: ${it.text}", Toast.LENGTH_SHORT).show()
                //use this result to display the text and in turn use the text to identify the item to add to the diary
                viewModel.findFood(it.text.toInt())

            }
        }


        codescanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_SHORT).show()

            }
        }

        scannerView.setOnClickListener {
            codescanner.startPreview()
        }


    }

    private fun observerSetup() {
        viewModel.getSearchResults().observe(this) { foods ->
            foods?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(this, "Item is: ${it[0].food_name}", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onResume(){
        super.onResume()
        if(::codescanner.isInitialized){
            codescanner?.startPreview()

        }
    }
    override fun onPause() {
        if (::codescanner.isInitialized) {
            codescanner?.releaseResources()
        }
        super.onPause()

    }


}