package com.bharatsmartbazar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Saffron = Color(0xFFFF7A00)
private val Green = Color(0xFF0B8F3C)
private val Blue = Color(0xFF123C78)
private val Bg = Color(0xFFFFFBF7)

data class Product(val name:String,val sub:String,val price:String,val old:String,val off:String,val emoji:String)
data class Cat(val name:String,val emoji:String)
private val cats=listOf(Cat("Fresh\nVegetables","🥦"),Cat("Fruits","🍎"),Cat("Groceries","🌾"),Cat("Dairy &\nBreakfast","🥛"),Cat("Snacks &\nFoods","🍟"),Cat("Home Care","🧴"),Cat("Personal\nCare","🧼"),Cat("Offers","🎁"),Cat("More","▦"))
private val products=listOf(Product("Tomato","1 kg • Fresh","₹29","₹49","40% OFF","🍅"),Product("Amul Taaza","500 ml","₹44","₹55","20% OFF","🥛"),Product("India Gate Rice","1 kg","₹99","₹149","33% OFF","🍚"),Product("Parle-G","200 g","₹15","₹20","25% OFF","🍪"))

class MainActivity:ComponentActivity(){override fun onCreate(b:Bundle?){super.onCreate(b);setContent{App()}}}

@Composable fun App(){MaterialTheme(colorScheme=lightColorScheme(primary=Green,secondary=Saffron,tertiary=Blue,background=Bg)){var tab by remember{mutableIntStateOf(0)};var cart by remember{mutableIntStateOf(0)};Scaffold(containerColor=Bg,bottomBar={Nav(tab,cart){tab=it}}){p->if(tab==0)Home(Modifier.padding(p)){cart++}else Tab(Modifier.padding(p),when(tab){1->"Categories";2->"Your Cart";3->"Wishlist";else->"Profile"},cart)}}}

@Composable private fun Home(m:Modifier,onAdd:()->Unit){Column(m.fillMaxSize().verticalScroll(rememberScrollState())){Header();Search();Spacer(Modifier.height(12.dp));Banner();Spacer(Modifier.height(14.dp));Cats();Spacer(Modifier.height(18.dp));Title("🔥 Top Deals for You","View All");LazyRow(contentPadding=PaddingValues(16.dp),horizontalArrangement=Arrangement.spacedBy(10.dp)){items(products){ProductCard(it,onAdd)}};Spacer(Modifier.height(16.dp));Service();Text("Bharat Smart Bazar • Har Ghar Ki Zaroorat, Ab Aur Bhi Aasaan!",Modifier.fillMaxWidth().padding(16.dp),textAlign=TextAlign.Center,color=Blue,fontSize=12.sp,fontWeight=FontWeight.Bold)}}

@Composable private fun Header(){Box(Modifier.fillMaxWidth().height(155.dp).background(Brush.horizontalGradient(listOf(Color(0xFFFFF4E8),Color.White,Color(0xFFEAF8EF))))){Box(Modifier.fillMaxWidth().height(8.dp).background(Brush.horizontalGradient(listOf(Saffron,Color.White,Green))));Row(Modifier.padding(16.dp).padding(top=18.dp),verticalAlignment=Alignment.CenterVertically){Text("🛒",fontSize=42.sp);Spacer(Modifier.width(9.dp));Column{Text("Bharat",fontSize=29.sp,fontWeight=FontWeight.ExtraBold,color=Blue);Text("Smart Bazar",fontSize=27.sp,fontWeight=FontWeight.ExtraBold,color=Green);Text("Har Ghar Ki Zaroorat, Ab Aur Bhi Aasaan!",fontSize=10.sp,fontWeight=FontWeight.Bold,color=Blue)}};Surface(Modifier.align(Alignment.TopEnd).padding(top=25.dp,end=12.dp),shape=RoundedCornerShape(22.dp),shadowElevation=3.dp){Row(Modifier.padding(horizontal=10.dp,vertical=8.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Default.LocationOn,null,tint=Saffron,modifier=Modifier.size(18.dp));Text("Sonvarsa Chowk",fontSize=11.sp,fontWeight=FontWeight.Bold);Icon(Icons.Default.KeyboardArrowDown,null,modifier=Modifier.size(16.dp))}}}}

@Composable private fun Search(){Surface(Modifier.padding(horizontal=16.dp).fillMaxWidth(),shape=RoundedCornerShape(30.dp),shadowElevation=5.dp,color=Color.White){Row(Modifier.padding(14.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Default.Search,null,tint=Blue,modifier=Modifier.size(26.dp));Spacer(Modifier.width(9.dp));Text("Search for groceries, home essentials...",color=Color.Gray,modifier=Modifier.weight(1f),fontSize=13.sp);Icon(Icons.Default.Mic,null,tint=Green)}}}

@Composable private fun Banner(){Box(Modifier.padding(horizontal=16.dp).fillMaxWidth().height(164.dp).background(Brush.linearGradient(listOf(Saffron,Color(0xFFFFB02E),Green)),RoundedCornerShape(24.dp))){Column(Modifier.padding(18.dp).align(Alignment.CenterStart)){Text("FREEDOM",color=Color.White,fontWeight=FontWeight.ExtraBold,fontSize=18.sp);Text("SALE",color=Color.White,fontWeight=FontWeight.Black,fontSize=35.sp);Text("Up to 50% OFF",color=Blue,fontWeight=FontWeight.ExtraBold,fontSize=18.sp);Spacer(Modifier.height(6.dp));Button(onClick={},colors=ButtonDefaults.buttonColors(containerColor=Green),contentPadding=PaddingValues(horizontal=14.dp,vertical=5.dp)){Text("Shop Now  →",fontWeight=FontWeight.Bold)}};Text("🥬🍅🛒",fontSize=48.sp,modifier=Modifier.align(Alignment.BottomEnd).padding(15.dp))}}

