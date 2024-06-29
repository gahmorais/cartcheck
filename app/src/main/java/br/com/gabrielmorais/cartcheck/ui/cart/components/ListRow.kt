package br.com.gabrielmorais.cartcheck.ui.cart.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency


@Composable
fun ListRow(
  index: Int,
  product: Product,
  onClick: () -> Unit = {}
) {
  Card(
    modifier = Modifier.clickable(onClick = onClick),
    colors = CardDefaults.cardColors(
      containerColor = containerBackgroundColor(index)
    ),
    shape = RectangleShape,
  ) {
    Row(
      Modifier
        .fillMaxWidth()
        .height(65.dp)
        .padding(horizontal = 16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.fillMaxWidth(.85F)) {
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = product.description,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme
            .typography
            .headlineSmall
        )

        Text(
          modifier = Modifier.fillMaxWidth(),
          text = product.price.toBrazilianCurrency(),
          style = MaterialTheme
            .typography
            .headlineSmall
            .copy(textAlign = TextAlign.Start),
        )
      }
      Text(
        text = product.quantity.toString(),
        style = MaterialTheme
          .typography
          .headlineSmall
          .copy(textAlign = TextAlign.Center),
      )
    }
  }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ListRowPreview() {
  CartCheckTheme {
    Surface {
      ListRow(index = 2, product = mockProductList[0])
    }
  }
}

@Composable
fun containerBackgroundColor(i: Int): Color {
  return if (i % 2 == 0) {
    MaterialTheme.colorScheme.primary
  } else {
    MaterialTheme.colorScheme.secondary
  }
}