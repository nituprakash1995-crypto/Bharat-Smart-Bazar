package com.bharatsmartbazar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF087A3D)
private val PaleGreen = Color(0xFFEAF6EE)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BharatSmartBazarApp() }
    }
}

data class GroceryProduct(val id: Int, val name: String, val detail: String, val price: Int, val emoji: String, val category: String)

private val products = listOf(
    GroceryProduct(1, "Aashirvaad Atta", "5 kg pack", 275, "🌾", "Atta & Rice"),
    GroceryProduct(2, "Fortune Sunflower Oil", "1 litre", 145, "🛢️", "Oil & Ghee"),
    GroceryProduct(3, "Amul Taaza Milk", "500 ml", 29, "🥛", "Dairy"),
    GroceryProduct(4, "Tata Salt", "1 kg", 28, "🧂", "Daily Needs"),
    GroceryProduct(5, "India Gate Basmati Rice", "1 kg", 115, "🍚", "Atta & Rice"),
    GroceryProduct(6, "Toor Dal", "1 kg", 165, "🫘", "Pulses"),
    GroceryProduct(7, "Sugar", "1 kg", 45, "🍬", "Daily Needs"),
    GroceryProduct(8, "Maggi Noodles", "Pack of 4", 56, "🍜", "Snacks")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BharatSmartBazarApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var search by remember { mutableStateOf("") }
    var cart by remember { mutableStateOf(mapOf<Int, Int>()) }
    var showCart by remember { mutableStateOf(false) }
    val count = cart.values.sum()
    val total = products.sumOf { p -> p.price * (cart[p.id] ?: 0) }

    MaterialTheme(colorScheme = lightColorScheme(primary = Green, secondary = Color(0xFFFFA000))) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("Bharat Smart Bazar", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.White)
                            Text("Sonvarsa Chowk, Samastipur, Bihar", fontSize = 11.sp, color = Color.White.copy(alpha = .9f))
                        }
                    },
                    actions = {
                        IconButton(onClick = { showCart = true }) {
                            BadgedBox(badge = { if (count > 0) Badge { Text(count.toString()) } }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Green)
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0; showCart = false }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
                    NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1; showCart = false }, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") })
                    NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2; showCart = false }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
                }
            }
        ) { padding ->
            when {
                showCart -> CartScreen(Modifier.padding(padding), cart, total, onBack = { showCart = false }, onChange = { id, qty -> cart = if (qty <= 0) cart - id else cart + (id to qty) })
                selectedTab == 2 -> ProfileScreen(Modifier.padding(padding))
                else -> HomeScreen(
                    Modifier.padding(padding), search, onSearch = { search = it },
                    filter = selectedTab == 1,
                    cart = cart,
                    onAdd = { id -> cart = cart + (id to ((cart[id] ?: 0) + 1)) },
                    onChange = { id, qty -> cart = if (qty <= 0) cart - id else cart + (id to qty) }
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    modifier: Modifier, search: String, onSearch: (String) -> Unit, filter: Boolean,
    cart: Map<Int, Int>, onAdd: (Int) -> Unit, onChange: (Int, Int) -> Unit
) {
    val shown = products.filter { it.name.contains(search, ignoreCase = true) || it.category.contains(search, ignoreCase = true) }
    LazyColumn(modifier.fillMaxSize().background(Color(0xFFF8FAF8)).padding(horizontal = 16.dp)) {
        item {
            Spacer(Modifier.height(14.dp))
            Card(colors = CardDefaults.cardColors(containerColor = PaleGreen), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(18.dp)) {
                    Text("घर बैठे किराना मँगाएँ!", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Green)
                    Text("Fresh groceries • Best prices • Home delivery", fontSize = 13.sp, color = Color.DarkGray)
                    Spacer(Modifier.height(8.dp))
                    Text("आपकी अपनी किराना दुकान — Bharat Smart Bazar", fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(14.dp))
            OutlinedTextField(value = search, onValueChange = onSearch, modifier = Modifier.fillMaxWidth(), placeholder = { Text("सामान खोजें / Search products") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true, shape = RoundedCornerShape(14.dp))
            Spacer(Modifier.height(14.dp))
            Text(if (filter) "Search Products" else "Popular Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Atta & Rice", "Dairy", "Pulses", "Snacks").forEach { category ->
                    AssistChip(onClick = { onSearch(category) }, label = { Text(category) })
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Popular Products", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(8.dp))
        }
        items(shown, key = { it.id }) { product ->
            ProductRow(product, cart[product.id] ?: 0, onAdd, onChange)
            Spacer(Modifier.height(9.dp))
        }
        item {
            if (shown.isEmpty()) Text("कोई सामान नहीं मिला।", modifier = Modifier.padding(20.dp))
            Spacer(Modifier.height(20.dp))
            Text("नोट: यह शुरुआती डेमो है। वास्तविक 1000+ उत्पाद, स्टॉक और डिलीवरी के लिए दुकान से डेटा जोड़ना होगा।", fontSize = 11.sp, color = Color.Gray)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ProductRow(product: GroceryProduct, qty: Int, onAdd: (Int) -> Unit, onChange: (Int, Int) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(58.dp).background(PaleGreen, CircleShape), contentAlignment = Alignment.Center) { Text(product.emoji, fontSize = 29.sp) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(product.detail, color = Color.Gray, fontSize = 12.sp)
                Text("₹${product.price}", color = Green, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
            if (qty == 0) {
                Button(onClick = { onAdd(product.id) }, contentPadding = PaddingValues(horizontal = 13.dp, vertical = 7.dp)) { Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp)); Text("ADD") }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = { onChange(product.id, qty - 1) }, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(34.dp)) { Text("−") }
                    Text(" $qty ", fontWeight = FontWeight.Bold)
                    Button(onClick = { onChange(product.id, qty + 1) }, contentPadding = PaddingValues(0.dp), modifier = Modifier.size(34.dp)) { Text("+") }
                }
            }
        }
    }
}

@Composable
private fun CartScreen(modifier: Modifier, cart: Map<Int, Int>, total: Int, onBack: () -> Unit, onChange: (Int, Int) -> Unit) {
    Column(modifier.fillMaxSize().background(Color(0xFFF8FAF8)).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
            Text("आपका Cart", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        }
        val itemsInCart = products.filter { (cart[it.id] ?: 0) > 0 }
        if (itemsInCart.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("आपका Cart खाली है। Home से सामान जोड़ें।") }
        } else {
            LazyColumn(Modifier.weight(1f)) {
                items(itemsInCart) { p ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(p.emoji, fontSize = 28.sp)
                        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) { Text(p.name, fontWeight = FontWeight.SemiBold); Text("₹${p.price} × ${cart[p.id]}", color = Color.Gray) }
                        Text("₹${p.price * (cart[p.id] ?: 0)}", fontWeight = FontWeight.Bold)
                        IconButton(onClick = { onChange(p.id, (cart[p.id] ?: 0) - 1) }) { Text("−") }
                        IconButton(onClick = { onChange(p.id, (cart[p.id] ?: 0) + 1) }) { Text("+") }
                    }
                    HorizontalDivider()
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("कुल राशि: ₹$total", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Green)
            Spacer(Modifier.height(8.dp))
            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("ऑर्डर / Checkout (Demo)") }
            Text("यह डेमो बटन वास्तविक ऑर्डर या भुगतान नहीं भेजता।", fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun ProfileScreen(modifier: Modifier) {
    Column(modifier.fillMaxSize().background(Color(0xFFF8FAF8)).padding(20.dp)) {
        Text("My Profile", fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("Bharat Smart Bazar", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        Text("Sonvarsa Chowk, Samastipur, Bihar")
        Spacer(Modifier.height(12.dp))
        Text("Home delivery उपलब्धता दुकान द्वारा पुष्टि की जाएगी।")
        Text("UPI / Online payment अभी demo में सक्रिय नहीं है।")
    }
}
