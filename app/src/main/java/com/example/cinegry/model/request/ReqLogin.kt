package com.example.cinegry.model.request

data class ReqLogin(val device_id: String,val device_type: String,val login_type: String, val email: String, val password: String)