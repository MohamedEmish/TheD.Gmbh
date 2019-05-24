package com.example.thedgmbh.dependancyInjection

import android.app.Application
import com.example.thedgmbh.views.DetailsActivity
import com.example.thedgmbh.views.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RoomModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(detailsActivity: DetailsActivity)

    fun application(): Application
}