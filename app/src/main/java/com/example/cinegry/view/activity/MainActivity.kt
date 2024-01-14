package com.example.cinegry.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.cinegry.databinding.ActivityMainBinding
import com.example.cinegry.model.BaseResponse
import com.example.cinegry.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnGuest.setOnClickListener {
            viewModel.guestlogin(this)
        }

        viewModel.observeGuestLoginLiveData().observe(this, Observer {

            when (it) {
                is BaseResponse.Loading -> {
                    binding.pbLogin.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.pbLogin.visibility = View.GONE
                    val intent = Intent(this, EscapeRoomsActivity::class.java)
                    startActivity(intent)
                }

                is BaseResponse.Error -> {
                    Toast.makeText(this, "${it.msg}", Toast.LENGTH_SHORT).show()
                    binding.pbLogin.visibility = View.GONE
                }
                else -> {
                    binding.pbLogin.visibility = View.GONE
                }
            }
        })
    }

}