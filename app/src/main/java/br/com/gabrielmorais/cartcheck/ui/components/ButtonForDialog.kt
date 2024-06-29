package br.com.gabrielmorais.cartcheck.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme

@Composable
fun ButtonForDialog(
  modifier: Modifier = Modifier,
  isEnable: Boolean = true,
  onClick: () -> Unit,
  content: @Composable (RowScope.() -> Unit)
) {
  OutlinedButton(
    modifier = modifier,
    enabled = isEnable,
    shape = RoundedCornerShape(8.dp),
    onClick = onClick,
    content = content
  )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ButtonForDialogPreview() {
  CartCheckTheme {
    Surface {
      ButtonForDialog(onClick = {}) {
        Text(text = "Exemplo")
      }
    }
  }
}