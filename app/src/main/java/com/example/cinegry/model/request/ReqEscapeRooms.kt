package com.example.cinegry.model.request

data class ReqEscapeRooms(
    val device_id: String,
    val device_type: String,
    val location_id: String,
    val member_id: String
)