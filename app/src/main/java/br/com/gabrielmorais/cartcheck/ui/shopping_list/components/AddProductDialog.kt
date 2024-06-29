package br.com.gabrielmorais.cartcheck.ui.shopping_list.components

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
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.data.models.Product
import br.com.gabrielmorais.cartcheck.ui.components.ButtonForDialog
import br.com.gabrielmorais.cartcheck.ui.components.TextFieldForInt
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.isValidInt

class AddProductDialogState(private val product: Product? = null) {
  var description by mutableStateOf(product?.description ?: "")
    private set
  var quantity by mutableStateOf(product?.quantity?.toString() ?: "")
    private set

  val onDescriptionChange: (String) -> Unit = {
    description = it
  }

  val onQuantityChange: (String) -> Unit = {
    quantity = it
  }

  fun getProduct() = product
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
  uiState: AddProductDialogState = AddProductDialogState(),
  onDismiss: () -> Unit = {},
  onConfirm: (AddProductDialogState) -> Unit = {}
) {
  val isButtonEnabled = uiState.description.isNotEmpty()
      && isValidInt(uiState.quantity)
  val focusRequester = FocusRequester()
  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(
      dismissOnBackPress = true,
      dismissOnClickOutside = true
    )
  ) {
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
        text = stringResource(R.string.text_add_product),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary)
      )

      TextField(
        modifier = Modifier.focusRequester(focusRequester),
        label = { Text(text = stringResource(R.string.text_product_description)) },
        value = uiState.description,
        onValueChange = uiState.onDescriptionChange,
        isError = uiState.description.isEmpty()
      )

      TextFieldForInt(
        value = uiState.quantity,
        onValueChange = uiState.onQuantityChange
      )

      Row(modifier = Modifier.fillMaxWidth()) {
        ButtonForDialog(
          modifier = Modifier.fillMaxWidth(0.4f),
          onClick = onDismiss
        ) {
          Text(text = stringResource(id = R.string.text_cancel))
        }
        Spacer(modifier = Modifier.size(8.dp))
        ButtonForDialog(
          modifier = Modifier.fillMaxWidth(),
          isEnable = isButtonEnabled,
          onClick = { onConfirm(uiState) }) {
          Text(text = stringResource(id = R.string.text_add))
        }
      }
    }
  }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AddProductDialogPreview() {
  CartCheckTheme {
    Surface {
      AddProductDialog()
    }
  }
}

