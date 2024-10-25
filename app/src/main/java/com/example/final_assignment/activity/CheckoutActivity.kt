package com.example.final_assignment.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.final_assignment.R
import android.widget.TextView
import com.example.final_assignment.Helper.ManagmentCart

class CheckoutActivity : BaseActivity() {
    private lateinit var managmentCart: ManagmentCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val deliveryDate = intent.getStringExtra("delivery_date")
        val deliveryTime = intent.getStringExtra("delivery_time")
        managmentCart = ManagmentCart(this)

        // Display the delivery date and time
        findViewById<TextView>(R.id.tvSelectedDate).text = "Desired Delivery Date: $deliveryDate"
        findViewById<TextView>(R.id.tvSelectedTime).text = "Desired Delivery Time: $deliveryTime"

        // You can also display a success message
        findViewById<TextView>(R.id.tvOrderMessage).text = "Order placed successfully!"

        // Implementing the reset functionality
        findViewById<Button>(R.id.btnReset).setOnClickListener {
            // Clear the cart
            managmentCart.clearCart()

            // Restart the app by launching the main activity
            val intent = Intent(this, IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}

