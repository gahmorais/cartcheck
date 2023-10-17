package br.com.gabrielmorais.cartcheck.ui.cart_page

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.components.AddItemDialog
import br.com.gabrielmorais.cartcheck.ui.components.EditBalanceDialog
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.sum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class CartActivity : ComponentActivity() {
  private val viewModel: CartViewModel by viewModel()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val products = viewModel.products.collectAsState()
      val balance = viewModel.balance.collectAsState()
      var showAddItem by remember { mutableStateOf(false) }
      var showEditBalance by remember { mutableStateOf(false) }
      CartCheckTheme {
        Scaffold(
          topBar = {
            TopAppBar(title = {
              Text(
                text = getString(R.string.text_new_sale),
                style = TextStyle(
                  fontSize = 30.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                ),
              )
            },
              actions = {
                TextButton(
                  onClick = {
                    val cart = Cart(
                      date = LocalDate.now().toEpochDay(),
                      products = products.value,
                      totalPrice = products.value.sum(),
                      balance = balance.value
                    )
                    lifecycleScope.launch(Dispatchers.IO) {
                      viewModel.apply {
                        saveCart(cart)
                        cleanCart()
                      }
                    }
                  }) {
                  Text(
                    text = "Salvar",
                    style = TextStyle(
                      fontWeight = FontWeight.Bold,
                      fontSize = 20.sp,
                      color = MaterialTheme.colorScheme.secondary
                    )
                  )
                }
              })
          },
          floatingActionButton = {
            FloatingActionButton(onClick = { showAddItem = true }) {
              Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
          }
        ) { paddingValues ->
          ProductList(
            modifier = Modifier.padding(paddingValues),
            balance = balance.value,
            products = products,
            onClick = {
              showEditBalance = true
            },
            deleteItem = { product ->
              viewModel.removeItem(product)
              Toast.makeText(this@CartActivity, "Item removido", Toast.LENGTH_SHORT).show()
            }
          )
        }
        if (showAddItem) {
          AddItemDialog(
            onConfirm = { state ->
              val product = Product(state.description, state.price, state.quantity)
              viewModel.addItem(product = product)
              showAddItem = false
            },
            onDismiss = {
              showAddItem = false
            }
          )
        }
        if (showEditBalance) {
          EditBalanceDialog(
            onConfirm = {
              viewModel.setBalance(it)
              showEditBalance = false
            },
            onDismiss = { showEditBalance = false }
          )
        }
      }
    }
  }
}