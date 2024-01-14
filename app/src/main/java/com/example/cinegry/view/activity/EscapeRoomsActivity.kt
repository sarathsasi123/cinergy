package com.example.cinegry.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinegry.databinding.ActivityEscapeRoomsBinding
import com.example.cinegry.adapter.RoomsRVAdapter
import com.example.cinegry.model.response.EscapeRoomsMovy
import com.example.cinegry.utils.BOTTOM_SHEET
import com.example.cinegry.model.BaseResponse
import com.example.cinegry.view.dialog.BottomSheetDialog
import com.example.cinegry.viewmodel.EscapeRoomsViewModel

class EscapeRoomsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEscapeRoomsBinding
    private val viewModel: EscapeRoomsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEscapeRoomsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.viewBack.setOnClickListener { finish() }

        viewModel.login(this)

        setObserver()


    }

    private fun setObserver() {
        viewModel.observeloginLivedata().observe(this, Observer {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    viewModel.getEscapeRooms(this)
                }

                is BaseResponse.Error -> {
                    Toast.makeText(this, "${it.msg}", Toast.LENGTH_SHORT).show()
                    binding.pbMain.visibility = View.GONE
                }
                else -> {
                    binding.pbMain.visibility = View.GONE
                }
            }
        })

        viewModel.observeEscapeRoomsLivedata().observe(this, Observer {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.pbMain.visibility = View.GONE
                    listData(it.data!!.escape_rooms_movies)
                }

                is BaseResponse.Error -> {
                    Toast.makeText(this, "${it.msg}", Toast.LENGTH_SHORT).show()
                    binding.pbMain.visibility = View.GONE
                }
                else -> {
                    binding.pbMain.visibility = View.GONE
                }
            }
        })
    }

    private fun listData(escapeRoomsMovies: List<EscapeRoomsMovy>) {
        binding.rvRooms.layoutManager = GridLayoutManager(this,2)
        val adapter = RoomsRVAdapter(escapeRoomsMovies){
            showBottomSheet(it)
        }
        binding.rvRooms.adapter = adapter
    }

    private fun showBottomSheet(escapeRoomsMovy: EscapeRoomsMovy) {

        val bottomSheet = BottomSheetDialog(escapeRoomsMovy)
        bottomSheet.show(supportFragmentManager, BOTTOM_SHEET)
    }
}

