package com.example.cinegry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinegry.R
import com.example.cinegry.databinding.ItemRoomsBinding
import com.example.cinegry.model.response.EscapeRoomsMovy
import com.squareup.picasso.Picasso

class RoomsRVAdapter(
    var mList: List<EscapeRoomsMovy>, val itemClickListener: (EscapeRoomsMovy)->Unit
) : RecyclerView.Adapter<RoomsRVAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemRoomsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoomsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                Picasso.get()
                    .load(image_url)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(binding.ivMovie)
                binding.tvTitle.text = Title
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
