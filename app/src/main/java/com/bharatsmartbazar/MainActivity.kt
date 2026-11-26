package com.bharatsmartbazar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Saffron = Color(0xFFFF9933)
private val IndiaGreen = Color(0xFF138808)
private val ChakraBlue = Color(0xFF000080)
private val LightBackground = Color(0xFFF7F8FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BharatSmartBazarApp()
        }
    }
}

@Composable
fun BharatSmartBazarApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = LightBackground
        ) {
            var cartCount by remember { mutableIntStateOf(0) }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopHeader()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    SearchBox()

                    Spacer(modifier = Modifier.height(16.dp))

                    SaleBanner()

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Shop by Category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CategoryRow()

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Popular Products",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProductCard(
                        name = "Fresh Tomato",
                        price = "₹40 / kg",
                        emoji = "🍅",
                        onAdd = { cartCount++ }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProductCard(
                        name = "Amul Taaza Milk",
                        price = "₹28 / 500 ml",
                        emoji = "🥛",
                        onAdd = { cartCount++ }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProductCard(
                        name = "India Gate Rice",
                        price = "₹120 / kg",
                        emoji = "🍚",
                        onAdd = { cartCount++ }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ProductCard(
                        name = "Parle-G Biscuits",
                        price = "₹10 / pack",
                        emoji = "🍪",
                        onAdd = { cartCount++ }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    DeliveryBanner()

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            BottomNavigation(cartCount = cartCount)
        }
    }
}

@Composable
fun TopHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "🇮🇳 Bharat Smart Bazar",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ChakraBlue
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Sonvarsa Chowk, Samastipur, Bihar",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = IndiaGreen,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(Saffron)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(Color.White)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(IndiaGreen)
            )
        }
    }
}

@Composable
fun SearchBox() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text("Search groceries, rice, milk...")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        singleLine = true
    )
}

@Composable
fun SaleBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Saffron
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "🇮🇳 FREEDOM SALE",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Fresh groceries at great prices!",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Fast home delivery in your area",
                fontSize = 13.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CategoryRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CategoryItem(
            emoji = "🥦",
            name = "Vegetables",
            modifier = Modifier.weight(1f)
        )

        CategoryItem(
            emoji = "🥛",
            name = "Dairy",
            modifier = Modifier.weight(1f)
        )

        CategoryItem(
            emoji = "🍚",
            name = "Rice",
            modifier = Modifier.weight(1f)
        )

        CategoryItem(
            emoji = "🍪",
            name = "Snacks",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CategoryItem(
    emoji: String,
    name: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                fontSize = 27.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductCard(
    name: String,
    price: String,
    emoji: String,
    onAdd: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(75.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1F5F2)
                )
            ) {
                BoxContent(emoji)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = IndiaGreen
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Fresh • Quality assured",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Button(
                onClick = onAdd,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IndiaGreen
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )

                Spacer(modifier = Modifier.width(3.dp))

                Text("ADD")
            }
        }
    }
}

@Composable
fun BoxContent(emoji: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = emoji,
            fontSize = 35.sp
        )
    }
}

@Composable
fun DeliveryBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEAF6EC)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🚚",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Fast & Free Delivery",
                    fontWeight = FontWeight.Bold,
                    color = IndiaGreen
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Easy shopping from Bharat Smart Bazar",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun BottomNavigation(cartCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomItem(
            icon = Icons.Default.Home,
            label = "Home"
        )

        BottomItem(
            icon = Icons.Default.Search,
            label = "Search"
        )

        BottomItem(
            icon = Icons.Default.ShoppingCart,
            label = if (cartCount > 0) "Cart ($cartCount)" else "Cart"
        )

        BottomItem(
            icon = Icons.Default.Person,
            label = "Profile"
        )
    }
}

@Composable
fun BottomItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = ChakraBlue,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            fontSize = 11.sp
        )
    }
}
