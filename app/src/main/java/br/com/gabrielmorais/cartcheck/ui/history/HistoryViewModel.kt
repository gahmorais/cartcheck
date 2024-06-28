package br.com.gabrielmorais.cartcheck.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class HistoryViewModel(private val cartRepository: CartRepository) : ViewModel() {

  init {
    viewModelScope.launch {
      loadCartsFromDatabase()
    }
  }

  private val _carts = MutableStateFlow<List<Cart>>(listOf())
  val carts = _carts.asStateFlow()
  private suspend fun loadCartsFromDatabase() = try {
    val carts = cartRepository.getAll()
    Timber.d("Carrinhos $carts")
    _carts.update { carts }
  } catch (e: Exception) {
    e.printStackTrace()
  }
}