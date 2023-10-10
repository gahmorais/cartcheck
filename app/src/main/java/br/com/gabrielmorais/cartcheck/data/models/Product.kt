package br.com.gabrielmorais.cartcheck.data.models

data class Product(
  val description: String,
  val price: Double,
  val quantity: Int = 1
)