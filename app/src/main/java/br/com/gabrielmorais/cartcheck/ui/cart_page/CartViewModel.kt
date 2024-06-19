package br.com.gabrielmorais.cartcheck.ui.cart_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import br.com.gabrielmorais.cartcheck.utils.TempData
import br.com.gabrielmorais.cartcheck.utils.sum
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

  fun saveCurrentCart() {
    tempData.setCurrentCart(cart.value)
    Log.i("CartViewModel", "saveCurrentCart: ${cart.value}")
  }

  private suspend fun getCurrentCart() {
    val currentCartJson = tempData.getCurrentCart()
    Log.i("CartViewModel", "getCurrentCart: $currentCartJson")
    if (currentCartJson.isNotEmpty()) {
      val currentCart = Gson().fromJson(currentCartJson, Cart::class.java)
      _cart.emit(currentCart)
    } else {
      val newCart = Cart()
      _cart.emit(newCart)
      Log.i("CartViewModel", "newCart $newCart")
      saveCurrentCart()
    }
  }

  fun addItem(product: Product) {
    _cart.value.apply {
      products.add(product)
      totalPrice = products.sum()
    }
  }

  private fun cleanCart() {
    _cart.value = Cart()
    tempData.setCurrentCart(_cart.value)
  }

  fun removeItem(product: Product) {
    _cart.value.products.remove(product)
  }

  fun setBalance(value: Double) {
    _cart.value.balance = value
  }
}