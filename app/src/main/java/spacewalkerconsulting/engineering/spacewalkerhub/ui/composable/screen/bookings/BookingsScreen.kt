package spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.screen.bookings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.shared.PBUHCContentWrapper
import spacewalkerconsulting.engineering.spacewalkerhub.ui.state.BookingUiState
import spacewalkerconsulting.engineering.spacewalkerhub.ui.state.DataUiState
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.Success
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.BookingViewModel

@Composable
fun BookingsScreen(modifier: Modifier = Modifier, viewModel: BookingViewModel = koinViewModel()) {
    val state by viewModel.bookingsState.collectAsState()
    var selected by remember { mutableStateOf<String?>(null) }
    PBUHCContentWrapper(
        dataState = state,
        dataPopulated = { BookingList((state as DataUiState.Populated).data, modifier) { selected = it } },
        dataEmpty = {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No bookings yet", style = MaterialTheme.typography.titleLarge)
                    Text("Browse Services from the Home tab", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
    )
    selected?.let { number ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text("Cancel this booking?") },
            text = { Text("The consultation slot will be released. This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { viewModel.cancelBooking(number); selected = null }) { Text("Confirm") }
            },
            dismissButton = { TextButton(onClick = { selected = null }) { Text("Keep booking") } },
        )
    }
}

@Composable
private fun BookingList(bookings: List<BookingUiState>, modifier: Modifier, onCancel: (String) -> Unit) {
    LazyColumn(modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Your consultations", style = MaterialTheme.typography.headlineMedium) }
        items(bookings, key = { it.bookingNumber }) { booking ->
            Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(booking.serviceName, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("Confirmed", color = Success, style = MaterialTheme.typography.labelMedium)
                    }
                    Text(booking.timestamp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Session #${booking.bookingNumber}", style = MaterialTheme.typography.labelMedium)
                    TextButton(onClick = { onCancel(booking.bookingNumber) }, modifier = Modifier.align(Alignment.End)) {
                        Text("Cancel", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
