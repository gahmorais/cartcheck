package br.com.gabrielmorais.cartcheck.ui.cart_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import br.com.gabrielmorais.cartcheck.utils.TempData
import br.com.gabrielmorais.cartcheck.utils.sum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CartViewModel(
  private val cartRepository: CartRepository,
  private val tempData: TempData
) : ViewModel() {
  private val _cart = MutableStateFlow(Cart())
  val cart = _cart.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      getCurrentCart()
    }
  }

  private suspend fun saveCart() = try {
    cartRepository.save(cart.value)
  } catch (e: Exception) {
    e.printStackTrace()
  }

  suspend fun finishPurchase() {
    saveCart()
    cleanCart()
  }

  suspend fun saveCurrentCart() {
    runBlocking {
      tempData.setCurrentCart(cart.value.id)
      saveCart()
      Log.i("CartViewModel", "saveCurrentCart: ${cart.value}")
    }
  }

  private suspend fun getCurrentCart() {
    val currentCartId = tempData.getCurrentCart()
    Log.i("CartViewModel", "getCurrentCart: $currentCartId")
    if (currentCartId.isNotEmpty()) {
      try {
        withContext(Dispatchers.IO) {
          val currentCart = cartRepository.getById(currentCartId)
          _cart.emit(currentCart)
          Log.i("CartViewModel", "getCurrentCartValue: ${cart.value}")
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    } else {
      val newCart = Cart()
      _cart.emit(newCart)
      Log.i("CartViewModel", "newCart $newCart")
      saveCurrentCart()
    }
  }

  fun addItem(product: Product) {
    val list = _cart.value.products.toMutableList()
    list.add(product)
    _cart.value.products = list.toList()
    _cart.value.totalPrice = list.sum()
  }

  private fun cleanCart() {
    _cart.value = Cart()
    tempData.setCurrentCart("")
  }

  fun removeItem(product: Product) {

    val list = _cart.value.products.toMutableList() //toMutableList()
    list.remove(product)
    _cart.value.products = list.toList()

  }

  fun setBalance(value: Double) {
    _cart.value.balance = value
  }
}