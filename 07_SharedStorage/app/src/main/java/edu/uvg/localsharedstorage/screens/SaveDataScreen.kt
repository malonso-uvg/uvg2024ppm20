package edu.uvg.localsharedstorage.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.*
import edu.uvg.localsharedstorage.data.UserDataStore


@Composable
fun SaveDataScreen(userDataStore: UserDataStore) {
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    // State variables for form inputs
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    val nationalities = listOf("Guatemala", "Mexico", "USA", "Canada", "Other")


    // Load data if exists
    LaunchedEffect(Unit) {
        userDataStore.userData.collect { userData ->
            firstName = userData.firstName
            lastName = userData.lastName
            birthDate = userData.birthDate
            nationality = userData.nationality
            age = if (userData.age != 0) userData.age.toString() else ""
        }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(birthDate) { selectedDate ->
            birthDate = selectedDate
        }
        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenu(nationality, nationalities) { selectedNationality ->
            nationality = selectedNationality
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                userDataStore.saveUserData(
                    firstName,
                    lastName,
                    birthDate,
                    nationality,
                    age.toIntOrNull() ?: 0
                )
            }

            showDialog = true

        }) {
            Text("Save")
        }
    }

    // Dialog to indicate success
    if (showDialog) {
        ConfirmationDialog(
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun DatePickerField(value: String, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(date)
        }, year, month, day
    )

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text("Birth Date") },
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(selectedValue: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedValue,
            onValueChange = {},
            label = { Text("Nationality") },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }, text = { Text(option) })
            }
        }
    }
}

@Composable
fun ConfirmationDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = {
            Text(text = "Success")
        },
        text = {
            Text("Your data has been saved successfully.")
        }
    )
}