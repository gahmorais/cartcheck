package br.com.gabrielmorais.cartcheck.ui.cart_page.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun ListHeader(
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
      style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
    )
    Text(
      text = value,
      style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)
    )
  }
}