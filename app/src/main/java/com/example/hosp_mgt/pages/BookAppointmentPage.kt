package com.example.hosp_mgt.pages

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hosp_mgt.AuthViewModel
import com.example.hosp_mgt.R
import com.example.hosp_mgt.components.DatePickerDropdown
import com.example.hosp_mgt.components.TimeSlotPickerComponent
import com.example.hosp_mgt.models.Appointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookAppointmentsPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser

    var selectedDate by remember { mutableStateOf("") }
    var bookedSlots by remember { mutableStateOf(setOf<String>()) }
    var selectedTimeSlot by remember { mutableStateOf<String?>(null) }

    val availableTimeSlots = listOf(
        "10:00 AM - 11:00 AM",
        "11:00 AM - 12:00 PM",
        "12:00 PM - 01:00 PM",
        "02:00 PM - 03:00 PM",
        "03:00 PM - 04:00 PM"
    )

    // Fetch booked slots from Firestore whenever the selected date changes
    LaunchedEffect(selectedDate) {
        if (selectedDate.isNotEmpty()) {
            firestore.collection("appointments")
                .whereEqualTo("date", selectedDate)
                .get()
                .addOnSuccessListener { documents ->
                    val slots = documents.mapNotNull { it.getString("timeSlot") }.toSet()
                    bookedSlots = slots
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error fetching slots: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown Date Picker
            DatePickerDropdown(selectedDate = selectedDate) { date ->
                selectedDate = date
                selectedTimeSlot = null // Reset selected slot when date changes
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show message if no date is selected
            if (selectedDate.isEmpty()) {
                Text(
                    text = "Please select a date to view available slots.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                // Time Slot Buttons
                availableTimeSlots.forEach { slot ->
                    val isBooked = bookedSlots.contains(slot)
                    Button(
                        onClick = { selectedTimeSlot = slot },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        enabled = !isBooked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isBooked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = slot,
                            color = if (isBooked) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // "Book Appointment" Button
                Button(
                    onClick = {
                        if (selectedTimeSlot != null && currentUser != null) {
                            val appointment = Appointment(
                                userId = currentUser.uid,
                                date = selectedDate,
                                timeSlot = selectedTimeSlot!!
                            )

                            firestore.collection("appointments")
                                .add(appointment)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Appointment booked!", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home")
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else if (currentUser == null) {
                            Toast.makeText(context, "Please sign in to book an appointment.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedTimeSlot != null, // Disable if no slot is selected
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Book Appointment", color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
