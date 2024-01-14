package com.example.cinegry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cinegry.R
import com.example.cinegry.databinding.ItemDateBinding
import com.example.cinegry.model.response.Session
import com.example.cinegry.model.response.ShowTime
import java.text.SimpleDateFormat
import java.util.Locale

class DateRVAdapter(
    val mList: List<ShowTime>, val itemClickListener: (List<Session>)->Unit
) : RecyclerView.Adapter<DateRVAdapter.ViewHolder>() {

    private var rowIndex = 0;
    inner class ViewHolder(val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){

                val inputPattern = "yyyy-MM-dd"
                val dayShort = "EE"
                val dateShort = "MMM dd"

                val fullDateFormatter = SimpleDateFormat(inputPattern, Locale.ENGLISH)
                val dayFormatter = SimpleDateFormat(dayShort, Locale.ENGLISH)
                val dateFormatter = SimpleDateFormat(dateShort, Locale.ENGLISH)


                binding.tvDay.text = dayFormatter.format(fullDateFormatter.parse(date)!!)
                binding.tvDate.text = dateFormatter.format(fullDateFormatter.parse(date)!!)
                binding.root.setOnClickListener {
                    rowIndex=adapterPosition
                    notifyDataSetChanged()
                    itemClickListener(this.sessions)
                }

                if(rowIndex==position){

                    binding.layoutMain.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.grey_green))
                }
                else
                {
                    binding.layoutMain.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.light_grey))
                }
            }


        }


    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
