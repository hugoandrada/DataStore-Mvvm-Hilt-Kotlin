package com.hugo.andrada.dev.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hugo.andrada.dev.datastore.databinding.ActivityMainBinding
import com.hugo.andrada.dev.datastore.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataStore()
        setTimeState()
        setNameState()
        setButton()
    }

    private fun setDataStore() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateBoolean.collect { state ->
                    when {
                        state -> binding.txtState.text = " State = " + state.toString()
                        else -> binding.txtState.text = "No data saved."
                    }
                }
            }
        }
    }

    private fun setTimeState() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.time.collect { time ->
                    when {
                        (time < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2)) -> {
                            binding.textTime.text = "Time over."
                            binding.progressBar.isVisible = true
                        }
                        else -> {
                            binding.textTime.text = "Time = $time"
                            binding.progressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun setNameState() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.name.collect { name ->
                    binding.name.text = if (name.isEmpty()) "No name" else "Name = $name"
                }
            }
        }
    }

    private fun setButton() {
        binding.btnState.setOnClickListener {
            val time = System.currentTimeMillis()
            val name = binding.nameText.text.toString()
            viewModel.saveTimeDataStore(time)
            viewModel.saveNameDataStore(name)
            viewModel.saveBoolean()
            binding.nameText.text?.clear()
        }
        binding.btnClear.setOnClickListener {
            viewModel.saveBoolean()
            viewModel.saveTimeDataStore(0)
            viewModel.saveNameDataStore("")
        }
    }
}