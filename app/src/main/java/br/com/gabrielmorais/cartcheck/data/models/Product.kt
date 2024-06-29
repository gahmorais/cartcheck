package br.com.gabrielmorais.cartcheck.data.models

import java.util.UUID


data class Product(
  var id: String = UUID.randomUUID().toString(),
  val description: String,
  val price: Double = 0.0,
  val quantity: Int = 1
)