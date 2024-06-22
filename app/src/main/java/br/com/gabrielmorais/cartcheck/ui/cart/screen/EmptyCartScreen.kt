package br.com.gabrielmorais.cartcheck.ui.cart.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.ui.cart.components.BalanceHeader
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency

@Composable
fun EmptyCartPage(
  modifier: Modifier = Modifier,
  balance: Double,
  onEditBalance: () -> Unit
) {

  Box(
    modifier = modifier.fillMaxSize(),
  ) {
    BalanceHeader(
      title = stringResource(R.string.text_balance),
      value = balance.toBrazilianCurrency(),
      onClick = { onEditBalance() }
    )
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(id = R.drawable.empty_cart),
        contentDescription = null
      )
      Text(
        modifier = Modifier.padding(horizontal = 32.dp),
        text = stringResource(R.string.empty_cart_message),
        style = MaterialTheme.typography.headlineMedium.copy(
          textAlign = TextAlign.Center
        )
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun EmptyCartPagePreview() {
  CartCheckTheme {
    Surface {
      EmptyCartPage(
        balance = 300.00,
        onEditBalance = {}
      )
    }
  }
}