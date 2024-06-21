package br.com.gabrielmorais.cartcheck.ui.cart_page

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.FooterList
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.ListHeader
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.ListRow
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList
import br.com.gabrielmorais.cartcheck.utils.sum
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProductList(
  modifier: Modifier = Modifier,
  products: List<Product>,
  balance: Double = 0.0,
  deleteItem: (p: Product) -> Unit = {},
  onEditBalance: () -> Unit = {},
  onEditProduct: (Product) -> Unit = {}
) {
  CartCheckTheme {
    val result = balance - products.sum()
    Box(modifier = Modifier.fillMaxSize()) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(.95F)
      ) {
        ListHeader(
          modifier = modifier,
          title = stringResource(R.string.text_balance),
          value = result.toBrazilianCurrency(),
          onClick = { onEditBalance() }
        )
        LazyColumn(
          modifier = Modifier.fillMaxSize()
        ) {
          itemsIndexed(
            items = products,
            key = { _, p -> p.id }
          ) { i, product ->
            val dismissState = rememberDismissState(
              confirmStateChange = {
                if (it == DismissValue.DismissedToStart) {
                  deleteItem(product)
                  true
                } else {
                  false
                }
              }
            )
            SwipeToDismiss(
              modifier = Modifier
                .padding(vertical = 1.dp)
                .animateItemPlacement(),
              state = dismissState,
              background = { BackgroundSwipeToDismiss(state = dismissState) },
              directions = setOf(DismissDirection.EndToStart),
              dismissThresholds = { directions ->
                FractionalThreshold(0.66F)
              },
              dismissContent = {
                ListRow(
                  index = i,
                  product = product,
                  onClick = {
                    onEditProduct(product)
                  }
                )
              }
            )
          }
        }
      }
      Box(
        modifier = Modifier.align(Alignment.BottomCenter)
      ) {
        FooterList(
          title = "Subtotal",
          value = products.sum().toBrazilianCurrency()
        )
      }
    }

  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackgroundSwipeToDismiss(
  state: DismissState
) {
  val color = when (state.dismissDirection) {
    DismissDirection.EndToStart -> Color.Red
    else -> Color.Transparent
  }
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = color),
  ) {
    Icon(
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 10.dp)
        .size(25.dp),
      imageVector = Icons.Rounded.Delete,
      contentDescription = null
    )
  }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductListPreview() {
  CartCheckTheme {
    Surface {
      ProductList(products = mockProductList)
    }
  }
}