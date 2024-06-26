package br.com.gabrielmorais.cartcheck.ui.cart.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.isValidDouble

class EditBalanceDialogState() {
  var balance by mutableStateOf("")
    private set

  val onBalanceChange: (String) -> Unit = {
    balance = it
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBalanceDialog(
  uiState: EditBalanceDialogState = EditBalanceDialogState(),
  onConfirm: (Double) -> Unit = {},
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
      modifier = Modifier
        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(10.dp))
        .padding(15.dp)
        .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
      val isErrorBalance = uiState.balance.isNotEmpty() && !isValidDouble(uiState.balance)
      Text(
        text = stringResource(R.string.text_change_balance),
        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary)
      )
      TextField(
        modifier = Modifier
          .fillMaxWidth()
          .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        value = uiState.balance,
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(fontSize = 20.sp),
        isError = isErrorBalance,
        supportingText = {
          if (isErrorBalance) {
            Text(text = stringResource(id = R.string.text_invalid_value))
          }
        },
        onValueChange = uiState.onBalanceChange,
      )
      Button(
        shape = RoundedCornerShape(5.dp),
        enabled = isValidDouble(uiState.balance),
        onClick = { onConfirm(uiState.balance.toDouble()) }) {
        Text(
          text = stringResource(R.string.text_update),
          style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
      }

      LaunchedEffect(Unit) {
        focusRequester.requestFocus()
      }
    }

  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun EditBalanceDialogPreview() {
  CartCheckTheme {
    Surface {
      EditBalanceDialog()
    }
  }
}