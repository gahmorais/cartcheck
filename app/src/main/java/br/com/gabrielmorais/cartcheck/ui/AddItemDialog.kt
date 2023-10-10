package br.com.gabrielmorais.cartcheck.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

class AddItemDialogState() {
  var price by mutableStateOf(0.0)
    private set
  var description by mutableStateOf("")
    private set

  var quantity by mutableStateOf(0)
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
    Column(
      Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Adicionar Item",
        textAlign = TextAlign.Center,
        style = TextStyle(fontSize = 20.sp)
      )
      TextField(
        label = { Text(text = "Descrição do produto") },
        value = uiState.description,
        onValueChange = uiState.onDescriptionChange
      )
      TextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(text = "Quantidade") },
        value = uiState.quantity.toString(), onValueChange = {
          uiState.onQuantityChange(it.toInt())
        })
      TextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text(text = "Preço") },
        value = uiState.price.toString(), onValueChange = {
          uiState.onPriceChange(it.toDouble())
        }
      )
      Row {
        TextButton(
          modifier = Modifier.fillMaxWidth(0.5F),
          onClick = {
            onConfirm(uiState)
          }) {
          Text(text = "Confirmar")
        }
        TextButton(
          modifier = Modifier.fillMaxWidth(),
          onClick = { onDismiss() }) {
          Text(text = "Cancelar")
        }
      }
    }
  }
}


@Preview(showBackground = true, backgroundColor = 555333)
@Composable
fun AddItemDialogPreview() {
  AddItemDialog()
}