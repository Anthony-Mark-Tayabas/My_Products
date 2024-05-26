package tayabas.anthony.myproducts.data.entity

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("type")
    val type: String
)