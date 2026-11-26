
package com.bharatsmartbazar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Saffron = Color(0xFFFF9933)
private val Green = Color(0xFF138808)
private val Navy = Color(0xFF000080)
private val Background = Color(0xFFF5F7F5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BharatApp()
        }
    }
}

data class Grocery(
    val name: String,
    val price: String,
    val emoji: String,
    val category: String
)

@Composable
fun BharatApp() {
    var cart by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var search by remember { mutableStateOf("") }

    val products = listOf(
        Grocery("Fresh Tomato", "₹40 / kg", "🍅", "Vegetables"),
        Grocery("Potato", "₹30 / kg", "🥔", "Vegetables"),
        Grocery("Onion", "₹35 / kg", "🧅", "Vegetables"),
        Grocery("Amul Taaza Milk", "₹28 / 500ml", "🥛", "Dairy"),
        Grocery("Fresh Paneer", "₹90 / 200g", "🧀", "Dairy"),
        Grocery("India Gate Rice", "₹120 / kg", "🍚", "Grocery"),
        Grocery("Aashirvaad Atta", "₹55 / kg", "🌾", "Grocery"),
        Grocery("Parle-G Biscuits", "₹10 / pack", "🍪", "Snacks")
    )

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                val tabs = listOf(
                    "Home", "Search", "Cart", "Profile"
                )
                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.Search,
                    Icons.Default.ShoppingCart,
                    Icons.Default.Person
                )

                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                icons[index],
                                contentDescription = label
                            )
                        },
                        label = {
                            Text(
                                if (index == 2 && cart > 0)
                                    "Cart ($cart)" else label
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Green,
                            selectedTextColor = Green,
                            indicatorColor = Color(0xFFE7F5E8)
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Header()

            when (selectedTab) {
                0 -> {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        OutlinedTextField(
                            value = search,
                            onValueChange = { search = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text("Search rice, milk, vegetables...")
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Green,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        Spacer(Modifier.height(16.dp))

                        SaleBanner()

                        Spacer(Modifier.height(22.dp))

                        SectionTitle("Shop by Category")

                        Spacer(Modifier.height(12.dp))

                        val categories = listOf(
                            "🥦" to "Vegetables",
                            "🍎" to "Fruits",
                            "🥛" to "Dairy",
                            "🍚" to "Grocery",
                            "🍪" to "Snacks",
                            "🧴" to "Personal Care"
                        )

                        LazyRow(
                            horizontalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {
                            items(categories) { category ->
                                CategoryCard(
                                    category.first,
                                    category.second
                                )
                            }
                        }

                        Spacer(Modifier.height(22.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SectionTitle("Popular Products")
                            Spacer(Modifier.weight(1f))
                            Text(
                                "Fresh Picks",
                                color = Green,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        val filtered = products.filter {
                            it.name.contains(search, ignoreCase = true)
                        }

                        filtered.forEach { product ->
                            ProductCard(product) {
                                cart++
                            }
                            Spacer(Modifier.height(12.dp))
                        }

                        Spacer(Modifier.height(12.dp))

                        DeliveryCard()

                        Spacer(Modifier.height(16.dp))

                        Text(
                            "🇮🇳 Made for your neighbourhood",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Navy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.height(20.dp))
                    }
                }

                1 -> {
                    Column(Modifier.padding(16.dp)) {
                        SectionTitle("Search Products")
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = search,
                            onValueChange = { search = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text("What do you need today?")
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Search, null)
                            }
                        )
                        Spacer(Modifier.height(16.dp))
                        products.filter {
                            it.name.contains(search, ignoreCase = true)
                        }.forEach {
                            ProductCard(it) { cart++ }
                            Spacer(Modifier.height(10.dp))
                        }
                    }
                }

                2 -> {
                    Column(Modifier.padding(20.dp)) {
                        SectionTitle("Your Shopping Cart")
                        Spacer(Modifier.height(18.dp))
                        Text(
                            "🛒",
                            fontSize = 55.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "$cart items added to your cart",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(18.dp))
                        Button(
                            onClick = { selectedTab = 0 },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Green
                            )
                        ) {
                            Text("Continue Shopping")
                        }
                        Text(
                            "Demo cart: checkout and payment will be added in the next stage.",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                3 -> {
                    Column(Modifier.padding(20.dp)) {
                        SectionTitle("My Profile")
                        Spacer(Modifier.height(20.dp))
                        Text("🇮🇳 Bharat Smart Bazar", fontSize = 22.sp,
                            fontWeight = FontWeight.Bold, color = Navy)
                        Spacer(Modifier.height(10.dp))
                        Text("📍 Sonvarsa Chowk, Samastipur, Bihar")
                        Spacer(Modifier.height(16.dp))
                        Text("🛍️ My Orders")
                        Spacer(Modifier.height(12.dp))
                        Text("💳 Payment Options")
                        Spacer(Modifier.height(12.dp))
                        Text("📞 Contact Shop")
                    }
                }
            }
        }
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "🇮🇳 Bharat Smart Bazar",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Navy
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    "📍 Sonvarsa Chowk, Samastipur, Bihar",
                    fontSize = 11.sp,
                    color = Color.DarkGray
                )
                Text(
                    "Your Neighbourhood Grocery Store",
                    fontSize = 11.sp,
                    color = Green,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                Icons.Default.ShoppingCart,
                contentDescription = "Shopping",
                tint = Green,
                modifier = Modifier.size(30.dp)
            )
        }

        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f).height(5.dp).background(Saffron))
            Box(Modifier.weight(1f).height(5.dp).background(Color.White))
            Box(Modifier.weight(1f).height(5.dp).background(Green))
        }
    }
}

@Composable
fun SaleBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Saffron
        )
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                "🇮🇳 BHARAT SMART BAZAR",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Freshness at Your Doorstep!",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Daily groceries • Great prices • Easy shopping",
                color = Color.White,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(30.dp),
                color = Color.White
            ) {
                Text(
                    "SHOP NOW  →",
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 9.dp
                    ),
                    color = Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        fontSize = 19.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Navy
    )
}

@Composable
fun CategoryCard(emoji: String, name: String) {
    Card(
        modifier = Modifier.width(94.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 30.sp)
            Spacer(Modifier.height(7.dp))
            Text(
                name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductCard(product: Grocery, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(76.dp),
                shape = RoundedCornerShape(15.dp),
                color = Color(0xFFF0F5EE)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(product.emoji, fontSize = 39.sp)
                }
            }

            Spacer(Modifier.width(13.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    product.category,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    product.price,
                    fontWeight = FontWeight.ExtraBold,
                    color = Green,
                    fontSize = 15.sp
                )
            }

            Button(
                onClick = onAdd,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                ),
                contentPadding = PaddingValues(
                    horizontal = 13.dp,
                    vertical = 7.dp
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Text("ADD", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DeliveryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE7F5E8)
        )
    ) {
        Row(
            modifier = Modifier.padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🚚", fontSize = 36.sp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    "Fast & Safe Home Delivery",
                    fontWeight = FontWeight.ExtraBold,
                    color = Green,
                    fontSize = 15.sp
                )
                Text(
                    "Shopping made easy for your family",
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
