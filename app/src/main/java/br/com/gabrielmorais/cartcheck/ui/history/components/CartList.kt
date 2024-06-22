package br.com.gabrielmorais.cartcheck.ui.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockCartList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartList(
  modifier: Modifier = Modifier,
  carts: List<Cart>,
  onCartClick: (Cart) -> Unit = {},
  onClick: () -> Unit = {}
) {
  stringResource(R.string.text_history)
  Scaffold(
    topBar = {
      AppBar(
        title = stringResource(R.string.text_history),
        onClick = onClick
      )
    }
  ) { paddingValues ->
    val sortedCarts = carts.sortedByDescending { it.date }
    LazyColumn(
      modifier = modifier
        .padding(paddingValues)
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      itemsIndexed(sortedCarts) { i, cart ->
        CartRow(index = i, cart = cart, onClick = { onCartClick(cart) })
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CartListPreview() {
  CartCheckTheme {
    Surface {
      CartList(carts = mockCartList)
    }
  }
}