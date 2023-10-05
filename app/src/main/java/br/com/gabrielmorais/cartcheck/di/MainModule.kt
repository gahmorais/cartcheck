package br.com.gabrielmorais.cartcheck.di

import androidx.room.Room
import br.com.gabrielmorais.cartcheck.data.AppDatabase
import org.koin.dsl.module

val mainModule = module {
  factory { Room.databaseBuilder(get(), AppDatabase::class.java, "cartcheck") }
}