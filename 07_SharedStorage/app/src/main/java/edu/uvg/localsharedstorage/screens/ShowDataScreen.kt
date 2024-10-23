package edu.uvg.localsharedstorage.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import edu.uvg.localsharedstorage.data.UserDataStore
import edu.uvg.localsharedstorage.data.UserData

@Composable
fun ShowDataScreen(userDataStore: UserDataStore) {
    // Remember a coroutine scope to handle launching coroutines in the UI
    val coroutineScope = rememberCoroutineScope()

    // State to hold the user data
    var userData by remember { mutableStateOf(UserData("", "", "", "", 0)) }

    // Load data from DataStore
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            userDataStore.userData.collect {
                userData = it
            }
        }
    }

    // Display user data
    UserInfoDisplay(userData)
}

@Composable
fun UserInfoDisplay(userData: UserData) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "User Information", style = MaterialTheme.typography.headlineSmall)

        Text(text = "First Name: ${userData.firstName}")
        Text(text = "Last Name: ${userData.lastName}")
        Text(text = "Birth Date: ${userData.birthDate}")
        Text(text = "Nationality: ${userData.nationality}")
        Text(text = "Age: ${userData.age}")
    }
}