@Composable private fun Cats(){LazyRow(contentPadding=PaddingValues(horizontal=16.dp),horizontalArrangement=Arrangement.spacedBy(12.dp)){items(cats){c->Column(horizontalAlignment=Alignment.CenterHorizontally,modifier=Modifier.width(76.dp)){Box(Modifier.size(66.dp).background(Color(0xFFEAF8EF),RoundedCornerShape(20.dp)),contentAlignment=Alignment.Center){Text(c.emoji,fontSize=30.sp)}Spacer(Modifier.height(5.dp));Text(c.name,fontSize=10.sp,fontWeight=FontWeight.Bold,textAlign=TextAlign.Center)}}}}

@Composable private fun Title(a:String,b:String){Row(Modifier.fillMaxWidth().padding(horizontal=16.dp),verticalAlignment=Alignment.CenterVertically){Text(a,Modifier.weight(1f),fontSize=19.sp,fontWeight=FontWeight.ExtraBold,color=Blue);Text("$b →",color=Saffron,fontWeight=FontWeight.Bold,fontSize=12.sp)}}

@Composable private fun ProductCard(p:Product,onAdd:()->Unit){Card(Modifier.width(178.dp),shape=RoundedCornerShape(20.dp),colors=CardDefaults.cardColors(Color.White),elevation=CardDefaults.cardElevation(3.dp)){Column(Modifier.padding(10.dp)){Box(Modifier.fillMaxWidth().height(110.dp).background(Color(0xFFF7F9FC),RoundedCornerShape(15.dp))){Text(p.emoji,fontSize=55.sp,modifier=Modifier.align(Alignment.Center));Text(p.off,Modifier.align(Alignment.TopEnd).background(Green,RoundedCornerShape(14.dp)).padding(6.dp),color=Color.White,fontSize=9.sp,fontWeight=FontWeight.ExtraBold)};Spacer(Modifier.height(7.dp));Text(p.name,fontWeight=FontWeight.ExtraBold,fontSize=14.sp);Text(p.sub,color=Color.Gray,fontSize=10.sp);Row(verticalAlignment=Alignment.CenterVertically){Text(p.price,color=Green,fontWeight=FontWeight.Black,fontSize=17.sp);Spacer(Modifier.width(5.dp));Text(p.old,color=Color.Gray,fontSize=10.sp)};Spacer(Modifier.height(6.dp));Button(onClick=onAdd,Modifier.fillMaxWidth().height(35.dp),shape=RoundedCornerShape(12.dp),colors=ButtonDefaults.buttonColors(Green),contentPadding=PaddingValues(0.dp)){Icon(Icons.Default.Add,null,modifier=Modifier.size(16.dp));Text("Add",fontWeight=FontWeight.ExtraBold)}}}}

@Composable private fun Service(){Row(Modifier.padding(horizontal=16.dp).fillMaxWidth().background(Brush.horizontalGradient(listOf(Color(0xFFFFF0D7),Color.White,Color(0xFFEAF8EF))),RoundedCornerShape(20.dp)).padding(13.dp),verticalAlignment=Alignment.CenterVertically){Text("🛵",fontSize=34.sp);Spacer(Modifier.width(8.dp));Column(Modifier.weight(1f)){Text("Fast & Free Delivery",color=Green,fontSize=16.sp,fontWeight=FontWeight.ExtraBold);Text("On orders above ₹499",color=Blue,fontSize=10.sp,fontWeight=FontWeight.Bold)}Text("🛡️\nSecure",textAlign=TextAlign.Center,color=Blue,fontSize=10.sp,fontWeight=FontWeight.Bold)}}

@Composable private fun Nav(tab:Int,cart:Int,on:(Int)->Unit){NavigationBar(containerColor=Color.White){listOf("Home" to Icons.Default.Home,"Categories" to Icons.Default.Category,"Cart" to Icons.Default.ShoppingCart,"Wishlist" to Icons.Default.FavoriteBorder,"Profile" to Icons.Default.Person).forEachIndexed{i,p->NavigationBarItem(selected=tab==i,onClick={on(i)},icon={if(i==2&&cart>0)BadgedBox({Badge{Text(cart.toString())}}){Icon(p.second,null)}else Icon(p.second,null)},label={Text(p.first,fontSize=9.sp)},colors=NavigationBarItemDefaults.colors(selectedIconColor=if(i==0)Saffron else Green,selectedTextColor=if(i==0)Saffron else Green,indicatorColor=Color(0xFFFFF2E7)))}}}

@Composable private fun Tab(m:Modifier,title:String,cart:Int){Column(m.fillMaxSize(),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){Icon(Icons.Default.Store,null,tint=Green,modifier=Modifier.size(64.dp));Text(title,fontSize=28.sp,fontWeight=FontWeight.ExtraBold,color=Blue);Spacer(Modifier.height(8.dp));Text(if(title=="Your Cart"&&cart>0)"$cart item(s) added" else "Bharat Smart Bazar demo screen",color=Color.Gray)}}
