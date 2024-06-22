package br.com.gabrielmorais.cartcheck.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CartCheckTheme {
        Surface {
          MainScreen()
        }
      }
    }
  }
}
