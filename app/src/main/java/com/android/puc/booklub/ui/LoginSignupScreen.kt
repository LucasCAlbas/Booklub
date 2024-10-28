package com.android.puc.booklub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LoginScreen(viewModel: LoginSignupViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Booklub Login",
            style = MaterialTheme.typography.displayLarge,
            color = Color(0xFF3B3B3B),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                viewModel.loginOrSignup(email.value, password.value)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login / Signup")
        }

        when (loginState) {
            is LoginState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            is LoginState.Error -> {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is LoginState.SuccessLogin -> {
                Text(
                    text = "Login Successful!",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is LoginState.SuccessSignup -> {
                Text(
                    text = "Signup Successful!",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            is LoginState.Idle -> {
            }
            else -> {
                Text(
                    text = "An unknown error occurred while logging in / signing up. Please close the app and try again",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    //LoginScreen()
}

