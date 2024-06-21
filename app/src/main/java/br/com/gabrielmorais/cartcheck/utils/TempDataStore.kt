package br.com.gabrielmorais.cartcheck.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.gabrielmorais.cartcheck.data.models.Cart
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TempDataStore(private val context: Context) {

  private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
  private val CURRENT_CART = stringPreferencesKey("current_cart")
  suspend fun setCurrentCart(cart: Cart) {
    val tempCart = Gson().toJson(cart)
    context.datastore.edit { prefs ->
      prefs[CURRENT_CART] = tempCart
    }
  }

  fun getCurrentCart(): Flow<Cart?> {
    return context.datastore.data.map { prefs ->
      val currentCartJson = prefs[CURRENT_CART]
      try {
        Gson().fromJson(currentCartJson, Cart::class.java)
      } catch (e: Exception) {
        null
      }
    }
  }


}