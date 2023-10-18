package br.com.gabrielmorais.cartcheck.ui.history_page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabrielmorais.cartcheck.R
import br.com.gabrielmorais.cartcheck.ui.theme.CartCheckTheme
import br.com.gabrielmorais.cartcheck.utils.toBrazilianCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryActivity : ComponentActivity() {
  private val viewModel by viewModel<HistoryViewModel>()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var showDetails by remember {
        mutableStateOf(false)
      }
      var selectCart by remember {
        mutableStateOf(0)
      }
      CartCheckTheme {
        val carts = viewModel.carts.collectAsState().value
        Scaffold(
          topBar = {
            TopAppBar(
              title = {
                Text(
                  text = getString(R.string.text_history),
                  style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                  ),
                )
              }
            )
          }
        ) { paddingValues ->
          if (showDetails) {
            LazyColumn(
              modifier = Modifier.padding(paddingValues)
            ) {
              item {
                Row(
                  modifier = Modifier.clickable(onClick = {
                    showDetails = false
                  }),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                  Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                  Text(
                    text = "Voltar",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                  )
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
              }

              items(carts[selectCart].products) { product ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                  Text(
                    modifier = Modifier.fillMaxWidth(0.43F),
                    text = product.description,
                    style = TextStyle(fontSize = 25.sp)
                  )
                  Text(
                    modifier = Modifier.fillMaxWidth(0.05F),
                    text = product.quantity.toString(),
                    style = TextStyle(fontSize = 25.sp)
                  )
                  Text(
                    modifier = Modifier.fillMaxWidth(0.45F),
                    text = product.price.toBrazilianCurrency(),
                    style = TextStyle(fontSize = 25.sp)
                  )
                }
                Spacer(modifier = Modifier.padding(5.dp))
              }
            }
          } else {
            LazyColumn(Modifier.padding(paddingValues)) {
              itemsIndexed(carts) { i, cart ->
                Row(
                  Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = {
                      showDetails = true
                      selectCart = i
                    }),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = cart.id.toString(),
                    style = TextStyle(fontSize = 25.sp)
                  )
                  Text(
                    style = TextStyle(fontSize = 25.sp),
                    text = LocalDate.ofEpochDay(cart.date)
                      .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                  )
                  Text(
                    style = TextStyle(fontSize = 25.sp),
                    text = cart.totalPrice.toBrazilianCurrency()
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}