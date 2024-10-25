// FavoriteActivity.kt
package com.example.final_assignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_assignment.Adapter.FavoriteAdapter
import com.example.final_assignment.Helper.ManagmentCart
import com.example.final_assignment.databinding.ActivityFavoriteBinding

class FavoriteActivity : BaseActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var managmentCart: ManagmentCart
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter(managmentCart.getListCart()) { item ->
            managmentCart.removeFromFavorites(item)
            favoriteAdapter.updateFavorites(managmentCart.getListCart())
        }
        binding.recyclerViewFavorites.adapter = favoriteAdapter
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { finish() }
    }
}
