package br.com.gabrielmorais.cartcheck.ui.cart_page

import androidx.lifecycle.ViewModel
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import br.com.gabrielmorais.cartcheck.utils.TempData
import br.com.gabrielmorais.cartcheck.utils.sum
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class CartViewModel(
  private val cartRepository: CartRepository,
  private val tempData: TempData
) : ViewModel() {
  private val _cart = MutableStateFlow(Cart())
  val cart = _cart.asStateFlow()

  private suspend fun saveCart() = try {
    cartRepository.save(cart.value)
  } catch (e: Exception) {
    e.printStackTrace()
  }

  suspend fun finishPurchase() {
    saveCart()
    cleanCart()
  }

  fun saveCartState() {
    tempData.setCurrentCart(cart.value)
    Timber.i("saveCurrentCart: ${cart.value}")
  }

  fun loadCurrentCart() {
    val currentCartJson = tempData.getCurrentCart()
    Timber.d("getCurrentCart: $currentCartJson")
    val cart = if (currentCartJson.isNotEmpty()) {
      Gson().fromJson(currentCartJson, Cart::class.java)
    } else {
      Cart()
    }
    _cart.update { cart }
  }

  fun addItem(product: Product) {
    _cart.update { currentCart ->
      currentCart.copy(
        products = _cart.value.products + product,
        totalPrice = _cart.value.products.sum()
      )
    }
  }

  fun updateItem(product: Product) {
    val products = _cart.value.products.toMutableList()
    val index = products.indexOfFirst { it.id == product.id }
    Timber.d("Item atualizado: $product")
    products[index] = product
    Timber.d("Nova lista $products")
    _cart.update { it.copy(products = products.toList()) }
  }

  private fun cleanCart() {
    _cart.update { Cart() }
    tempData.setCurrentCart(_cart.value)
  }

  fun removeItem(product: Product) {
    _cart.update { currentCart ->
      val updateProducts = currentCart.products.toMutableList().apply {
        remove(product)
      }
      currentCart.copy(
        products = updateProducts,
        totalPrice = updateProducts.sumOf { it.price }
      )
    }
  }

  fun setBalance(value: Double) {
    _cart.update { it.copy(balance = value) }
  }
}