package br.com.gabrielmorais.cartcheck.ui.cart_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.utils.mockProductList
import br.com.gabrielmorais.cartcheck.utils.sum
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency
import java.util.UUID

@Composable
fun ProductList(
  modifier: Modifier = Modifier,
  products: List<Product>
) {

  LazyColumn {
    item {
      Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        Text(
          text = "Subtotal",
          style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
        )
        Text(
          text = products.sum().toBrazilianCurrency(),
          style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)
        )
      }
      Spacer(modifier = Modifier.padding(10.dp))
    }

    itemsIndexed(
      items = products,
      key = { i, p -> UUID.randomUUID().toString() },
      itemContent = { index, product ->
        Card(
          Modifier.fillMaxWidth(),
          shape = RectangleShape,
        ) {
          Row(
            Modifier
              .fillMaxWidth()
              .background(if (index % 2 == 0) Color.Gray else Color.White),
            horizontalArrangement = Arrangement.SpaceAround,
          ) {
            Text(text = product.description, fontSize = 25.sp)
            Text(
              text = product.price.toBrazilianCurrency(),
              fontSize = 25.sp,
              textAlign = TextAlign.End
            )
          }
        }

      })
  }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
  ProductList(products = mockProductList)
}