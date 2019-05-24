package com.example.thedgmbh.utils

import android.app.Application
import com.example.thedgmbh.dependancyInjection.ApplicationComponent
import com.example.thedgmbh.dependancyInjection.ApplicationModule
import com.example.thedgmbh.dependancyInjection.DaggerApplicationComponent
import com.example.thedgmbh.dependancyInjection.RoomModule

class RoomApplication : Application() {
    private var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()

    }

    fun getApplicationComponent(): ApplicationComponent? {
        return applicationComponent
    }
}