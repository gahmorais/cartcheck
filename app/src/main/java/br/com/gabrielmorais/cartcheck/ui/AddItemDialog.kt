package br.com.gabrielmorais.cartcheck.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemDialog(
  onConfirm: () -> Unit = {},
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
        value = "",
        onValueChange = {}
      )
      TextField(
        label = { Text(text = "Quantidade") },
        value = "", onValueChange = {})
      TextField(
        label = { Text(text = "Preço") },
        value = "", onValueChange = {}
      )
      Row {
        TextButton(
          modifier = Modifier.fillMaxWidth(0.5F),
          onClick = { onConfirm() }) {
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