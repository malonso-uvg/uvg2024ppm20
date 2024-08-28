package edu.uvg.a03_androidnavigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.uvg.a03_androidnavigation.ui.theme._03_AndroidNavigationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _03_AndroidNavigationTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onOptionSelected = { option ->
                    when (option) {
                        "Option 1" -> {
                            val intent = Intent(context, Option1Activity::class.java)
                            context.startActivity(intent)
                        }
                        // Agrega más opciones
                    }
                    scope.launch { drawerState.close() }
                },
                backgroundColor = Color(0xFF6200EE)
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(onMenuClicked = {
                    scope.launch { drawerState.open() }
                })
            },
            bottomBar = { BottomNavigationBar(navController = rememberNavController()) }
        ) { innerPadding ->
            NavigationHost(navController = rememberNavController(), Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen() }
        composable("profile") { ProfileScreen() }
        composable("about") { AboutScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onMenuClicked: () -> Unit) {
    TopAppBar(
        title = { Text("My App", textAlign = TextAlign.Center) },
        navigationIcon = {
            IconButton(onClick = { onMenuClicked() }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
fun DrawerContent(onOptionSelected: (Any?) -> Unit, backgroundColor: Color) {
    Surface(
        color = backgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = { onOptionSelected(null) }) {
                Text("Opción 1", color = Color.White)
            }
            TextButton(onClick = { onOptionSelected(null) }) {
                Text("Opción 2", color = Color.White)
            }
            TextButton(onClick = { onOptionSelected(null) }) {
                Text("Opción 3", color = Color.White)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Home"),
        BottomNavItem("profile", Icons.Default.Person, "Profile"),
        BottomNavItem("about", Icons.Default.Info, "About")
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}


@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home Screen", modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ProfileScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Profile Screen", modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun AboutScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "About Screen", modifier = Modifier.fillMaxSize())
    }
}

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}