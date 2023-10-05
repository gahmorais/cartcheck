package br.com.gabrielmorais.cartcheck

import android.app.Application
import br.com.gabrielmorais.cartcheck.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidLogger()
      androidContext(this@MainApplication)
      modules(listOf(mainModule))
    }
  }
}