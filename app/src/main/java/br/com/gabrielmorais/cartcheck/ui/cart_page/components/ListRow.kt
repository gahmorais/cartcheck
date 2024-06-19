package br.com.gabrielmorais.cartcheck.ui.cart_page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency


@Composable
fun ListRow(
  index: Int,
  product: Product
) {
  Card(
    Modifier
      .fillMaxSize(),
    shape = RectangleShape,
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .background(if (index % 2 == 0) Color.Gray.copy(alpha = 0.2F) else Color.White)
        .padding(5.dp),
      horizontalArrangement = Arrangement.SpaceAround,
    ) {
      Text(
        text = product.description,
        fontSize = 25.sp
      )
      Text(
        text = product.quantity.toString(),
        fontSize = 25.sp,
        textAlign = TextAlign.End
      )
      Text(
        text = product.price.toBrazilianCurrency(),
        fontSize = 25.sp,
        textAlign = TextAlign.End
      )
    }
  }
}