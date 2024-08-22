package edu.uvg.composableui_usingai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import edu.uvg.composableui_usingai.ui.theme.ComposableUI_UsingAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposableUI_UsingAITheme {

            }
        }
    }
}

//Este composable fue creado utilizando ChatGPT, el prompt fue el siguiente
/*
*

In android context using jetpack compose material 3, I need a contact card which has two columns, in the first column we found a circled avatar in the top left, in the 2nd column we found firstname lastname in bold, below we found phone number, below we found email address background color of the card is navy blue and the letters are white

*
*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    avatarUrl: String,
    firstName: String,
    lastName: String,
    phoneNumber: String,
    email: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF000080)),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar)
                , contentDescription = null
                , contentScale = ContentScale.Crop
                , modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Second Column - Name, Phone, Email
            Column {
                Text(
                    text = "$firstName $lastName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = phoneNumber,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = email,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContactCard() {
    ContactCard(
        avatarUrl = "https://via.placeholder.com/150",
        firstName = "John",
        lastName = "Doe",
        phoneNumber = "123-456-7890",
        email = "john.doe@example.com"
    )
}