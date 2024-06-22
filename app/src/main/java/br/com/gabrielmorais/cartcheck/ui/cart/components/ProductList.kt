package br.com.gabrielmorais.cartcheck.ui.cart.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProductList(
  modifier: Modifier = Modifier,
  products: List<Product>,
  deleteItem: (p: Product) -> Unit = {},
  onEditProduct: (Product) -> Unit = {}
) {
  LazyColumn(modifier = modifier.fillMaxSize()) {
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
        dismissThresholds = { _ ->
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
      ProductList(products = mockProductList.subList(0, 3))
    }
  }
}