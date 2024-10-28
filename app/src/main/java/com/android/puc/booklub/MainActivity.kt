package com.android.puc.booklub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.puc.booklub.ui.LoginScreen
import com.android.puc.booklub.ui.LoginSignupViewModel
import com.android.puc.booklub.ui.theme.BooklubTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooklubTheme {
               if(auth.currentUser != null) {
                   Greeting("Login returned true")
               }
               else {
                   val vm = LoginSignupViewModel()
                   LoginScreen(vm)
               }
               }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BooklubTheme {
        Greeting("Android")
    }
}