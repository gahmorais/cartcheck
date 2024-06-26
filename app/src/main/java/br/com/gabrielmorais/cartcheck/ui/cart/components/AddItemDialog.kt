package br.com.gabrielmorais.cartcheck.ui.cart.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.isValidDouble
import br.com.gabrielmorais.cartcheck.utils.isValidInt

class AddItemDialogState(private val product: Product? = null) {
  var price by mutableStateOf(product?.price?.toString() ?: "")
    private set

  var description by mutableStateOf(product?.description ?: "")
    private set

  var quantity by mutableStateOf(product?.quantity?.toString() ?: "")
    private set

  val onPriceChange: (String) -> Unit = {
    price = it
  }

  val onDescriptionChange: (String) -> Unit = {
    description = it
  }

  val onQuantityChange: (String) -> Unit = {
    quantity = it
  }

  fun getProduct(): Product? = product

  fun toProduct() = Product(
    description = description,
    price = price.toDouble(),
    quantity = quantity.toInt()
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
  uiState: AddItemDialogState = AddItemDialogState(),
  onConfirm: (AddItemDialogState) -> Unit = {},
  onDismiss: () -> Unit = {}
) {
  Dialog(
    onDismissRequest = { onDismiss() },
    properties = DialogProperties(
      dismissOnBackPress = true,
      dismissOnClickOutside = true
    )
  ) {
    val focusRequester = remember {
      FocusRequester()
    }
    Column(
      Modifier
        .background(
          color = MaterialTheme.colorScheme.background,
          shape = RoundedCornerShape(10.dp)
        )
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      val isErrorQuantity = uiState.quantity.isNotEmpty() && !isValidInt(uiState.quantity)
      val isErrorPrice = uiState.price.isNotEmpty() && !isValidDouble(uiState.price)
      val isButtonEnabled = isValidInt(uiState.quantity)
          && isValidDouble(uiState.price)
          && uiState.description.isNotEmpty()

      Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.text_add_product),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary)
      )

      TextField(
        modifier = Modifier.focusRequester(focusRequester),
        label = { Text(text = stringResource(R.string.text_product_description)) },
        value = uiState.description,
        onValueChange = uiState.onDescriptionChange
      )
      TextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(text = stringResource(R.string.text_quantity)) },
        value = uiState.quantity,
        onValueChange = uiState.onQuantityChange,
        isError = isErrorQuantity,
        supportingText = {
          if (isErrorQuantity) {
            Text(text = stringResource(id = R.string.text_invalid_value))
          }
        }
      )
      TextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(text = stringResource(R.string.text_price)) },
        value = uiState.price, onValueChange = {
          uiState.onPriceChange(it.replace(",", "."))
        },
        isError = isErrorPrice,
        supportingText = {
          if (isErrorPrice) {
            Text(text = stringResource(id = R.string.text_invalid_value))
          }
        }
      )
      Row {
        OutlinedButton(
          modifier = Modifier.fillMaxWidth(0.4F),
          shape = RoundedCornerShape(8.dp),
          onClick = { onDismiss() }) {
          Text(
            text = stringResource(R.string.text_cancel),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedButton(
          modifier = Modifier.fillMaxWidth(),
          enabled = isButtonEnabled,
          shape = RoundedCornerShape(8.dp),
          onClick = {
            onConfirm(uiState)
          }) {
          Text(
            text = stringResource(R.string.text_add),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
      LaunchedEffect(Unit) {
        focusRequester.requestFocus()
      }
    }

  }
}

@Preview
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddItemDialogPreview() {
  CartCheckTheme {
    Surface {
      AddItemDialog()
    }
  }
}