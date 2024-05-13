package com.dofi.tb1.app

import android.app.Application
import com.dofi.tb1.di.librariesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.Locale

class SocialMercuApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(librariesModule)
            androidContext(this@SocialMercuApp)
        }
        Locale.setDefault(Locale("id"))
    }

}