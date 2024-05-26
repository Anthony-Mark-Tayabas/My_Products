package tayabas.anthony.myproducts.ui.product

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import tayabas.anthony.myproducts.data.entity.SortOrder


@Composable
fun SortPriceWidget(currentOrder: SortOrder, onSortChanged: () -> Unit) {
    val order = when (currentOrder) {
        SortOrder.ASCENDING -> "Highest to lowest"
        SortOrder.DESCENDING,
        SortOrder.NO_ORDER -> "Lowest to highest"
    }
    OutlinedButton(
        onClick = onSortChanged,
    ) {
        Text(
            text = "Sort price: $order",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun FilterTypeWidget(productTypes: List<String>, selectedType: String, onSelectedTypeChanged: (String) -> Unit) {
    Row(modifier = Modifier
        .padding(bottom = 8.dp)) {
        if (selectedType.isNotEmpty()) {
            TextButton(onClick = { onSelectedTypeChanged("") }) {
                Text(
                    text = "clear",
                    style = MaterialTheme.typography.bodySmall,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        LazyRow {
            items(productTypes) { type ->
                OutlinedButton(
                    onClick = { onSelectedTypeChanged(type) },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        text = type.toLowerCase(Locale.current),
                        style = if (type == selectedType) MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight
                                .Bold
                        ) else MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight
                                .Normal
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}