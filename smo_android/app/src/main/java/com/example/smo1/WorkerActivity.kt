package com.example.smo1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.data.model.WorkerRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkerActivity : AppCompatActivity() {
    private var welcomeText: TextView? = null
    private var editTextMachineID: EditText? = null
    private var editTextJobID: EditText? = null
    private var submitButton: Button? = null
    private var scanMachineIdButton: Button? = null
    private var scanJobIdButton: Button? = null
    private var resetButton: Button? = null

    private val CAMERA_REQUEST_CODE = 100
    private var isScanningMachineId = true

    // Retrofit API service
    private val apiService = RetrofitClient.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        welcomeText = findViewById(R.id.welcomeText)
        editTextMachineID = findViewById(R.id.machineIdInput)
        editTextJobID = findViewById(R.id.jobIdInput)
        submitButton = findViewById(R.id.submitButton)
        scanMachineIdButton = findViewById(R.id.scanMachineIdButton)
        scanJobIdButton = findViewById(R.id.scanJobIdButton)
        resetButton = findViewById(R.id.resetButton)

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val employeeName = sharedPref.getString("EMPLOYEE_NAME", "Worker")
            ?: intent.getStringExtra("EMPLOYEE_NAME") ?: "Worker"
        welcomeText?.text = "Welcome, $employeeName!"

        checkCameraPermission()

        submitButton?.setOnClickListener { handleSubmission() }
        scanMachineIdButton?.setOnClickListener {
            isScanningMachineId = true
            startQrScanner()
        }
        scanJobIdButton?.setOnClickListener {
            isScanningMachineId = false
            startQrScanner()
        }
        resetButton?.setOnClickListener {
            editTextMachineID?.setText("")
            editTextJobID?.setText("")
            Toast.makeText(this, "Fields cleared", Toast.LENGTH_SHORT).show()
        }

        editTextMachineID?.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                handleQRIDInput(editTextMachineID?.text.toString())
                true
            } else false
        }

        editTextJobID?.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                handleQRIDInput(editTextJobID?.text.toString())
                true
            } else false
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        }
    }

    private fun startQrScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR code")
        options.setCameraId(0)
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val scannedData = result.contents
            if (isScanningMachineId) {
                editTextMachineID?.setText(scannedData)
                Toast.makeText(this, "Machine ID scanned: $scannedData", Toast.LENGTH_SHORT).show()
            } else {
                val machineId = editTextMachineID?.text.toString()
                if (machineId == scannedData) {
                    Toast.makeText(this, "Scan again, IDs are the same.", Toast.LENGTH_SHORT).show()
                } else {
                    editTextJobID?.setText(scannedData)
                    Toast.makeText(this, "Job ID scanned: $scannedData", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSubmission() {
        val machineid = editTextMachineID?.text.toString()
        val qrid = editTextJobID?.text.toString()

        if (machineid.isNotEmpty() && qrid.isNotEmpty()) {
            if (machineid == qrid) {
                Toast.makeText(this, "Scan again, IDs are the same.", Toast.LENGTH_SHORT).show()
                editTextMachineID?.setText("")
                editTextJobID?.setText("")
            } else {
                // Get employee ID from SharedPreferences (assuming it's stored as an int)
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val employeeId = sharedPref.getString("EMPLOYEE_NAME", null).toString()

                // Create WorkerRequest object
                val request = WorkerRequest(employeeId, machineid, qrid)

                // Submit to API using coroutine
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val response: Response<Unit> = apiService.performBundle(request)
                        if (response.isSuccessful) {
                            Toast.makeText(this@WorkerActivity, "Submission Successful!", Toast.LENGTH_SHORT).show()
                            editTextMachineID?.setText("")
                            editTextJobID?.setText("")
                        } else {
                            Toast.makeText(this@WorkerActivity, "Submission failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@WorkerActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleQRIDInput(scannedID: String) {
        val machineID = editTextMachineID?.text.toString()
        val jobID = editTextJobID?.text.toString()

        if (machineID.isEmpty()) {
            editTextMachineID?.setText(scannedID)
            Toast.makeText(this, "Machine ID scanned: $scannedID", Toast.LENGTH_SHORT).show()
        } else if (jobID.isEmpty()) {
            if (machineID == scannedID) {
                Toast.makeText(this, "Scan again, IDs are the same.", Toast.LENGTH_SHORT).show()
            } else {
                editTextJobID?.setText(scannedID)
                Toast.makeText(this, "Job ID scanned: $scannedID", Toast.LENGTH_SHORT).show()
            }
        } else {
            handleSubmission()
            Toast.makeText(this, "Both fields are filled. Please submit or clear.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Camera permission required for scanning", Toast.LENGTH_LONG).show()
        }
    }
}