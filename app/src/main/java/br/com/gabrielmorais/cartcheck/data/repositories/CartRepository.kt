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

  suspend fun getById(id: String) = try {
    cartDao.getById(id)
  } catch (e: Exception) {
    throw e
  }


  fun getAll(): List<Cart> = try {
    cartDao.getAllCarts()
  } catch (e: Exception) {
    throw e
  }

  fun delete(cart: Cart) = try {
    cartDao.delete(cart)
  } catch (e: Exception) {
    throw e
  }

}