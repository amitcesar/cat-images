package com.github.amitcesar.cat_images

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.github.amitcesar.cat_images.api.CatApiService
import com.github.amitcesar.cat_images.databinding.ActivityMainBinding
import com.github.amitcesar.cat_images.model.CatImage
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val catAdapter = CatAdapter()
    private val catApiService = CatApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        fetchCats()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        binding.rvCats.apply {
            adapter = catAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun setupListeners() {
        binding.btnRetry.setOnClickListener {
            fetchCats()
        }
    }

    private fun fetchCats() {

        showLoading()

        lifecycleScope.launch {
            try {

                val response = catApiService.fetchCats()

                catAdapter.submitList(response)
                showContent()
            } catch (e: Exception) {
                e.printStackTrace()
                showError()
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorContainer.visibility = View.GONE
        binding.rvCats.visibility = View.GONE
    }

    private fun showContent() {
        binding.progressBar.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
        binding.rvCats.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.progressBar.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.rvCats.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        catApiService.close()
    }
}
