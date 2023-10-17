package br.com.gabrielmorais.cartcheck.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.gabrielmorais.cartcheck.data.converters.Converters
import br.com.gabrielmorais.cartcheck.data.dao.CartDao
import br.com.gabrielmorais.cartcheck.data.models.Cart

@Database(version = 1, entities = [Cart::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun cartDao(): CartDao
}