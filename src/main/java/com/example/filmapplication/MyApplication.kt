package com.example.filmapplication

import android.app.Application
import com.example.filmapplication.di.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MyApplication)
            modules(movieModule)
        }
    }
}