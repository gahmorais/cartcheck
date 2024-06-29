package br.com.gabrielmorais.cartcheck.di

import br.com.gabrielmorais.cartcheck.ui.cart.CartViewModel
import br.com.gabrielmorais.cartcheck.ui.history.HistoryViewModel
import br.com.gabrielmorais.cartcheck.ui.shopping_list.ShoppingListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { CartViewModel(get(), get()) }
  viewModel { HistoryViewModel(get()) }
  viewModel { ShoppingListViewModel(get()) }
}