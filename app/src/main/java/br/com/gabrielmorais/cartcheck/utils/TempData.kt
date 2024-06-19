package br.com.gabrielmorais.cartcheck.utils

import android.content.Context
import br.com.gabrielmorais.cartcheck.data.models.Cart
import com.google.gson.Gson

private const val PREFERENCES_FILES = "preferences"
private const val CURRENT_CART = "current_cart"

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

}