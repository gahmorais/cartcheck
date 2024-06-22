package br.com.gabrielmorais.cartcheck.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielmorais.cartcheck.data.models.Cart
import br.com.gabrielmorais.cartcheck.data.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val cartRepository: CartRepository) : ViewModel() {
  init {
    viewModelScope.launch(Dispatchers.IO) {
      getAll()
    }
  }

  private val _carts = MutableStateFlow<List<Cart>>(listOf())
  val carts = _carts.asStateFlow()
  private fun getAll() = try {
    _carts.value = cartRepository.getAll()
  } catch (e: Exception) {
    e.printStackTrace()
  }
}