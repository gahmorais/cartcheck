package br.com.gabrielmorais.cartcheck.ui.history_page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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