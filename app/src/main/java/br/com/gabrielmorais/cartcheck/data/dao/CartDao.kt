package br.com.gabrielmorais.cartcheck.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.gabrielmorais.cartcheck.data.models.Cart

@Dao
interface CartDao {
  @Insert
  fun insert(cart: Cart)

  @Query("SELECT * FROM carts WHERE id = :id ")
  fun getById(id: Int): Cart

  @Delete
  fun delete(id: Int)
}