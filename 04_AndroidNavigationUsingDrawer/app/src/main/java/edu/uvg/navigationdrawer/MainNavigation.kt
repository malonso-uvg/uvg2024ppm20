package edu.uvg.navigationdrawer

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.uvg.navigationdrawer.screens.AboutScreen
import edu.uvg.navigationdrawer.screens.ArticlesScreen
import edu.uvg.navigationdrawer.screens.SettingsScreen
import edu.uvg.navigationdrawer.ui.theme.NavigationDrawerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


enum class MainRoute(value: String){
    Articles("articles")
    , About("about")
    , Settings("settings")
}

private data class DrawerMenu(var icon: ImageVector, val title: String, val route: String)

private val menus = arrayOf(
    DrawerMenu(Icons.Filled.Face, "Articles", MainRoute.Articles.name)
    , DrawerMenu(Icons.Filled.Settings, "Settings", MainRoute.Settings.name)
    , DrawerMenu(Icons.Filled.Info, "About Us", MainRoute.About.name)
)

@Composable
private fun DrawerContent(
    menus: Array<DrawerMenu>
    , onMenuClick: (String) -> Unit
){
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
            , contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.size(150.dp)
                , imageVector = Icons.Filled.AccountCircle
                , contentScale = ContentScale.Crop
                , contentDescription = null
            )
        } //End of Box implementation
        Spacer(modifier = Modifier.height(12.dp))
        menus.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) }
                , icon = { Icon(imageVector = it.icon, contentDescription = null) }
                , selected = false
                , onClick = {
                    onMenuClick(it.route)
                }
            ) //End of NavigationDrawerItem
        }//End of menus loop
        Spacer(modifier = Modifier.height(12.dp))
        NavigationDrawerItem( //New activity
            label = { Text("Nueva Activity") }
            , icon = { Icon(Icons.Default.Create, contentDescription = null) }
            , selected = false
            , onClick = {
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra("name", "Moises Alonso")
                context.startActivity(intent)
            }
        ) //End of NavigationDrawerItem


    } //End of Column implementation
}

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
    , coroutineScope: CoroutineScope = rememberCoroutineScope()
    , drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
){
    ModalNavigationDrawer(
        drawerState = drawerState
        , drawerContent = {
            ModalDrawerSheet{
                DrawerContent(menus) { route ->
                    coroutineScope.launch{
                        drawerState.close()
                    }
                    navController.navigate(route)
                } //End of DrawerContent
            } //End of ModalDrawerSheet
        } //End of drawerContent parameter
    ) {
        NavHost(
            navController = navController
            , startDestination = MainRoute.Articles.name
        ){
            composable(MainRoute.Articles.name){
                ArticlesScreen(drawerState = drawerState)
            }

            composable(MainRoute.About.name){
                AboutScreen(drawerState = drawerState)
            }

            composable(MainRoute.Settings.name){
                SettingsScreen(drawerState = drawerState)
            }
        }
    } //End of ModalNavigationDrawer
}

@Preview()
@Composable
private fun DrawerContentPreview(){
    NavigationDrawerTheme {
        DrawerContent(menus, {})
    }
}

@Preview
@Composable
fun MainNavigationPreview(){
    NavigationDrawerTheme {
        MainNavigation()
    }
}