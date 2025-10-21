package me.marcosvalera.unabstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {

            val navController= rememberNavController()
            var startDestination= "login"

            val auth = Firebase.auth
            val currentUser= auth.currentUser
            if (currentUser != null){
                startDestination ="home"
            }else{
                startDestination = "login"
            }

            NavHost(navController=navController,
                startDestination=startDestination,
                modifier = Modifier.fillMaxSize()
            ){
                composable(route= "login"){
                    LoginScreen(onClickregister = { navController.navigate("regis") },
                        onsuccessfulLogin = {navController.navigate("home"){
                            popUpTo("login"){inclusive=true}
                        } })
                }
                composable ("regis"){
                    RegisterScreen(onClickback = {navController.popBackStack()},
                        onSusccessfullResgister = {navController.navigate("home"){
                        popUpTo("login"){inclusive=true}
                    } }
                    )

                }
                composable ("home"){
                    HomeScreen()
                }
            }
        }
    }
}

