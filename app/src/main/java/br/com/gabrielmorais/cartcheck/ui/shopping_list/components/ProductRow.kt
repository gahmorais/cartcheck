package br.com.gabrielmorais.cartcheck.ui.shopping_list.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.cart.components.containerBackgroundColor
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.mockProductList

@Composable
fun ProductRow(
  modifier: Modifier = Modifier,
  index: Int,
  product: Product,
  onClick: (Product) -> Unit
) {
  Card(
    modifier = modifier.clickable(onClick = { onClick(product) }),
    shape = RectangleShape,
    colors = CardDefaults.cardColors(
      containerColor = containerBackgroundColor(i = index)
    )
  ) {
    Row(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)
        .height(65.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = product.description,
        style = MaterialTheme.typography.headlineSmall,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = product.quantity.toString(),
        style = MaterialTheme.typography.headlineSmall,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ProductListPreview() {
  CartCheckTheme {
    Surface {
      Column {
        (0..4).mapIndexed { _, n ->
          ProductRow(
            modifier = Modifier.fillMaxWidth(),
            index = n,
            product = mockProductList[n],
            onClick = {}
          )
        }
      }
    }
  }
}
