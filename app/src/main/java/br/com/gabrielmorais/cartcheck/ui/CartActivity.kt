package br.com.gabrielmorais.cartcheck.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme

class CartActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var showAddItem by remember { mutableStateOf(false) }
      CartCheckTheme() {
        Scaffold(
          topBar = {
            TopAppBar(title = { Text(text = "Nova compra") },
              actions = {
                IconButton(onClick = {
                  showAddItem = true
                }) {
                  Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
              })
          },
        ) { paddingValues ->
          LazyColumn(Modifier.padding(paddingValues)) {

          }
        }
        if (showAddItem) {
          AddItemDialog(
            onConfirm = {},
            onDismiss = {
              showAddItem = false
            }
          )
        }
      }
    }
  }
}