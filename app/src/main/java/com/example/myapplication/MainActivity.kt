package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.glide.rememberGlidePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm = MainViewModel()
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Main(vm)
                }
            }
        }
    }
}

@Composable
fun Main(vm: MainViewModel) {
    LaunchedEffect(key1 = Unit, block = { vm.init() })

    Scaffold(
        topBar = { TopAppBar(title = { Text("Users") }, backgroundColor = Color.LightGray) },
        content = {
            if (vm.errorMessage.isNotBlank()) {
                Text(text = "Error ${vm.errorMessage}!")
            } else {
                UserList(vm.state)
            }
        },
    )
}

@Composable
fun UserList(users: List<GetUserList.Data>) {
    LazyColumn {
        items(items = users) { user ->

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberGlidePainter(request = user.avatarUrl, fadeIn = true),
                    contentDescription = "User profile picture",
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                )

                Text(user.name, modifier = Modifier.padding(16.dp))
            }
            Divider()
        }
    }
}