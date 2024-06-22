package br.com.gabrielmorais.cartcheck.ui.history.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.ui.history.HistoryViewModel
import br.com.gabrielmorais.cartcheck.ui.history.components.CartDetails
import br.com.gabrielmorais.cartcheck.ui.history.components.CartList
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryPage(
  viewModel: HistoryViewModel = koinViewModel(),
  onExit: () -> Unit = {}
) {
  CartCheckTheme {
    var selectCart by remember {
      mutableStateOf<Cart?>(null)
    }
    val carts = viewModel.carts.collectAsState().value
    selectCart?.let {
      CartDetails(
        cart = it,
        onBackClick = {
          selectCart = null
        }
      )
    } ?: CartList(
      carts = carts,
      onCartClick = { cart ->
        selectCart = cart
      },
      onClick = onExit
    )
  }
}
