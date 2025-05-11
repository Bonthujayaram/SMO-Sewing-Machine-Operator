package com.example.smo1


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smo1.ui.LoginActivity
import com.example.smo1.ui.home.HomeFragment

class SignupActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var roleSpinner: Spinner
    private lateinit var signupButton: Button
    private lateinit var loginPrompt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        // Initialize views
        nameEditText = findViewById(R.id.name)
        passwordEditText = findViewById(R.id.password)
        emailEditText = findViewById(R.id.email)
        phoneEditText = findViewById(R.id.phone)
        roleSpinner = findViewById(R.id.role)
        signupButton = findViewById(R.id.signup_button)
        loginPrompt = findViewById(R.id.login_prompt)

        // Populate the Spinner with role options
        val roles = arrayOf("Select your role", "Admin", "User ") // Added hint option
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        signupButton.setOnClickListener {
            // Handle signup logic here
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val role = roleSpinner.selectedItem.toString() // Get selected role

            // Basic validation
            if (name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || role == "Select your role") {
                Toast.makeText(this, "Please fill in all fields and select a role", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Handle signup logic here (e.g., API call, database insertion)
            // For demonstration, we'll just show a success message
            Toast.makeText(this, "Signup successful for $name with role $role", Toast.LENGTH_SHORT).show()

            // Navigate to HomeActivity on successful signup
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish() // Close the SignupActivity
        }

        // Set OnClickListener for the login prompt
        loginPrompt.setOnClickListener {
            // Navigate back to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}