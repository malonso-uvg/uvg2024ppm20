package edu.uvg.navigationdrawer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.uvg.navigationdrawer.CustomAppBar


@Composable
fun AboutScreen(drawerState: DrawerState){
    Scaffold(
        topBar = { CustomAppBar(drawerState = drawerState, title = "About us")}
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
            , horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.Center
        ) {
            Text(text = "About Screen")
        }

    }
}