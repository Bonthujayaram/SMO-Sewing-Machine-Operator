package com.example.smo1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.data.model.CuttingRequest
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CutterActivity : AppCompatActivity() {

    private lateinit var qridEditText: EditText
    private lateinit var typeEditText: EditText
    private lateinit var sizeEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var submitBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var scanBtn: Button

    private val CAMERA_PERMISSION_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cutter)

        qridEditText = findViewById(R.id.qrid)
        typeEditText = findViewById(R.id.type)
        sizeEditText = findViewById(R.id.size)
        quantityEditText = findViewById(R.id.quantity)
        submitBtn = findViewById(R.id.btnSubmit)
        resetBtn = findViewById(R.id.btnReset)
        scanBtn = findViewById(R.id.btnScan)

        submitBtn.setOnClickListener {
            val qrid = qridEditText.text.toString().trim()
            val type = typeEditText.text.toString().trim()
            val size = sizeEditText.text.toString().trim()
            val quantityText = quantityEditText.text.toString().trim()

            if (qrid.isNotEmpty() && type.isNotEmpty() && size.isNotEmpty() && quantityText.isNotEmpty()) {
                try {
                    val quantity = quantityText.toInt()
                    CoroutineScope(Dispatchers.Main).launch {
                        performLogin(qrid, type, size, quantity)
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter valid numbers for QR ID and quantity", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        resetBtn.setOnClickListener {
            qridEditText.text.clear()
            typeEditText.text.clear()
            sizeEditText.text.clear()
            quantityEditText.text.clear()
            qridEditText.isEnabled = true // Re-enable qrid EditText on reset
            Toast.makeText(this, "Fields reset", Toast.LENGTH_SHORT).show()
        }

        scanBtn.setOnClickListener {
            if (checkCameraPermission()) {
                startQrCodeScanner()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startQrCodeScanner()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startQrCodeScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.setCameraId(0) // Use back camera
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true) // Lock orientation
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                qridEditText.setText(result.contents) // Set scanned QR code to qrid EditText
                qridEditText.isEnabled = false // Make qrid EditText uneditable
            } else {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun performLogin(qrid: String, type: String, size: String, quantity: Int) {
        try {
            val request = CuttingRequest(qrid, type, size, quantity)
            Log.d("Myapp", "Sending request: $request")
            val response = RetrofitClient.api.setBundle(request)
            if (response.isSuccessful) {
                Toast.makeText(this, "Successfully Submitted", Toast.LENGTH_SHORT).show()
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                Log.e("Myapp", "Submission failed: Response error - $errorBody, Code: ${response.code()}")
                Toast.makeText(this, "Submission failed: $errorBody", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            val errorMessage = "Submission failed: ${e.message}"
            Log.e("Myapp", errorMessage, e)
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}