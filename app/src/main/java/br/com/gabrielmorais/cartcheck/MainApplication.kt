package br.com.gabrielmorais.cartcheck

import android.app.Application
import br.com.gabrielmorais.cartcheck.di.mainModule
import br.com.gabrielmorais.cartcheck.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    startKoin {
      androidLogger()
      androidContext(this@MainApplication)
      modules(listOf(mainModule, viewModelModule))
    }


  }
}