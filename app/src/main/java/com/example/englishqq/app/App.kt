package com.example.englishqq.app

import android.app.Application
import com.example.englishqq.di.dataModule
import com.example.englishqq.di.viewModelModule
import com.google.android.gms.common.util.CollectionUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(dataModule, viewModelModule)
        GlobalContext.startKoin { // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@App)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            koin.loadModules(modules)
        }
    }
}