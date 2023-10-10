package br.com.gabrielmorais.cartcheck.ui.cart_page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.AddItemDialog
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity : ComponentActivity() {
  private val viewModel: CartViewModel by viewModel()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val products = viewModel.products.collectAsState()
      var showAddItem by remember { mutableStateOf(false) }
      CartCheckTheme() {
        Scaffold(
          topBar = {
            TopAppBar(title = { Text(text = "Nova compra") },
              actions = {
                IconButton(onClick = {
                  showAddItem = true
                }) {
                  Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
              })
          },
        ) { paddingValues ->
          ProductList(modifier = Modifier.padding(paddingValues), products = products.value)
        }
        if (showAddItem) {
          AddItemDialog(
            onConfirm = { state ->
              val product = Product(state.description, state.price, state.quantity)
              viewModel.add(product = product)
              showAddItem = false
            },
            onDismiss = {
              showAddItem = false
            }
          )
        }
      }
    }
  }
}