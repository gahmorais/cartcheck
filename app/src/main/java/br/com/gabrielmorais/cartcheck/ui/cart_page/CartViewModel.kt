package br.com.gabrielmorais.cartcheck.ui.cart_page

import androidx.lifecycle.ViewModel
import br.com.gabrielmorais.cartcheck.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {
  private val _products = MutableStateFlow<MutableList<Product>>(mutableListOf())
  val products = _products.asStateFlow()

  fun add(product: Product) {
    val list = _products.value.toMutableList()
    list.add(product)
    _products.value = list
  }
}