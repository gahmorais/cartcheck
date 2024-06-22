package br.com.gabrielmorais.cartcheck.ui.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.cart.components.ListRow
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockCartList
import br.com.gabrielmorais.cartcheck.utils.sum
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetails(
  modifier: Modifier = Modifier,
  cart: Cart,
  onBackClick: () -> Unit = {}
) {
  Scaffold(
    topBar = {
      AppBar(
        title = stringResource(id = R.string.text_back),
        onClick = onBackClick
      )
    }
  ) { paddingValues ->
    Column(
      modifier = modifier
        .padding(paddingValues)
        .fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier
          .fillMaxWidth()
          .fillMaxHeight(.94F),
          verticalArrangement = Arrangement.spacedBy(4.dp)) {
          itemsIndexed(
            items = cart.products,
            key = { _: Int, item: Product -> item.id }
          ) { i, product ->
            ListRow(index = i, product = product)
          }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
          Footer(cart)
        }
      }
    }
  }
}

@Composable
private fun Footer(cart: Cart) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = stringResource(R.string.text_total),
      style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
    )
    Text(
      text = cart.products.sum().toBrazilianCurrency(),
      style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
    )
  }
}

//item {
//  Row(verticalAlignment = Alignment.CenterVertically) {
//    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
//    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
//    Text(
//      text = stringResource(R.string.text_back),
//      style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
//    )
//  }
//}


@Preview(showBackground = true)
@Composable
fun CartDetailsPreview() {
  CartCheckTheme {
    Surface {
      CartDetails(cart = mockCartList[0])
    }
  }
}