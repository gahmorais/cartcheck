package br.com.gabrielmorais.cartcheck.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class Cart(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  @ColumnInfo(name = "data_compra")
  val date: Long,
  @ColumnInfo(name = "lista_produtos")
  val products: List<Product>,
  @ColumnInfo(name = "preco_total")
  val totalPrice: Double,
  @ColumnInfo(name = "saldo")
  val balance: Double
)
