package com.example.hosp_mgt.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePicker(
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
) {
    val context = LocalContext.current

    val datePickerDialog = remember {
        createDatePickerDialog(context, selectedDate, onDateSelected)
    }

    // Show the dialog when clicked
    androidx.compose.material3.Button(onClick = { datePickerDialog.show() }) {
        val formattedDate = "${selectedDate.get(Calendar.DAY_OF_MONTH)}-${selectedDate.get(Calendar.MONTH) + 1}-${selectedDate.get(Calendar.YEAR)}"
        androidx.compose.material3.Text(text = "Select Date: $formattedDate")
    }
}

private fun createDatePickerDialog(
    context: Context,
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
): DatePickerDialog {
    return DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val newDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            onDateSelected(newDate)
        },
        selectedDate.get(Calendar.YEAR),
        selectedDate.get(Calendar.MONTH),
        selectedDate.get(Calendar.DAY_OF_MONTH)
    )
}
