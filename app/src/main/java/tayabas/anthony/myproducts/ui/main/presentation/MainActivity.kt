package tayabas.anthony.myproducts.ui.main.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tayabas.anthony.myproducts.common.State
import tayabas.anthony.myproducts.data.remote.dto.ProductResponse
import tayabas.anthony.myproducts.ui.product.ProductListPage
import tayabas.anthony.myproducts.ui.main.viewmodel.ProductViewModel
import tayabas.anthony.myproducts.ui.theme.MyProductsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState = productViewModel.product.collectAsState()
            MainScreen(uiState = uiState.value)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                productViewModel.getProducts()
            }
        }
    }
}

@Composable
fun MainScreen(uiState: State<Any>) {
    val context = LocalContext.current
    var isLoading = remember {
        true
    }

    MyProductsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState) {
                    is State.DataState -> {
                        val productList = (uiState.data as ProductResponse)
                        Log.d("MainScreen", "Product List Size: ${productList.size}")
                        ProductListPage(productList = productList)
                        isLoading = false
                    }

                    is State.ErrorState -> {
                        Log.e("MainScreen", "Error encountered: ${uiState.exception.message}")
                        Toast.makeText(context, "Operation Failed", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }

                    State.LoadingState -> {
                        Log.d("MainScreen", "Loading Product List")
                        isLoading = true
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .padding(80.dp)
                    )
                }
            }
        }
    }
}