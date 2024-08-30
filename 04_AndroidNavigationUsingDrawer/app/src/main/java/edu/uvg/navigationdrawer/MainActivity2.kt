package edu.uvg.navigationdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import edu.uvg.navigationdrawer.ui.theme.NavigationDrawerTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            NavigationDrawerTheme {
                HelloText()
            }
        }
    }
}

@Composable
fun HelloText(){
    Text(text = "Hello World")
}