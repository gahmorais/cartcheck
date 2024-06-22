package br.com.gabrielmorais.cartcheck.ui.cart.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.cart.components.BalanceHeader
import br.com.gabrielmorais.cartcheck.ui.cart.components.FooterList
import br.com.gabrielmorais.cartcheck.ui.cart.components.ProductList
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList
import br.com.gabrielmorais.cartcheck.utils.sum
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency

@Composable
fun ShoppingListScreen(
  modifier: Modifier = Modifier,
  products: List<Product>,
  onEditBalance: () -> Unit = {},
  onEditProduct: (Product) -> Unit = {},
  onDeleteProduct: (Product) -> Unit = {},
  balance: Double
) {
  Box {
    Column(modifier.fillMaxSize()) {
      BalanceHeader(
        title = stringResource(R.string.text_balance),
        value = balance.toBrazilianCurrency(),
        onClick = { onEditBalance() }
      )
      ProductList(
        modifier = Modifier.fillMaxHeight(.95F),
        products = products,
        onEditProduct = onEditProduct,
        deleteItem = onDeleteProduct
      )
    }
    Box(
      modifier = Modifier.align(Alignment.BottomCenter)
    ) {
      FooterList(
        title = stringResource(R.string.text_subtotal),
        value = products.sum().toBrazilianCurrency()
      )
    }
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun ProductListScreenPreview() {
  CartCheckTheme {
    Surface {
      ShoppingListScreen(products = mockProductList.subList(0,3), balance = 400.0)
    }
  }
}