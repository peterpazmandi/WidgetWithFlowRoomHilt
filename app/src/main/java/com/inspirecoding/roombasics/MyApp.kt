package com.inspirecoding.roombasics

import android.app.Application
import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application()
{
    init
    {
        instance = this
    }

    companion object
    {
        private var instance: MyApp? = null

        fun applicationContext() : Context
        {
            return instance!!.applicationContext
        }
    }
}