package br.com.gabrielmorais.cartcheck.ui.cart_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.FooterList
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.ListHeader
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.ListRow
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList
import br.com.gabrielmorais.cartcheck.utils.sum
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProductList(
  modifier: Modifier = Modifier,
  products: List<Product>,
  balance: Double = 0.0,
  deleteItem: (p: Product) -> Unit = {},
  onClick: () -> Unit = {}
) {
  CartCheckTheme {
    val result = balance - products.sum()
    LazyColumn(
      modifier = Modifier.fillMaxSize()
    ) {
      stickyHeader {
        ListHeader(
          modifier = modifier,
          title = stringResource(R.string.text_balance),
          value = result.toBrazilianCurrency(),
          onClick = { onClick() }
        )
        Spacer(modifier = Modifier.padding(10.dp))
      }

      itemsIndexed(
        items = products,
        key = { i: Int, p: Product -> p.hashCode() }
      ) { index, product ->
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
              index = index,
              product = product
            )
          }
        )
      }

      item {
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

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
  ProductList(products = mockProductList)
}