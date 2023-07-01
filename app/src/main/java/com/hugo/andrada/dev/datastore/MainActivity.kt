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
        setButton()
    }

    private fun setDataStore() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateCompleted.collect { state ->
                    if (state) {
                        binding.txtState.text = " Data = TRUE"
                    } else {
                        binding.txtState.text = "No data saved."
                    }
                }
            }
        }
    }

    private fun setTimeState() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.time.collect { time ->
                    binding.textTime.text = if (time == 0L) "No time saved." else "Time = $time"
                    binding.progressBar.isVisible = time < System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(1)
                }
            }
        }
    }

    private fun setButton() {
        binding.btnState.setOnClickListener {
            viewModel.saveDataStoreState(true)
            val time = System.currentTimeMillis()
            viewModel.saveTimeDataStore(time)
        }
        binding.btnClear.setOnClickListener {
            viewModel.saveDataStoreState(false)
            viewModel.saveTimeDataStore(0)
        }
    }
}