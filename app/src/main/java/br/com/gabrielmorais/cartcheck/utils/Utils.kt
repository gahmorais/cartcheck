package br.com.gabrielmorais.cartcheck.utils

import br.com.gabrielmorais.cartcheck.data.models.Product
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Double.toBrazilianCurrency(): String {
  val format = NumberFormat.getCurrencyInstance()
  format.currency = Currency.getInstance(Locale("pt", "BR"))
  return format.format(this).replace(".", ",")
}

fun List<Product>.sum() = fold(0.0) { acc, product -> acc + product.price }