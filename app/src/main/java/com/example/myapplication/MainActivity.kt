package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.MediumPadding
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.SmallPadding
import com.example.myapplication.ui.theme.TinyPadding
import com.google.accompanist.glide.rememberGlidePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) { Main() }
            }
        }
    }
}

@Composable
fun Main() {
    val vm = BeerListViewModel()
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar(title = { Text("Beers") }, backgroundColor = Color.LightGray) },
        content = {
            NavHost(navController = navController, startDestination = "beer_list") {
                composable("beer_list") {
                    InitialScreen(vm = vm) { navController.navigate("beer/$it") }
                }
                composable(
                    "beer/{beerId}",
                    arguments = listOf(navArgument("beerId") { type = NavType.IntType })
                ) { backStackEntry ->
                    Beer(beerId = backStackEntry.arguments?.getInt("beerId")!!)
                }
            }
        },
    )
}

@Composable
fun InitialScreen(vm: BeerListViewModel, onBeerSelected: (Int) -> Unit) {
    LaunchedEffect(key1 = Unit, block = { vm.init() })
    if (vm.errorMessage.isNotBlank()) {
        Text(text = "Error ${vm.errorMessage}!")
    } else {
        Loading(isLoading = vm.state.isEmpty()) {
            BeerList(vm.state, onBeerSelected)
        }
    }
}

@Composable
fun Loading(isLoading: Boolean, content: @Composable () -> Unit = {}, ){
    if(isLoading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        content()
    }
}

@Composable
fun BeerList(beers: List<GetBeerList.Data>, onBeerSelected: (Int) -> Unit) {
    LazyColumn {
        items(items = beers) { beer -> BeerRow(beer, onBeerSelected) }
    }
}

@Composable
private fun BeerRow(beer: GetBeerList.Data, onBeerSelected: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = TinyPadding)
            .clickable {
                onBeerSelected(beer.id)
            },
    ) {
        BeerImage(beer.avatarUrl)

        Column {
            Heading(beer.name)
            Body(beer.description)
        }
    }
    Divider()
}

@Composable
private fun BeerImage(url: String) {
    Image(
        painter = rememberGlidePainter(request = url, fadeIn = true),
        contentDescription = "Beer picture",
        modifier = Modifier
            .width(128.dp)
            .height(128.dp)
    )
}

@Composable
private fun Body(text: String) {
    Text(
        text,
        modifier = Modifier.padding(horizontal = MediumPadding, vertical = SmallPadding)
    )
}

@Composable
private fun Heading(text: String) {
    Text(
        text,
        modifier = Modifier.padding(horizontal = MediumPadding, vertical = SmallPadding),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )
}

@Composable
private fun Beer(vm: BeerViewModel =  BeerViewModel(), beerId: Int) {
    LaunchedEffect(key1 = Unit, block = { vm.init(beerId) })
    if (vm.errorMessage.isNotBlank()) {
        Text(text = "Error ${vm.errorMessage}!")
    } else {
        Loading(isLoading = vm.state == null) {
            BeerPage(vm.state!!)
        }
    }
}

@Composable
private fun BeerPage(beer: GetBeer.Data) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize()) {
        Heading(text = beer.name)
        BeerImage(url = beer.imageUrl)
        IngredientList(heading = "Malt", ingredients = beer.malt)
        IngredientList(heading = "Hops", ingredients = beer.hops)
        IngredientList(heading = "Yeast", ingredients = listOf(beer.yeast))
    }
}

@Composable
private fun IngredientList(heading: String, ingredients: List<String>){
    Heading(text = heading)
    Column {
        ingredients.forEach {
            Body(text = it)
        }
    }
}