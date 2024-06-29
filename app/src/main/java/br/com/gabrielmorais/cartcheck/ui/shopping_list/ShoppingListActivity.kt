package br.com.gabrielmorais.cartcheck.ui.shopping_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.gabrielmorais.cartcheck.ui.shopping_list.screen.ShoppingListScreen
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingListActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CartCheckTheme {
        Surface {
          val viewModel: ShoppingListViewModel by viewModel()
          val products by viewModel.products.collectAsState()
          ShoppingListScreen(
            products = products,
            addProduct = {
              viewModel.addProduct(it)
            },
            removeProduct = {
              viewModel.removeProduct(it)
            },
            updateProduct = {
              viewModel.updateProduct(it)
            }
          )
        }
      }
    }
  }
}