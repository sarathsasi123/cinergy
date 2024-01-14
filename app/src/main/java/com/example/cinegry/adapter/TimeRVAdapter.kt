package com.example.cinegry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinegry.databinding.ItemDateBinding
import com.example.cinegry.databinding.ItemRoomsBinding
import com.example.cinegry.databinding.ItemTimeBinding
import com.example.cinegry.model.response.EscapeRoomsMovy
import com.example.cinegry.model.response.Session
import com.example.cinegry.model.response.ShowTime
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class TimeRVAdapter(
    val mList: List<Session>, val itemClickListener: (Session)->Unit
) : RecyclerView.Adapter<TimeRVAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){

                binding.tvDate.text = Showtime
                binding.root.setOnClickListener {
                    itemClickListener(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
