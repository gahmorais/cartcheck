package br.com.gabrielmorais.cartcheck.utils

import android.content.Context
import android.content.SharedPreferences
import br.com.gabrielmorais.cartcheck.data.models.Cart
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

  fun SharedPreferences.stringFlow(key: String, defaultValue: String): Flow<String> = callbackFlow {
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, changedKey ->
      if (key == changedKey) {
        trySend(prefs.getString(key, defaultValue) ?: defaultValue)
      }
    }

    trySend(getString(key, defaultValue) ?: defaultValue)
    preferences.registerOnSharedPreferenceChangeListener(listener)

    awaitClose { preferences.unregisterOnSharedPreferenceChangeListener(listener) }
  }
}