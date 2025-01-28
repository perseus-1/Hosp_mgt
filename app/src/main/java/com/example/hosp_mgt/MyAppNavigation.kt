package com.example.hosp_mgt

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hosp_mgt.AuthViewModel
import com.example.hosp_mgt.pages.BookAppointmentsPage
import com.example.hosp_mgt.pages.HomePage
import com.example.hosp_mgt.pages.LoginPage
import com.example.hosp_mgt.pages.SignupPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // Login Page
        composable("login") {
            LoginPage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Signup Page
        composable("signup") {
            SignupPage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Home Page
        composable("home") {
            HomePage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Book Appointments Page
        composable("bookAppointments") {
            BookAppointmentsPage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}
