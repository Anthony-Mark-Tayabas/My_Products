package tayabas.anthony.myproducts.ui.main.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tayabas.anthony.myproducts.common.State
import tayabas.anthony.myproducts.ui.main.viewmodel.ProductViewModel
import tayabas.anthony.myproducts.ui.theme.MyProductsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    MyProductsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            when (uiState) {
                is State.DataState -> {
                    Toast.makeText(context, "Data: ${uiState.data}", Toast.LENGTH_SHORT).show()
                }

                is State.ErrorState -> {
                    Toast.makeText(context, "Error: ${uiState.exception.message}", Toast.LENGTH_SHORT).show()
                }

                State.LoadingState -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
            }

            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyProductsTheme {
        Greeting("Android")
    }
}