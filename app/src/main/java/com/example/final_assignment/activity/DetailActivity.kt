package com.example.final_assignment.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_assignment.Adapter.ColorAdapter
import com.example.final_assignment.Adapter.SizeAdapter
import com.example.final_assignment.Adapter.SliderAdapter
import com.example.final_assignment.Helper.ManagmentCart
import com.example.final_assignment.Model.ItemsModel
import com.example.final_assignment.Model.SliderModel
import com.example.final_assignment.R
import com.example.final_assignment.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundle()
        banners()
        initLists()
    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size) {
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }

        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = true
        binding.slider.clipChildren = true
        binding.slider.offscreenPageLimit = 1


        if (sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "RM " + item.price
        binding.ratingTxt.text = "${item.rating} Rating"
        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOder
            managmentCart.insertFood(item)
        }
        binding.backBtn.setOnClickListener { finish() }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
        updateFavoriteButton()
    }

    private fun updateFavoriteButton() {
        if (managmentCart.getListCart().any { it.title == item.title }) {
            binding.favBtn.setImageResource(R.drawable.ic_favorite_red)
        } else {
            binding.favBtn.setImageResource(R.drawable.ic_favorite_shadow)
        }

        binding.favBtn.setOnClickListener {
            if (managmentCart.getListCart().any { it.title == item.title }) {
                managmentCart.removeFromFavorites(item)
                binding.favBtn.setImageResource(R.drawable.ic_favorite_shadow)
            } else {
                managmentCart.insertFood(item)
                binding.favBtn.setImageResource(R.drawable.ic_favorite_red)
            }
        }
    }
    }
