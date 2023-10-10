package br.com.gabrielmorais.cartcheck.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "carts")
data class Cart(
  @PrimaryKey
  val id: Int,
  @ColumnInfo(name = "data_compra")
  val date: LocalDate,
  @ColumnInfo(name = "lista_produtos")
  val products: List<Product>,
  @ColumnInfo(name = "preco_total")
  val totalPrice: Double
)
