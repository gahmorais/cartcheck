package br.com.gabrielmorais.cartcheck.di

import androidx.room.Room
import br.com.gabrielmorais.cartcheck.data.AppDatabase
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import br.com.gabrielmorais.cartcheck.utils.TempData
import org.koin.dsl.module

val mainModule = module {
  single { CartRepository(get<AppDatabase>().cartDao()) }
  single { TempData(get()) }
  single {
    Room.databaseBuilder(
      get(),
      AppDatabase::class.java,
      "cartcheck"
    ).build()
  }

}