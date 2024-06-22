package br.com.gabrielmorais.cartcheck.ui.cart.components

import androidx.compose.foundation.clickable
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
fun BalanceHeader(
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
  title: String,
  value: String
) {
  Row(
    modifier
      .fillMaxWidth()
      .clickable(
        enabled = true,
        onClick = { onClick() }),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.SpaceAround,
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

@Preview(showBackground = true)
@Composable
fun ListHeaderPreview() {
  CartCheckTheme {
    Surface {
      BalanceHeader(title = "Saldo", value = "300.00")
    }
  }
}