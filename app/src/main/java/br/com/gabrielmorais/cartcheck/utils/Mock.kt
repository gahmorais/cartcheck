package br.com.gabrielmorais.cartcheck.utils

import androidx.compose.runtime.mutableStateOf
import br.com.gabrielmorais.cartcheck.data.models.Product

val mockProductList = mutableStateOf(
  listOf(
    Product("Arroz", 20.5, 1),
    Product("Feijão", 9.5, 4),
    Product("Açucar", 3.5, 4),
    Product("Café", 10.5, 2)
  )
)