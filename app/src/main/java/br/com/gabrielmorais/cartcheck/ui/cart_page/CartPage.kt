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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.di.mainModule
import br.com.gabrielmorais.cartcheck.di.viewModelModule
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.AddItemDialog
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.AddItemDialogState
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.EditBalanceDialog
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(viewModel: CartViewModel = koinViewModel()) {
  val cart by viewModel.cart.collectAsState()
  var showAddItem by remember { mutableStateOf(false) }
  var showEditBalance by remember { mutableStateOf(false) }
  var addItemDialogState = AddItemDialogState()
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val scope = rememberCoroutineScope()
  DisposableEffect(key1 = lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      if (event == Lifecycle.Event.ON_STOP) {
        viewModel.saveCartState()
      }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose {
      lifecycleOwner.lifecycle.removeObserver(observer)
    }
  }

  LaunchedEffect(key1 = "init") {
    viewModel.loadCurrentCart()
  }

  CartCheckTheme {
    Scaffold(
      topBar = {
        AppBar {
          scope.launch(Dispatchers.IO) {
            viewModel.finishPurchase()
          }
        }
      },
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
        onEditBalance = {
          showEditBalance = true
        },
        onEditProduct = { product ->
          showAddItem = true
          addItemDialogState = AddItemDialogState(product)
        },
        deleteItem = { product ->
          viewModel.removeItem(product)
          Toast.makeText(context, "Item removido", Toast.LENGTH_SHORT).show()
        }
      )

    }
    if (showAddItem) {
      AddItemDialog(
        uiState = addItemDialogState,
        onConfirm = { state ->
          val newProduct = state.toProduct()
          val oldProduct = state.getProduct()
          if (oldProduct != null) {
            val updatedProduct = oldProduct.copy(
              description = state.description,
              price = state.price.toDouble(),
              quantity = state.quantity.toInt()
            )
            viewModel.updateItem(updatedProduct)
          } else {
            viewModel.addItem(product = newProduct)
          }
          showAddItem = false
          addItemDialogState = AddItemDialogState()
        },
        onDismiss = {
          showAddItem = false
          addItemDialogState = AddItemDialogState()
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
private fun AppBar(onClick: () -> Unit = {}) {
  val context = LocalContext.current
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
        onClick = onClick
      ) {
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


@Composable
@Preview(showBackground = true)
fun CartPagePreview() {
  KoinApplication(application = {
    modules(viewModelModule, mainModule)
  }) {
    CartPage()
  }
}