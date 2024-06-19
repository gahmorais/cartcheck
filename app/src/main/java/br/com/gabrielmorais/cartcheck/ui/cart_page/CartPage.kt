package br.com.gabrielmorais.cartcheck.ui.cart_page

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.components.AddItemDialog
import br.com.gabrielmorais.cartcheck.ui.components.EditBalanceDialog
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(viewModel: CartViewModel = koinViewModel()) {
  val cart by viewModel.cart.collectAsState()
  var showAddItem by remember { mutableStateOf(false) }
  var showEditBalance by remember { mutableStateOf(false) }
  val context = LocalContext.current
  CartCheckTheme {
    Scaffold(
      topBar = { AppBar() },
      floatingActionButton = {
        FloatingActionButton(onClick = { showAddItem = true }) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
      }
    ) { paddingValues ->
      ProductList(
        modifier = Modifier.padding(paddingValues),
        balance = cart.balance,
        products = cart.products,
        onClick = {
          showEditBalance = true
        },
        deleteItem = { product ->
          viewModel.removeItem(product)
          Toast.makeText(context, "Item removido", Toast.LENGTH_SHORT).show()
        }
      )

    }
    if (showAddItem) {
      AddItemDialog(
        onConfirm = { state ->
          val product = Product(
            description = state.description,
            price = state.price,
            quantity = state.quantity
          )
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar(viewModel: CartViewModel = koinViewModel()) {
  val context = LocalContext.current
  val scope = rememberCoroutineScope()
  TopAppBar(title = {
    Text(
      text = context.getString(R.string.text_new_sale),
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
          scope.launch(Dispatchers.IO) {
            viewModel.apply {
              finishPurchase()
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
}