package com.coding.sat

import android.app.Application
import com.coding.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModules
            )
        }
        initApp()
    }
}

fun initApp() {
    Logger.init()
}