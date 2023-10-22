package com.example.observablesdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.observablesdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    private val viewModel: MViewModel by lazy {
        ViewModelProvider(this).get(MViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()

        initListeners()

    }

    private fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun initListeners() {
        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.mutateFlow().collect {
                    binding.txtFlow.text = it
                    showSnackBar("Update-Flow")
                }
            }
        }

        binding.btnStateflow.setOnClickListener {
            viewModel.mutateStateFlow()
        }

        binding.btnSharedflow.setOnClickListener {
            viewModel.mutateSharedFlow()
        }

        binding.btnLivedata.setOnClickListener {
            viewModel.mutateLiveData()
        }
    }

    private fun initObservers() {
        viewModel.liveData.observe(this) {
            binding.tvLivedata.text = it
            showSnackBar("Update-LiveData")
        }

        lifecycleScope.launch {
            viewModel.sharedFlow.collect {
                binding.txtSharedflow.text = it
                showSnackBar("SharedFlow-Update")
            }
        }

        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                binding.txtStateflow.text = it
                showSnackBar("StateFlow-Update")
            }
        }
    }

}