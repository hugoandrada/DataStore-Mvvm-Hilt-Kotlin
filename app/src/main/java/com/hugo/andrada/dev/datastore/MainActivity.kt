package com.hugo.andrada.dev.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.hugo.andrada.dev.datastore.databinding.ActivityMainBinding
import com.hugo.andrada.dev.datastore.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataStore()
        setButton()
    }

    private fun setDataStore() {
        lifecycleScope.launchWhenCreated {
            viewModel.stateCompleted.collect { state ->
                if (state) {
                    binding.txtState.text = " completed"
                } else {
                    binding.txtState.text = "wrong"
                }
            }
        }
    }

    private fun setButton() {
        binding.btnState.setOnClickListener {
            viewModel.saveDataStoreState(true)
            binding.txtState.text = " completed"
        }
        binding.btnClear.setOnClickListener {
            viewModel.saveDataStoreState(false)
            binding.txtState.text = "wrong"
        }
    }
}