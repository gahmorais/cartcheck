package br.com.gabrielmorais.cartcheck.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.isValidDouble

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldForDouble(
  modifier: Modifier = Modifier,
  value: String,
  onValueChange: (String) -> Unit
) {
  val isError = value.isNotEmpty() && !isValidDouble(value)
  TextField(
    modifier = modifier,
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    label = { Text(text = stringResource(R.string.text_price)) },
    value = value, onValueChange = onValueChange,
    isError = isError,
    supportingText = {
      if (isError) {
        Text(text = stringResource(id = R.string.text_invalid_value))
      }
    }
  )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TextFieldForDoublePreview(){
  CartCheckTheme {
    Surface {
      TextFieldForDouble(value = "") {

      }
    }
  }
}