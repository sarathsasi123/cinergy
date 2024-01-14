package com.example.cinegry.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinegry.model.request.ReqGuestTocken
import com.example.cinegry.model.response.ResGuestTocken
import com.example.cinegry.network.ApiClient
import com.example.cinegry.model.BaseResponse
import com.example.cinegry.utils.DEVICE_TYPE
import com.example.cinegry.utils.SECRET_KEY
import com.example.cinegry.utils.SecureSharedPrefs
import com.example.cinegry.utils.USER_TOKEN
import com.example.cinegry.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private var loginLivedata: MutableLiveData<BaseResponse<ResGuestTocken>> = MutableLiveData()

    fun guestlogin(context:Context) {

        loginLivedata.value = BaseResponse.Loading()
        val reqGuestToken = ReqGuestTocken(SECRET_KEY, DEVICE_TYPE, Utils.UniqeId(context), "")


        ApiClient.client.guestLogin(reqGuestToken).enqueue(object :
            Callback<ResGuestTocken> {
            override fun onResponse(
                call: Call<ResGuestTocken>,
                response: Response<ResGuestTocken>
            ) {
                if (response.body() != null) {

                    val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
                    val editor = sharedPreferences.edit()
                    editor.putString(USER_TOKEN, "Bearer ${response.body()!!.token}")
                    editor.apply()
                    loginLivedata.value = BaseResponse.Success(response.body())

                } else {
                    loginLivedata.value = BaseResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ResGuestTocken>, t: Throwable) {
                loginLivedata.value = BaseResponse.Error(t.message)
            }
        })
    }

    fun observeGuestLoginLiveData(): LiveData<BaseResponse<ResGuestTocken>> {
        return loginLivedata
    }


} 
