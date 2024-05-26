package tayabas.anthony.myproducts.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import tayabas.anthony.myproducts.R
import tayabas.anthony.myproducts.data.entity.Product
import tayabas.anthony.myproducts.data.entity.SortOrder

@Composable
fun ProductListPage(productList: ArrayList<Product>) {
    var filteredProductList by remember { mutableStateOf(productList) }
    val productTypes = filteredProductList.map { it.type }.distinct()

    var searchText by remember { mutableStateOf("") }
    var sortOrder by remember {
        mutableStateOf(SortOrder.NO_ORDER)
    }
    var selectedType by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchWidget(searchText = searchText, onSearchTextChange = { searchText = it })
        SortPriceWidget(currentOrder = sortOrder) {
            sortOrder = when (sortOrder) {
                SortOrder.ASCENDING -> SortOrder.DESCENDING
                SortOrder.DESCENDING,
                SortOrder.NO_ORDER -> SortOrder.ASCENDING
            }
        }
        FilterTypeWidget(productTypes = productTypes, selectedType = selectedType) {
            selectedType = it
        }

        val sortedProductList by remember(searchText, sortOrder, selectedType) {
            mutableStateOf(
                when (sortOrder) {
                    SortOrder.ASCENDING -> {
                        filteredProductList
                            .filter {
                                it.name.contains(searchText, ignoreCase = true)
                            }
                            .filter {
                                it.type.contains(selectedType, ignoreCase = true)
                            }
                            .sortedBy { it.price }
                    }

                    SortOrder.DESCENDING -> {
                        filteredProductList
                            .filter {
                                it.name.contains(searchText, ignoreCase = true)
                            }
                            .filter {
                                it.type.contains(selectedType, ignoreCase = true)
                            }
                            .sortedByDescending { it.price }
                    }

                    SortOrder.NO_ORDER -> {
                        filteredProductList
                            .filter {
                                it.name.contains(searchText, ignoreCase = true)
                            }
                            .filter {
                                it.type.contains(selectedType, ignoreCase = true)
                            }
                    }
                }
            )
        }

        ProductGrid(productList = sortedProductList)
    }
}

@Composable
fun SearchWidget(searchText: String, onSearchTextChange: (String) -> Unit) {
    var text by remember { mutableStateOf(searchText) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onSearchTextChange(it)
        },
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun ProductGrid(productList: List<Product>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(productList) { product ->
            ProductGridItem(product = product)
        }
    }
}

@Composable
fun ProductGridItem(product: Product) {
    val displayDetails = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                displayDetails.value = true
            }
    ) {
        Box {
            AsyncImage(
                model = product.picture,
                placeholder = painterResource(id = R.drawable.loading),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter)
                    .background(Color.Gray.copy(alpha = 0.6f))
            ) {
                Text(
                    text = product.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp)
                )
                Text(
                    text = "$${product.price}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp)
                )
            }
        }
    }

    if (displayDetails.value) {
        ProductDetailView(product = product) {
            displayDetails.value = false
        }
    }
}