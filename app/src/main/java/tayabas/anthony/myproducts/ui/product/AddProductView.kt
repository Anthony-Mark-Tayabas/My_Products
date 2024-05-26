package tayabas.anthony.myproducts.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import tayabas.anthony.myproducts.data.entity.Product

@Composable
fun AddProductView(setShowDialog: (Boolean) -> Unit, onAddProduct: (Product) -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                var name by remember {
                    mutableStateOf("")
                }

                var type by remember {
                    mutableStateOf("")
                }

                var pictureUrl by remember {
                    mutableStateOf("")
                }

                var price by remember {
                    mutableStateOf("")
                }

                var description by remember {
                    mutableStateOf("")
                }

                Text(
                    text = "Add a product",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text("Name")
                    }
                )
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text("Type")
                    }
                )
                OutlinedTextField(
                    value = pictureUrl,
                    onValueChange = { pictureUrl = it },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text("Picture URL")
                    }
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    label = {
                        Text("Price")
                    }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text("Description")
                    }
                )
                Button(
                    onClick = {
                        onAddProduct(
                            Product(
                                name = name,
                                type = type,
                                picture = pictureUrl,
                                price = price.toDouble(),
                                description = description
                            )
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Image(
                        imageVector = Icons.Filled.Add,
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = "Add product"
                    )
                    Text(
                        text = "Add Product",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}