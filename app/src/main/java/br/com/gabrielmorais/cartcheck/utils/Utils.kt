package br.com.gabrielmorais.cartcheck.utils

import br.com.gabrielmorais.cartcheck.data.models.Product
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

fun Double.toBrazilianCurrency(): String {
  val format = NumberFormat.getCurrencyInstance()
  format.currency = Currency.getInstance(Locale("pt", "BR"))
  return format.format(this).replace(".", ",")
}

fun formatDate(long: Long): String {
  val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy")
  val date = LocalDate.ofEpochDay(long)
  return date.format(pattern)
}

fun isValidInt(value: String): Boolean {
  return value.toIntOrNull() != null
}

fun isValidDouble(value: String): Boolean {
  return value.toDoubleOrNull() != null
}

fun List<Product>.sum() = fold(0.0) { acc, product -> acc + (product.price * product.quantity) }