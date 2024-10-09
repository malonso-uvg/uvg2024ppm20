package edu.uvg.uvgchatejemplo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.uvg.uvgchatejemplo.screen.ChatRoomListScreen
import edu.uvg.uvgchatejemplo.screen.ChatScreen
import edu.uvg.uvgchatejemplo.screen.Screen
import edu.uvg.uvgchatejemplo.screen.SignUpScreen
import edu.uvg.uvgchatejemplo.screen.LoginScreen
import edu.uvg.uvgchatejemplo.ui.theme.UVGChatEjemploTheme
import edu.uvg.uvgchatejemplo.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            UVGChatEjemploTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavigationGraph(navController = navController, authViewModel = authViewModel)
                    }
                }

            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel
                ,onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) }
                , onSignInSuccess = { navController.navigate(Screen.ChatRoomsScreen.route) }
            )
        }
        composable(Screen.ChatRoomsScreen.route) {
            ChatRoomListScreen (
                onJoinClicked = { navController.navigate("${Screen.ChatScreen.route}/${it.id}") }
            )
        }

        composable("${Screen.ChatScreen.route}/{roomId}") {
            val roomId: String = it
                .arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)
        }
    }
}

