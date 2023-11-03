package br.com.gabrielmorais.cartcheck.utils

import android.content.Context

private const val PREFERENCES_FILES = "preferences"
private const val CURRENT_CART = "current_cart"

class TempData(context: Context) {
  private val preferences = context.getSharedPreferences(PREFERENCES_FILES, Context.MODE_PRIVATE)
  private val editor = preferences.edit()

  fun setCurrentCart(id: String) {
    editor.putString(CURRENT_CART, id)
    editor.commit()
  }

  fun getCurrentCart(): String {
    return preferences.getString(CURRENT_CART, "") ?: ""
  }

}