package br.com.gabrielmorais.cartcheck.ui.cart.screen

import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.di.mainModule
import br.com.gabrielmorais.cartcheck.di.viewModelModule
import br.com.gabrielmorais.cartcheck.ui.cart.CartViewModel
import br.com.gabrielmorais.cartcheck.ui.cart.components.AddItemDialog
import br.com.gabrielmorais.cartcheck.ui.cart.components.AddItemDialogState
import br.com.gabrielmorais.cartcheck.ui.cart.components.EditBalanceDialog
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.sum
import br.com.gabrielmorais.cartcheck.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(viewModel: CartViewModel = koinViewModel()) {
  val cart by viewModel.cart.collectAsState()
  var showAddItem by remember { mutableStateOf(false) }
  var showEditBalance by remember { mutableStateOf(false) }
  var addItemDialogState = AddItemDialogState()
  val lifecycleOwner = LocalLifecycleOwner.current
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val message by viewModel.message.collectAsState(initial = "")

  DisposableEffect(key1 = lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      when (event) {
        Lifecycle.Event.ON_STOP -> {
          Timber.d("Encerrando APP")
          viewModel.saveCartState()
        }

        Lifecycle.Event.ON_RESUME -> {
          if (cart.balance == 0.0) {
            showEditBalance = true
          }
        }

        else -> {}
      }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose {
      lifecycleOwner.lifecycle.removeObserver(observer)
    }
  }


  LaunchedEffect(key1 = message) {
    if (message.isNotEmpty()) {
      context.toast(message)
    }
  }

  LaunchedEffect(key1 = "init") {
    viewModel.loadCurrentCart()
  }

  var isFirstRender by remember {
    mutableStateOf(true)
  }
  LaunchedEffect(cart) {
    if (isFirstRender && cart.balance == 0.0) {
      showEditBalance = true
      isFirstRender = false
    }
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
        FloatingActionButton(
          modifier = Modifier.offset(y = (-30).dp),
          onClick = { showAddItem = true }) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
      },
    ) { paddingValues ->
      if (cart.products.isEmpty()) {
        EmptyCartPage(
          modifier = Modifier.padding(paddingValues),
          balance = cart.balance,
          onEditBalance = {
            showEditBalance = true
          }
        )
      } else {
        val resultBalance = cart.balance - cart.products.sum()
        ShoppingListScreen(
          modifier = Modifier.padding(paddingValues),
          products = cart.products,
          balance = resultBalance,
          onEditBalance = {
            showEditBalance = true
          },
          onEditProduct = { product ->
            showAddItem = true
            addItemDialogState = AddItemDialogState(product)
          },
          onDeleteProduct = { product ->
            viewModel.removeItem(product)
          },
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
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar(onClick: () -> Unit = {}) {
  val context = LocalContext.current
  TopAppBar(title = {
    Text(
      text = context.getString(R.string.text_new_sale),
      style = MaterialTheme.typography.headlineMedium.copy(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
      ),
    )
  },
    actions = {
      TextButton(onClick = onClick) {
        Text(
          text = stringResource(R.string.text_save),
          style = MaterialTheme.typography.headlineSmall
            .copy(
              color = MaterialTheme.colorScheme.secondary
            ),
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