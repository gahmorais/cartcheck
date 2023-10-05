package br.com.gabrielmorais.cartcheck.data

import androidx.room.RoomDatabase
import br.com.gabrielmorais.cartcheck.data.dao.CartDao

abstract class AppDatabase : RoomDatabase() {
  abstract fun cartDao(): CartDao
}