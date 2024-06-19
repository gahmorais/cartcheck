package br.com.gabrielmorais.cartcheck.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "carts")
data class Cart(
  @PrimaryKey
  @ColumnInfo(name = "id")
  var id: String = UUID.randomUUID().toString(),
  @ColumnInfo(name = "data_compra")
  var date: Long = LocalDate.now().toEpochDay(),
  @ColumnInfo(name = "lista_produtos")
  var products: MutableList<Product> = mutableListOf(),
  @ColumnInfo(name = "preco_total")
  var totalPrice: Double = 0.0,
  @ColumnInfo(name = "saldo")
  var balance: Double = 0.0,
  @ColumnInfo(name = "finalizado")
  var isFinished: Boolean = false
)