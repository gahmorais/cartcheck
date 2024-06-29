package br.com.gabrielmorais.cartcheck.ui.shopping_list

import androidx.lifecycle.ViewModel
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.utils.TempData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class ShoppingListViewModel(
  private val tempData: TempData
) : ViewModel() {

  private val _products = MutableStateFlow(getShoppingList())
  val products = _products.asStateFlow()

  fun addProduct(product: Product) {
    _products.update { it + product }
  }

  fun removeProduct(product: Product) {
    val productList = _products.value.toMutableList()
    productList.remove(product)
    _products.update { productList }
  }

  fun updateProduct(product: Product) {
    val productList = _products.value.toMutableList()
    val index = productList.indexOfFirst { it.id == product.id }
    Timber.d("Item atualizado: $product")
    productList[index] = product
    Timber.d("Nova lista $products")
    _products.update { productList }
  }

  fun saveShoppingList() {
    tempData.setShoppingList(_products.value)
  }

  private fun getShoppingList(): List<Product> {
    val shoppingList = tempData.getShoppingList()
    return if (shoppingList.isEmpty()) {
      listOf()
    } else {
      val typeToken = object : TypeToken<List<Product>>() {}.type
      Gson().fromJson(shoppingList, typeToken)
    }
  }


}