package br.com.gabrielmorais.cartcheck.data.repositories

import androidx.annotation.WorkerThread
import br.com.gabrielmorais.cartcheck.data.dao.CartDao
import br.com.gabrielmorais.cartcheck.data.models.Cart

class CartRepository(private val cartDao: CartDao) {

  @WorkerThread
  suspend fun save(cart: Cart) = try {
    cartDao.insert(cart)
  } catch (e: Exception) {
    throw e
  }

  suspend fun getAll(): List<Cart> = try {
    cartDao.getAll()
  } catch (e: Exception) {
    throw e
  }

  fun delete(id: Int) = try {
    cartDao.delete(id)
  } catch (e: Exception) {
    throw e
  }

}