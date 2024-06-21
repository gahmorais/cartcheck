package br.com.gabrielmorais.cartcheck.ui.history_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.ui.cart_page.components.containerBackgroundColor
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.formatDate
import br.com.gabrielmorais.cartcheck.utils.mockCartList
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency


@Composable
fun CartRow(
  index: Int,
  cart: Cart,
  onClick: () -> Unit = {}
) {

  Card(
    shape = RectangleShape,
    colors = CardDefaults.cardColors(
      containerColor = containerBackgroundColor(index)
    )
  ) {
    Row(
      modifier = Modifier
        .clickable(onClick = onClick)
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.fillMaxWidth(.65F)) {
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = cart.id.substringBefore("-"),
          style = TextStyle(fontSize = 25.sp)
        )
        Text(
          modifier = Modifier.fillMaxWidth(),
          style = TextStyle(fontSize = 25.sp),
          text = formatDate(cart.date)
        )
      }
      Text(
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(fontSize = 25.sp, textAlign = TextAlign.Center),
        text = cart.totalPrice.toBrazilianCurrency()
      )
    }
  }
}


@Composable
@Preview(showBackground = true)
fun CartRowPreview() {
  CartCheckTheme {
    Surface {
      CartRow(index = 1, cart = mockCartList[0].copy(totalPrice = 999.99))
    }
  }
}
