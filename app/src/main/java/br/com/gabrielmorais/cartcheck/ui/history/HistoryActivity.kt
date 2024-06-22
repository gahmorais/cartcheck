package br.com.gabrielmorais.cartcheck.ui.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.gabrielmorais.cartcheck.ui.history.screen.HistoryPage
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : ComponentActivity() {
  private val viewModel by viewModel<HistoryViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HistoryPage {
        finish()
      }
    }
  }
}