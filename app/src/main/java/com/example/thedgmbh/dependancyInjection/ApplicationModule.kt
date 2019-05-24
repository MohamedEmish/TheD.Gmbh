package com.example.thedgmbh.dependancyInjection

import android.app.Application
import com.example.thedgmbh.utils.RoomApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: RoomApplication) {

    @Provides
    fun provideRoomDemoApplication(): RoomApplication {
        return application
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }
}