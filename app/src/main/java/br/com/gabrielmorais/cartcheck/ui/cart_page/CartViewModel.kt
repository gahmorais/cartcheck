package br.com.gabrielmorais.cartcheck.ui.cart_page

import androidx.lifecycle.ViewModel
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
  private val _products = MutableStateFlow<List<Product>>(listOf())
  val products = _products.asStateFlow()

  private val _balance = MutableStateFlow(0.0)
  val balance = _balance.asStateFlow()

  suspend fun saveCart(cart: Cart) = try {
    cartRepository.save(cart)
  } catch (e: Exception) {
    e.printStackTrace()
  }


  fun addItem(product: Product) {
    val list = _products.value.toMutableList()
    list.add(product)
    _products.value = list.toList()
  }

  fun cleanCart(){
    _products.value = listOf()
  }

  fun removeItem(product: Product) {

    val list = _products.value.toMutableList() //toMutableList()
    list.remove(product)
    _products.value = list.toList()

  }

  fun setBalance(value: Double) {
    _balance.value = value
  }
}