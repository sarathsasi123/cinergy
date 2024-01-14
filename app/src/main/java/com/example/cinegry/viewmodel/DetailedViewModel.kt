package com.example.cinegry.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinegry.model.request.ReqDetails
import com.example.cinegry.model.response.ResDetails
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

class DetailedViewModel: ViewModel() {
    private var detailsLivedata: MutableLiveData<BaseResponse<ResDetails>> = MutableLiveData()

    fun getDetails(movieId:String, context: Context) {

        detailsLivedata.value = BaseResponse.Loading()

        val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val accessToken = sharedPreferences.getString(USER_TOKEN, "")
        val userDetails = sharedPreferences.getString(LOGIN_RESPONSE, "")
        val user = Gson().fromJson(userDetails, ResLogin::class.java)
        val header = mapOf(
            KEY_AUTHORIZATION to accessToken,
            KEY_ACCESS_TOCKEN to SECRET_KEY,
            KEY_USER_ID to user.user.id.toString()
        )

        val reqGuestToken = ReqDetails(Utils.UniqeId(context), DEVICE_TYPE,  LOCATION_ID,
            movieId
        )

        ApiClient.client.getMovieInfo(header, reqGuestToken).enqueue(object :
            Callback<ResDetails> {
            override fun onResponse(
                call: Call<ResDetails>,
                response: Response<ResDetails>
            ) {
                if (response.body() != null) {

                    detailsLivedata.value = BaseResponse.Success(response.body())
                } else {
                    detailsLivedata.value = BaseResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<ResDetails>, t: Throwable) {
                detailsLivedata.value = BaseResponse.Error(t.message)
            }
        })
    }

    fun observeDetailsLivedata(): LiveData<BaseResponse<ResDetails>> {
        return detailsLivedata
    }
}