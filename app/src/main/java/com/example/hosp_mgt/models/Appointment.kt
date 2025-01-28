package com.example.hosp_mgt.models

data class Appointment(
    val userId: String = "",      // ID of the user booking the appointment
    val date: String = "",        // Selected date (e.g., "2025-01-27")
    val timeSlot: String = ""     // Selected time slot (e.g., "10:00 AM - 11:00 AM")
)
