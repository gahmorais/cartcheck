package br.com.gabrielmorais.cartcheck.ui.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme


@Composable
fun FooterList(
  modifier: Modifier = Modifier,
  title: String,
  value: String
) {
  Row(
    modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    Text(
      text = title,
      style = MaterialTheme
        .typography
        .headlineMedium
        .copy(fontWeight = FontWeight.Bold)
    )
    Text(
      text = value,
      style = MaterialTheme
        .typography
        .headlineMedium
        .copy(fontWeight = FontWeight.Bold)
    )
  }
}

@Preview
@Composable
private fun FooterListPreview() {
  CartCheckTheme {
    Surface {
      FooterList(title = "Subtotal", value = "R$ 500.0")
    }
  }
}