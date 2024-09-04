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
        var name: String? = "Android"
        name = intent.getStringExtra("name")

        setContent{
            NavigationDrawerTheme {
                HelloText(name)
            }
        }
    }
}

@Composable
fun HelloText(name: String? = "Android"){
    Text(text = "Hello " + name)
}