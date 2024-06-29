package br.com.gabrielmorais.cartcheck.utils

import android.content.Context
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import com.google.gson.Gson

private const val PREFERENCES_FILES = "preferences"
private const val CURRENT_CART = "current_cart"
private const val SHOPPING_LIST = "lista_compras"

class TempData(context: Context) {
  private val preferences = context.getSharedPreferences(PREFERENCES_FILES, Context.MODE_PRIVATE)
  private val editor = preferences.edit()

  fun setCurrentCart(cart: Cart) {
    val tempCart = Gson().toJson(cart)
    editor.putString(CURRENT_CART, tempCart)
    editor.commit()
  }

  fun getCurrentCart(): String {
    return preferences.getString(CURRENT_CART, "") ?: ""
  }

  fun setShoppingList(products: List<Product>) {
    val shoppingList = Gson().toJson(products)
    editor.putString(SHOPPING_LIST, shoppingList)
    editor.commit()
  }

  fun getShoppingList(): String {
    return preferences.getString(SHOPPING_LIST, "") ?: ""
  }
}