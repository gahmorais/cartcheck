package br.com.gabrielmorais.cartcheck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.gabrielmorais.cartcheck.R

class AddItemDialogState() {
  var price by mutableStateOf(1.0)
    private set
  var description by mutableStateOf("")
    private set

  var quantity by mutableStateOf(1)
    private set

  val onPriceChange: (Double) -> Unit = {
    price = it
  }

  val onDescriptionChange: (String) -> Unit = {
    description = it
  }

  val onQuantityChange: (Int) -> Unit = {
    quantity = it
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
  uiState: AddItemDialogState = AddItemDialogState(),
  onConfirm: (AddItemDialogState) -> Unit = {},
  onDismiss: () -> Unit = {}
) {
  Dialog(onDismissRequest = { onDismiss() }) {
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
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.text_add_item),
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
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
        value = uiState.quantity.toString(), onValueChange = {
          try {
            uiState.onQuantityChange(it.toInt())
          } catch (e: Exception) {
            uiState.onQuantityChange(0)
          }
        })
      TextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(text = stringResource(R.string.text_price)) },
        value = uiState.price.toString(), onValueChange = {
          try {
            uiState.onPriceChange(it.toDouble())
          } catch (e: Exception) {

          }
        }
      )
      Row {
        TextButton(
          modifier = Modifier.fillMaxWidth(0.5F),
          onClick = {
            onConfirm(uiState)
          }) {
          Text(text = stringResource(R.string.text_confirm))
        }
        TextButton(
          modifier = Modifier.fillMaxWidth(),
          onClick = { onDismiss() }) {
          Text(text = stringResource(R.string.text_cancel))
        }
      }
      LaunchedEffect(Unit) {
        focusRequester.requestFocus()
      }
    }
  }
}


@Preview(showBackground = true, backgroundColor = 555333)
@Composable
fun AddItemDialogPreview() {
  AddItemDialog()
}