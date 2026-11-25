package com.bharatsmartbazar.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Red = Color(0xFFD90000)
private val Green = Color(0xFF178A32)
private val Yellow = Color(0xFFFFC107)

data class Product(val name: String, val unit: String, val price: Int, val old: Int = 0, val category: String)

val products = listOf(
    Product("Aashirvaad Atta", "5 kg", 265, 295, "Atta & Dal"),
    Product("Fortune Sunflower Oil", "1 L", 145, 165, "Oil"),
    Product("Tata Salt", "1 kg", 28, 32, "Grocery"),
    Product("Toor Dal", "1 kg", 145, 160, "Atta & Dal"),
    Product("Amul Taaza Milk", "1 L", 58, 62, "Dairy"),
    Product("Parle-G Biscuits", "800 g", 75, 85, "Snacks"),
    Product("Tata Tea", "250 g", 125, 145, "Beverages"),
    Product("Coca-Cola", "750 ml", 45, 50, "Beverages")
)

@Composable
fun BsbApp() {
    var tab by remember { mutableIntStateOf(0) }
    var cart by remember { mutableStateOf(mapOf<String, Int>()) }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Red,
            secondary = Green,
            surface = Color.White
        )
    ) {
        Scaffold(
            topBar = { TopBar(cart.values.sum()) },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = tab == 0, onClick = { tab = 0 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
                    NavigationBarItem(selected = tab == 1, onClick = { tab = 1 }, icon = { Icon(Icons.Default.Category, null) }, label = { Text("Categories") })
                    NavigationBarItem(selected = tab == 2, onClick = { tab = 2 }, icon = { Icon(Icons.Default.ShoppingCart, null) }, label = { Text("Cart") })
                    NavigationBarItem(selected = tab == 3, onClick = { tab = 3 }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
                }
            }
        ) { pad ->
            when (tab) {
                0 -> HomeScreen(Modifier.padding(pad), cart) { p -> cart = cart + (p.name to ((cart[p.name] ?: 0) + 1)) }
                1 -> CategoryScreen(Modifier.padding(pad), cart) { p -> cart = cart + (p.name to ((cart[p.name] ?: 0) + 1)) }
                2 -> CartScreen(Modifier.padding(pad), cart, onRemove = { name -> cart = cart - name }, onCheckout = {})
                else -> ProfileScreen(Modifier.padding(pad))
            }
        }
    }
}

@Composable
fun TopBar(cartCount: Int) {
    Surface(shadowElevation = 2.dp) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.bsb_logo),
                contentDescription = "Bharat Smart Bazar",
                modifier = Modifier.size(54.dp).clip(RoundedCornerShape(27.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text("Bharat Smart Bazar", fontWeight = FontWeight.Bold, color = Red, fontSize = 18.sp)
                Text("Sonvarsa Chowk, Samastipur", fontSize = 12.sp, color = Color.Gray)
            }
            BadgedBox(badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } }) {
                Icon(Icons.Default.ShoppingCart, "Cart", tint = Red)
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier, cart: Map<String, Int>, add: (Product) -> Unit) {
    var search by remember { mutableStateOf("") }
    LazyColumn(modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            OutlinedTextField(
                value = search, onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search grocery products...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Red), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(18.dp)) {
                    Text("घर बैठे किराना मंगाइए", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Fast Home Delivery • Sonvarsa Chowk, Samastipur", color = Color.White)
                    Spacer(Modifier.height(10.dp))
                    Text("UPI • Easy Checkout • Best Prices", color = Yellow, fontWeight = FontWeight.Bold)
                }
            }
        }
        item { Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(listOf("Grocery", "Atta & Dal", "Oil", "Snacks", "Beverages", "Dairy")) { c ->
                    AssistChip(onClick = {}, label = { Text(c) }, leadingIcon = { Icon(Icons.Default.LocalGroceryStore, null) })
                }
            }
        }
        item { Text("Popular Products", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        items(products.filter { search.isBlank() || it.name.contains(search, true) }) { p ->
            ProductCard(p, add)
        }
    }
}

@Composable
fun CategoryScreen(modifier: Modifier, cart: Map<String, Int>, add: (Product) -> Unit) {
    var selected by remember { mutableStateOf("Grocery") }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Shop by Category", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listOf("Grocery","Atta & Dal","Oil","Snacks","Beverages","Dairy")) { c ->
                FilterChip(selected == c, onClick = { selected = c }, label = { Text(c) })
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(products.filter { it.category == selected }) { p -> ProductCard(p, add) }
        }
    }
}

@Composable
fun ProductCard(p: Product, add: (Product) -> Unit) {
    Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(72.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF7F7F7)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.LocalGroceryStore, null, tint = Green, modifier = Modifier.size(36.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(p.name, fontWeight = FontWeight.Bold)
                Text(p.unit, color = Color.Gray, fontSize = 13.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("₹${p.price}", color = Red, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    if (p.old > 0) {
                        Spacer(Modifier.width(7.dp))
                        Text("₹${p.old}", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            Button(onClick = { add(p) }, shape = RoundedCornerShape(10.dp), contentPadding = PaddingValues(horizontal = 12.dp)) {
                Text("ADD")
            }
        }
    }
}

@Composable
fun CartScreen(modifier: Modifier, cart: Map<String, Int>, onRemove: (String) -> Unit, onCheckout: () -> Unit) {
    val items = products.filter { cart.containsKey(it.name) }
    val total = items.sumOf { it.price * (cart[it.name] ?: 0) }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Your Cart", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        if (items.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Cart is empty") }
        } else {
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(items) { p ->
                    Card {
                        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(p.name, fontWeight = FontWeight.Bold)
                                Text("Qty: ${cart[p.name]} × ₹${p.price}")
                            }
                            Text("₹${p.price * (cart[p.name] ?: 0)}", fontWeight = FontWeight.Bold)
                            IconButton(onClick = { onRemove(p.name) }) { Icon(Icons.Default.Delete, "Remove") }
                        }
                    }
                }
            }
            Text("Total: ₹$total", fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onCheckout, modifier = Modifier.fillMaxWidth(), enabled = total > 0) {
                Icon(Icons.Default.Payment, null); Spacer(Modifier.width(8.dp)); Text("Proceed to UPI Checkout")
            }
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier) {
    Column(modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("My Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        ListItem(headlineContent = { Text("My Orders") }, leadingContent = { Icon(Icons.Default.ReceiptLong, null) })
        ListItem(headlineContent = { Text("Delivery Address") }, leadingContent = { Icon(Icons.Default.LocationOn, null) })
        ListItem(headlineContent = { Text("Help & Support") }, leadingContent = { Icon(Icons.Default.Help, null) })
        Text("Demo version • Bharat Smart Bazar • Samastipur", color = Color.Gray)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BsbApp() }
    }
}
