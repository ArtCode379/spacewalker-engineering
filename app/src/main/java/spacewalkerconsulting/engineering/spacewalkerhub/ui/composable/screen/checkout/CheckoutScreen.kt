package spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.screen.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.data.entity.BookingEntity
import spacewalkerconsulting.engineering.spacewalkerhub.data.repository.ServiceRepository
import spacewalkerconsulting.engineering.spacewalkerhub.ui.state.DataUiState
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.CheckoutViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CheckoutScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToBookingsScreen: () -> Unit,
) {
    val bookingState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalid by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val service = remember(serviceId) { ServiceRepository().getById(serviceId) }
    val enabled = viewModel.customerFirstName.isNotBlank() && viewModel.customerLastName.isNotBlank() &&
        viewModel.customerEmail.isNotBlank() && phone.isNotBlank() && selectedDate.isNotBlank()

    if (bookingState is DataUiState.Populated) {
        CheckoutDialog((bookingState as DataUiState.Populated<BookingEntity>).data, service?.name.orEmpty(), selectedDate, onNavigateToBookingsScreen)
    }
    CheckoutContent(
        serviceName = service?.name.orEmpty(),
        servicePrice = service?.price ?: 0.0,
        firstName = viewModel.customerFirstName,
        lastName = viewModel.customerLastName,
        email = viewModel.customerEmail,
        phone = phone,
        notes = notes,
        selectedDate = selectedDate,
        isEmailInvalid = emailInvalid,
        modifier = modifier,
        focusManager = LocalFocusManager.current,
        isButtonEnabled = enabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPhoneChanged = { phone = it },
        onNotesChanged = { notes = it },
        onDateClick = { showDatePicker = true },
        onConfirm = { viewModel.placeBooking(serviceId) },
    )
    if (showDatePicker) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                    }
                    showDatePicker = false
                }) { Text("Select") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } },
        ) { DatePicker(state = pickerState) }
    }
}

@Composable
private fun CheckoutContent(
    serviceName: String,
    servicePrice: Double,
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    notes: String,
    selectedDate: String,
    isEmailInvalid: Boolean,
    modifier: Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit,
    onDateClick: () -> Unit,
    onConfirm: () -> Unit,
) {
    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Book consultation", style = MaterialTheme.typography.headlineMedium)
        Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(serviceName, style = MaterialTheme.typography.titleLarge)
                Text("From £${servicePrice.toInt()}", color = MaterialTheme.colorScheme.secondary)
            }
        }
        CheckoutTextField(firstName, onFirstNameChanged, "First name", Modifier.fillMaxWidth())
        CheckoutTextField(lastName, onLastNameChanged, "Last name", Modifier.fillMaxWidth())
        CheckoutTextField(
            email,
            onEmailChanged,
            "Email",
            Modifier.fillMaxWidth(),
            isError = isEmailInvalid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        CheckoutTextField(phone, onPhoneChanged, "Phone", Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { onDateClick() },
            readOnly = true,
            label = { Text("Preferred date") },
            trailingIcon = { Icon(Icons.Default.CalendarMonth, null) },
            modifier = Modifier.fillMaxWidth().clickable { onDateClick() },
        )
        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChanged,
            label = { Text("Project notes") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                focusManager.clearFocus()
                onConfirm()
            },
            enabled = isButtonEnabled,
            modifier = Modifier.fillMaxWidth().height(54.dp),
        ) { Text("Confirm Booking") }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        label = { Text(labelText) },
        modifier = modifier,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
    )
}

@Composable
fun CheckoutDialog(
    booking: BookingEntity,
    serviceName: String,
    selectedDate: String,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text("Consultation confirmed") },
        text = {
            Text(
                "Session #${booking.bookingNumber}\n$serviceName · $selectedDate\n\n" +
                    "Your engineering consultant will be ready in the online conference or at the project address " +
                    "at the appointed time."
            )
        },
        confirmButton = { TextButton(onClick = onConfirm) { Text("View bookings") } },
    )
}
