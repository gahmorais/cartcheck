package br.com.gabrielmorais.cartcheck.utils

import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import kotlin.random.Random

private fun randomPrice() = Random.nextDouble(1.0, 100.0)
private fun randomQuantity() = Random.nextInt(1, 300)

val mockProductList = listOf(
  Product(description = "Arroz", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Feijão", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Açucar", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Oleo", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Refrigerante", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Agua Sanitaria", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Manteiga", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Desodorante", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Sabao em po", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Sabonete", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Batata Palha", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Leite Condensado", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Creme de leite", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Azeite de oliva", price = randomPrice(), quantity = randomQuantity()),
  Product(description = "Fuba", price = randomPrice(), quantity = randomQuantity()),
)

val mockCartList = listOf(
  Cart(products = mockProductList, totalPrice = 300.00),
  Cart(products = mockProductList, totalPrice = 400.00),
  Cart(products = mockProductList, totalPrice = 500.00),
  Cart(products = mockProductList, totalPrice = 600.00),
  Cart(products = mockProductList, totalPrice = 700.00),
  Cart(products = mockProductList, totalPrice = 800.00),
)