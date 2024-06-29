package br.com.gabrielmorais.cartcheck.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.di.mainModule
import br.com.gabrielmorais.cartcheck.di.viewModelModule
import br.com.gabrielmorais.cartcheck.ui.cart.CartActivity
import br.com.gabrielmorais.cartcheck.ui.history.HistoryActivity
import br.com.gabrielmorais.cartcheck.ui.shopping_list.ShoppingListActivity
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import org.koin.compose.KoinApplication


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  modifier: Modifier = Modifier,
  context: Context = LocalContext.current
) {
  Scaffold(
    topBar = { AppBar() }
  ) { contentPadding ->
    Column(
      modifier
        .fillMaxSize()
        .padding(contentPadding)
        .padding(32.dp),
      verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      RectangleButton(title = stringResource(R.string.text_new_shop)) {
        context.openActivity(CartActivity::class.java)
      }
      RectangleButton(title = stringResource(R.string.text_shopping_history)) {
        context.openActivity(HistoryActivity::class.java)
      }
      RectangleButton(title = stringResource(R.string.text_shopping_list)) {
        context.openActivity(ShoppingListActivity::class.java)
      }
    }
  }
}

private fun <T> Context.openActivity(activity: Class<T>) {
  val intent = Intent(this, activity)
  startActivity(intent)
}

@Composable
private fun RectangleButton(
  modifier: Modifier = Modifier,
  title: String,
  onClick: () -> Unit
) {
  Button(
    modifier = modifier
      .fillMaxWidth(),
    onClick = onClick,
    shape = RoundedCornerShape(16.dp)
  ) {
    Text(
      modifier = Modifier.padding(vertical = 8.dp),
      text = title,
      style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center)
    )
  }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar() {
  TopAppBar(
    title = {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.app_name),
        style = TextStyle(
          fontSize = 30.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        ),
      )
    })
}


@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
  KoinApplication(moduleList = {
    listOf(mainModule, viewModelModule)
  }) {
    CartCheckTheme {
      Surface {
        MainScreen()
      }
    }
  }
}