package com.example.moviesnews

import android.app.Application

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MoviesRepository.initialize(applicationContext)
    }

}