package br.com.gabrielmorais.cartcheck.ui.shopping_list.screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.cart.components.BackgroundSwipeToDismiss
import br.com.gabrielmorais.cartcheck.ui.shopping_list.components.AddProductDialog
import br.com.gabrielmorais.cartcheck.ui.shopping_list.components.AddProductDialogState
import br.com.gabrielmorais.cartcheck.ui.shopping_list.components.ProductRow
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList


@OptIn(
  ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
  ExperimentalFoundationApi::class
)
@Composable
fun ShoppingListScreen(
  modifier: Modifier = Modifier,
  products: List<Product>,
  addProduct: (Product) -> Unit,
  removeProduct: (Product) -> Unit,
  updateProduct: (Product) -> Unit
) {
  var showAddProduct by remember {
    mutableStateOf(false)
  }
  var addProductDialogState = AddProductDialogState()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = stringResource(id = R.string.text_shopping_list)) },
        actions = {
          TextButton(onClick = {}) {
            Text(
              modifier = Modifier.padding(horizontal = 16.dp),
              text = stringResource(R.string.text_start_buy),
              style = MaterialTheme.typography.titleMedium
            )
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        shape = RoundedCornerShape(8.dp),
        onClick = { showAddProduct = true }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)

      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = modifier.padding(paddingValues),
      verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
      itemsIndexed(products) { i, product ->
        val dismissState = rememberDismissState(
          confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
              removeProduct(product)
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
            ProductRow(
              index = i,
              product = product,
              onClick = {
                addProductDialogState = AddProductDialogState(it)
                showAddProduct = true
              }
            )
          }
        )
      }

    }

    if (showAddProduct) {
      AddProductDialog(
        uiState = addProductDialogState,
        onDismiss = {
          showAddProduct = false
          addProductDialogState = AddProductDialogState()
        },
        onConfirm = { state ->
          val currentProduct = state.getProduct()
          if (currentProduct != null) {
            val updatedProduct = currentProduct.copy(
              description = state.description,
              quantity = state.quantity.toInt()
            )
            updateProduct(updatedProduct)
          } else {
            val product = Product(
              description = state.description,
              quantity = state.quantity.toInt()
            )
            addProduct(product)
          }
          showAddProduct = false
          addProductDialogState = AddProductDialogState()
        }
      )
    }
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun ShoppingListScreenPreview() {
  CartCheckTheme {
    Surface {
      ShoppingListScreen(
        products = mockProductList,
        addProduct = {},
        removeProduct = {},
        updateProduct = {}
      )
    }
  }
}