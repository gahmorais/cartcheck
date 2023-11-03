package br.com.gabrielmorais.cartcheck.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.gabrielmorais.cartcheck.data.models.Cart

@Dao
interface CartDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(cart: Cart)

  @Query("SELECT * FROM carts")
  fun getAllCarts(): List<Cart>

  @Query("SELECT finalizado FROM carts WHERE id = :id")
  fun isFinished(id: String): Boolean

  @Query("SELECT * FROM carts WHERE id = :id")
  suspend fun getById(id: String): Cart

  @Delete
  fun delete(cart: Cart)
}