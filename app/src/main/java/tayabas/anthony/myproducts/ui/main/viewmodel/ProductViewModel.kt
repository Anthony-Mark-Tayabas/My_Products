package tayabas.anthony.myproducts.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tayabas.anthony.myproducts.common.NetworkUtils
import tayabas.anthony.myproducts.common.State
import tayabas.anthony.myproducts.data.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private val _products = MutableStateFlow<State<Any>>(State.LoadingState)
    val product get() = _products.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                _products.update { State.LoadingState }
                _products.update { State.DataState(productRepository.getProducts().body() ?: arrayListOf()) }
            } catch (e: Exception) {
                _products.update { NetworkUtils.resolveError(e) }
            }
        }
    }

}