package com.example.hosp_mgt.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeSlotPickerComponent(
    timeSlots: List<String>,
    selectedSlot: String?,
    onSlotSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Available Time Slots",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        timeSlots.forEach { timeSlot ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedSlot == timeSlot,
                        onClick = { onSlotSelected(timeSlot) }
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedSlot == timeSlot) Color(0xFFBBDEFB) else Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = timeSlot,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
