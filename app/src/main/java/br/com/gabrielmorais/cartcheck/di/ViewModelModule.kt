package br.com.gabrielmorais.cartcheck.di

import br.com.gabrielmorais.cartcheck.ui.cart_page.CartViewModel
import br.com.gabrielmorais.cartcheck.ui.history_page.HistoryViewModel
import br.com.gabrielmorais.cartcheck.ui.main_page.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { MainViewModel() }
  viewModel { CartViewModel(get(), get()) }
  viewModel { HistoryViewModel(get()) }
}