package com.example.hosp_mgt.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hosp_mgt.AuthState
import com.example.hosp_mgt.AuthViewModel
import com.example.hosp_mgt.R

@Composable
fun SignupPage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("home")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background_image),  // Your image resource
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Content goes on top of the image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),  // Padding to match LoginPage
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title text
            Text(text = "Signup Page", fontSize = 32.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text(text = "Email", color = Color.Black)  // Label color matching LoginPage
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        // Handle next action for keyboard
                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = "Password", color = Color.Black)  // Label color matching LoginPage
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle done action for keyboard
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Signup Button
            Button(
                onClick = {
                    authViewModel.signup(email, password)
                },
                enabled = authState.value != AuthState.Loading,
                modifier = Modifier.fillMaxWidth()  // Ensure button fills the width
            ) {
                Text(text = "Signup", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // TextButton to navigate to login
            TextButton(onClick = {
                navController.navigate("login")
            }) {
                Text(text = "Already have an account? Login here", color = Color.Black)
            }
        }
    }
}