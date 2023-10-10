package br.com.gabrielmorais.cartcheck.ui.main_page

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.ui.cart_page.CartActivity
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      CartCheckTheme {
        Scaffold(
          topBar = {
            TopAppBar(title = {
              Text(text = stringResource(id = R.string.app_name))
            })
          }
        ) { contentPadding ->
          Column(
            Modifier
              .fillMaxSize()
              .padding(contentPadding)
              .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Button(
              modifier = Modifier.fillMaxWidth(),
              onClick = {
                val intent = Intent(this@MainActivity, CartActivity::class.java)
                startActivity(intent)
              }) {
              Text(text = "Nova Compra", style = TextStyle(fontSize = 24.sp))
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
              modifier = Modifier.fillMaxWidth(),
              onClick = {

              }) {
              Text(text = "Histórico de compras", style = TextStyle(fontSize = 24.sp))
            }
          }
        }
      }
    }
  }
}
