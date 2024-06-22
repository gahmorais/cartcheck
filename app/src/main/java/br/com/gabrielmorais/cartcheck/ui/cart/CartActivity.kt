package br.com.gabrielmorais.cartcheck.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.gabrielmorais.cartcheck.ui.cart.screen.CartPage

class CartActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CartPage()
    }
  }
}