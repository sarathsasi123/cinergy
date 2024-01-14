package com.example.cinegry.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinegry.R
import com.example.cinegry.adapter.DateRVAdapter
import com.example.cinegry.adapter.TimeRVAdapter
import com.example.cinegry.databinding.ActivityDetailedBinding
import com.example.cinegry.model.response.ResDetails
import com.example.cinegry.model.response.Session
import com.example.cinegry.model.response.ShowTime
import com.example.cinegry.utils.ARG_DATA
import com.example.cinegry.model.BaseResponse
import com.example.cinegry.viewmodel.DetailedViewModel
import com.squareup.picasso.Picasso

class DetailedActivity : AppCompatActivity() {

    val viewModel: DetailedViewModel by viewModels()
    private lateinit var binding: ActivityDetailedBinding
    private var showTimesList = arrayListOf<Session>()
    private var showDateList = arrayListOf<ShowTime>()
    private lateinit var dateAdapter: DateRVAdapter
    private lateinit var timeAdapter: TimeRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.viewBack.setOnClickListener { finish() }

        listDateData()
        listTimeData()
        val movieId = intent.getStringExtra(ARG_DATA)
        viewModel.getDetails(movieId!!, this)

        setObserver()
    }

    private fun setObserver() {
        viewModel.observeDetailsLivedata().observe(this, Observer {

            when (it) {
                is BaseResponse.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.pbMain.visibility = View.GONE
                    setData(it.data!!)
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

    private fun setData(data: ResDetails) {

        data.apply {
            Picasso.get()
                .load(movie_info.image_url)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                .into(binding.ivMain);

            binding.tvTitle.text = movie_info.Title
            binding.tvRating.text = "${movie_info.Rating} . ${movie_info.RunTime} MIN"

            showTimesList.clear()
            showTimesList.addAll(movie_info.show_times[0].sessions)
            timeAdapter.notifyDataSetChanged()

            showDateList.clear()
            showDateList.addAll(movie_info.show_times)
            dateAdapter.notifyDataSetChanged()
        }

    }

    private fun listDateData() {
        binding.rvDate.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        dateAdapter = DateRVAdapter(showDateList) {
            showTimesList.clear()
            showTimesList.addAll(it)
            timeAdapter.notifyDataSetChanged()
        }
        binding.rvDate.adapter = dateAdapter
    }

    private fun listTimeData() {
        binding.rvTime.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        timeAdapter = TimeRVAdapter(showTimesList) {
        }
        binding.rvTime.adapter = timeAdapter
    }
}