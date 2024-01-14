package com.example.cinegry.utils

import android.content.Context
import android.provider.Settings

class Utils {

    companion object {
        fun UniqeId(context: Context): String =
            "CI -${Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)}"
    }
}