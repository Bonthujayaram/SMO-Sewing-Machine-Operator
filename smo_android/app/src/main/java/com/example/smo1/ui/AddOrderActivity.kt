package com.example.smo1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smo1.data.api.RetrofitClient
import com.example.smo1.data.model.SetOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddOrderActivity : AppCompatActivity() {

    private lateinit var styleNoEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var customerNameEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        // Initialize views
        styleNoEditText = findViewById(R.id.style_no)
        quantityEditText = findViewById(R.id.quantity)
        customerNameEditText = findViewById(R.id.customer_name)
        submitButton = findViewById(R.id.submit_button)


        // Set OnClickListener for submit button
        submitButton.setOnClickListener {
            val styleNo = styleNoEditText.text.toString()
            val quantityno = quantityEditText.text.toString().toInt()

            if (styleNo.isEmpty()) {
                Toast.makeText(this, "Please enter Style No", Toast.LENGTH_SHORT).show()
            } else {
                // Create Order object to send
                val order = SetOrder(style = styleNo, quantity = quantityno, customername = "Customer Name")

                // Call API to add order
                RetrofitClient.api.addOrder(order).enqueue(object : Callback<SetOrder> {
                    override fun onResponse(call: Call<SetOrder>, response: Response<SetOrder>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AddOrderActivity, "Order added successfully!", Toast.LENGTH_SHORT).show()

                            // Pass order details back to HomeFragment
                            val intent = Intent()
                            intent.putExtra("orderDetails", "Style No: $styleNo")
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            Toast.makeText(this@AddOrderActivity, "Failed to add order!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<SetOrder>, t: Throwable) {
                        Toast.makeText(this@AddOrderActivity, "API Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Submitted Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
