package com.example.final_assignment.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_assignment.Adapter.CartAdapter
import com.example.final_assignment.Helper.ChangeNumberItemsListener
import com.example.final_assignment.Helper.ManagmentCart
import com.example.final_assignment.R
import com.example.final_assignment.databinding.ActivityCartBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart

    private var tax: Double = 0.0
    private lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        myCalendar = Calendar.getInstance()

        setVariable()
        initCartList()
        calculateCart()
        initDatePicker()
        initTimePicker()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter =
            CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            })

        with(binding) {
            emptyTxt.visibility =
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility =
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managmentCart.getTotalFee() * percentTax) * 100) / 100.0
        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100

        with(binding) {
            totalFeeTxt.text = "RM $itemTotal"
            taxTxt.text = "RM $tax"
            deliveryTxt.text = "RM $delivery"
            totalTxt.text = "RM $total"
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }

        binding.button.setOnClickListener {
            if (managmentCart.getListCart().isEmpty()) {
                // Show a message if the cart is empty
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.tvDate.text.isEmpty() || binding.tvTime.text.isEmpty()) {
                // Show a message if date or time is not selected
                Toast.makeText(this, "Please pick a delivery date and time!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // If all conditions are met, navigate to the checkout page
            val intent = Intent(this, CheckoutActivity::class.java).apply {
                putExtra("delivery_date", binding.tvDate.text.toString())
                putExtra("delivery_time", binding.tvTime.text.toString())
            }
            startActivity(intent)

            // Optionally, you can display a success message on the CheckoutActivity
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initDatePicker() {
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel()
        }

        binding.btnDate.setOnClickListener {
            DatePickerDialog(
                this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateLabel() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.tvDate.text = sdf.format(myCalendar.time)
    }

    private fun initTimePicker() {
        binding.btnTime.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                binding.tvTime.text = String.format("%02d:%02d", hourOfDay, minute)
            }, startHour, startMinute, false).show()
        }
    }
}
