package com.example.cinegry.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinegry.model.request.ReqEscapeRooms
import com.example.cinegry.model.request.ReqLogin
import com.example.cinegry.model.response.ResEscapeRooms
import com.example.cinegry.model.response.ResLogin
import com.example.cinegry.network.ApiClient
import com.example.cinegry.model.BaseResponse
import com.example.cinegry.utils.DEVICE_TYPE
import com.example.cinegry.utils.KEY_ACCESS_TOCKEN
import com.example.cinegry.utils.KEY_AUTHORIZATION
import com.example.cinegry.utils.KEY_USER_ID
import com.example.cinegry.utils.LOCATION_ID
import com.example.cinegry.utils.LOGIN_RESPONSE
import com.example.cinegry.utils.SECRET_KEY
import com.example.cinegry.utils.SecureSharedPrefs
import com.example.cinegry.utils.USER_TOKEN
import com.example.cinegry.utils.Utils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EscapeRoomsViewModel : ViewModel() {

    private var loginLivedata: MutableLiveData<BaseResponse<ResLogin>> = MutableLiveData()
    private var escapeRoomsLivedata: MutableLiveData<BaseResponse<ResEscapeRooms>> = MutableLiveData()

    lateinit var  sharedPreferences: SharedPreferences
    fun login(context: Context) {

        loginLivedata.value = BaseResponse.Loading()
        val reqGuestToken = ReqLogin(Utils.UniqeId(context), DEVICE_TYPE,  "2","","")

        sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val accessToken = sharedPreferences.getString(USER_TOKEN, "")
        val header = mapOf(
            KEY_AUTHORIZATION to accessToken,
            KEY_ACCESS_TOCKEN to SECRET_KEY,
            KEY_USER_ID to "0"
        )

        ApiClient.client.login(header, reqGuestToken).enqueue(object :
            Callback<ResLogin> {
            override fun onResponse(
                call: Call<ResLogin>,
                response: Response<ResLogin>
            ) {
                if (response.body() != null) {
                    val json = Gson().toJson(response.body())
                    val editor = sharedPreferences.edit()
                    editor.putString(LOGIN_RESPONSE, json)
                    editor.apply()
                    loginLivedata.value = BaseResponse.Success(response.body())
                } else {
                    loginLivedata.value = BaseResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ResLogin>, t: Throwable) {
                loginLivedata.value = BaseResponse.Error(t.message)
            }
        })
    }

    fun observeloginLivedata(): LiveData<BaseResponse<ResLogin>> {
        return loginLivedata
    }

    fun getEscapeRooms(context: Context) {

        escapeRoomsLivedata.value = BaseResponse.Loading()

        val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val accessToken = sharedPreferences.getString(USER_TOKEN, "")
        val userDetails = sharedPreferences.getString(LOGIN_RESPONSE, "")
        val user = Gson().fromJson(userDetails, ResLogin::class.java)
        val header = mapOf(
            KEY_AUTHORIZATION to accessToken,
            KEY_ACCESS_TOCKEN to SECRET_KEY,
            KEY_USER_ID to user.user.id.toString()
        )

        val reqGuestToken = ReqEscapeRooms(Utils.UniqeId(context), DEVICE_TYPE,  LOCATION_ID,
            user.user.member_id.toString()
        )

        ApiClient.client.getEscapeRooms(header, reqGuestToken).enqueue(object :
            Callback<ResEscapeRooms> {
            override fun onResponse(
                call: Call<ResEscapeRooms>,
                response: Response<ResEscapeRooms>
            ) {
                if (response.body() != null) {

                    escapeRoomsLivedata.value = BaseResponse.Success(response.body())
                } else {
                    escapeRoomsLivedata.value = BaseResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ResEscapeRooms>, t: Throwable) {
                escapeRoomsLivedata.value = BaseResponse.Error(t.message)
            }
        })
    }

    fun observeEscapeRoomsLivedata(): LiveData<BaseResponse<ResEscapeRooms>> {
        return escapeRoomsLivedata
    }
} 
