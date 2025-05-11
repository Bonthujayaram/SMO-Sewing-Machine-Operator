package com.example.smo1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smo1.CutterActivity
import com.example.smo1.MainActivity
import com.example.smo1.R
import com.example.smo1.WorkerActivity
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.data.model.LoginRequest
import com.example.smo1.data.model.RoleResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    performLogin(username, password)
                }
            } else {
                Toast.makeText(this, "Please enter valid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun performLogin(username: String, password: String) {
        try {
            val request = LoginRequest(username, password)
            val response = RetrofitClient.authApi.login(request)
            if (response.isSuccessful) {
                val roleResponse = response.body()
                val role = roleResponse?.role
                val employeeName = roleResponse?.employeeName

                if (role != null && employeeName != null) {
                    // Save to SharedPreferences
                    val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("EMPLOYEE_NAME", employeeName)
                        putString("ROLE", role)
                        apply()
                    }

                    Toast.makeText(this, "Welcome $employeeName! Role: $role", Toast.LENGTH_SHORT).show()
                    navigateBasedOnRole(role, employeeName)
                } else {
                    Toast.makeText(this, "Login failed: Role or Name not found", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Login failed: Role or Name not found in response")
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Login failed"
                Toast.makeText(this, "Login failed: $errorBody", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Login failed: Response error - $errorBody, Code: ${response.code()}")
            }
        } catch (e: Exception) {
            val errorMessage = "Login failed: ${e.message}"
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            Log.e(TAG, errorMessage, e)
        }
    }

    private fun navigateBasedOnRole(role: String, employeeName: String) {
        val intent = when (role.lowercase()) {
            "floormanager" -> {
                Log.i(TAG, "Starting MainActivity with role: $role")
                Intent(this, MainActivity::class.java)
            }
            "workeremployee" -> {
                Log.i(TAG, "Starting WorkerActivity with role: $role")
                Intent(this, WorkerActivity::class.java)
            }
            "cutteremployee" -> {
                Log.i(TAG, "Starting CutterActivity with role: $role")
                Intent(this, CutterActivity::class.java)
            }
            else -> {
                Toast.makeText(this, "Unknown role: $role", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Unknown role: $role")
                Intent(this, MainActivity::class.java)
            }
        }
        // Add employee name and employeeId to intent
        intent.putExtra("EMPLOYEE_NAME", employeeName)
        startActivity(intent)
        finish()
    }
}