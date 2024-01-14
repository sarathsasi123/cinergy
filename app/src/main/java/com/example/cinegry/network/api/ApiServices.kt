package com.example.cinegry.network.api

import com.example.cinegry.model.request.ReqDetails
import com.example.cinegry.model.request.ReqEscapeRooms
import com.example.cinegry.model.request.ReqGuestTocken
import com.example.cinegry.model.request.ReqLogin
import com.example.cinegry.model.response.ResDetails
import com.example.cinegry.model.response.ResEscapeRooms
import com.example.cinegry.model.response.ResGuestTocken
import com.example.cinegry.model.response.ResLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiServices {
	@POST("guestToken")
	fun guestLogin(@Body reqGuestTocken: ReqGuestTocken) : Call<ResGuestTocken>

	@POST("login")
	fun login(@HeaderMap headers: Map<String, String?>, @Body reqLogin: ReqLogin) : Call<ResLogin>

	@POST("escapeRoomMovies")
	fun getEscapeRooms(@HeaderMap headers: Map<String, String?>, @Body reqLogin: ReqEscapeRooms) : Call<ResEscapeRooms>

	@POST("getMovieInfo")
	fun getMovieInfo(@HeaderMap headers: Map<String, String?>, @Body reqLogin: ReqDetails) : Call<ResDetails>
}
