package com.example.cinegry.view.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinegry.R
import com.example.cinegry.databinding.BottomSheetLayoutBinding
import com.example.cinegry.model.response.EscapeRoomsMovy
import com.example.cinegry.utils.ARG_DATA
import com.example.cinegry.view.activity.DetailedActivity
import com.example.cinegry.view.activity.EscapeRoomsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class BottomSheetDialog(private val escapeRoomsMovy: EscapeRoomsMovy) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        binding.tvTitle.text = escapeRoomsMovy.Title
        binding.tvTime.text = escapeRoomsMovy.RunTime
        binding.tvDescription.text = escapeRoomsMovy.Synopsis

        binding.ivClose.setOnClickListener{
            dismiss()
        }
        binding.btnBookNow.setOnClickListener{
            dismiss()
            val intent = Intent(requireContext(), DetailedActivity::class.java)
            intent.putExtra(ARG_DATA,escapeRoomsMovy.ID)
            startActivity(intent)
        }
        Picasso.get()
            .load(escapeRoomsMovy.image_url)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
            .into(binding.ivMain);

        return binding.root
    }
}