package com.example.cashiapp.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cashiapp.pressentation.states.UiState
import com.example.cashiapp.android.viewmodel.PaymentViewModel
import kotlinx.coroutines.delay

@Composable
fun MainScreen(viewModel: PaymentViewModel) {
    val payments by viewModel.payments.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("USD") }
    var displayMessage by remember { mutableStateOf<String?>(null) }

    // Clear fields when payment succeeds
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            email = ""
            amount = ""
            currency = "USD"
            viewModel.resetUiState()  // Reset so Success is only handled once
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cashi App", style = MaterialTheme.typography.headlineMedium)

        // Email Field
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            value = email,
            onValueChange = { email = it },
            label = { Text("Recipient Email") }
        )

        // Amount Field
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Currency Dropdown
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenuCurrency(selected = currency, onSelected = { currency = it })

        // CTA
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            onClick = {
                viewModel.sendPayment(email, amount.toDoubleOrNull() ?: 0.0, currency)
            },
            enabled = uiState != UiState.Loading
        ) {
            Text("Send Payment")
        }

        Spacer(modifier = Modifier.height(12.dp).align(Alignment.CenterHorizontally))

        // Processing of operation
        LaunchedEffect(uiState) {
            when (uiState) {
                UiState.Loading -> displayMessage = "Sending Payment..."
                UiState.Success -> {
                    displayMessage = "Payment Sent Successfully!"
                    delay(1000)  // wait for 500ms
                    displayMessage = null // or change to another message
                }
                is UiState.Failure -> displayMessage = (uiState as UiState.Failure).message
                else -> displayMessage = null
            }
        }
        if (displayMessage != null) {
            Text(
                text = displayMessage!!,
                color = when (uiState) {
                    UiState.Success -> Color.Green
                    UiState.Loading -> Color.Gray
                    is UiState.Failure -> Color.Red
                    else -> Color.Unspecified
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Transactions
        Spacer(modifier = Modifier.height(24.dp))
        Text("Transaction History", style = MaterialTheme.typography.headlineSmall)
        Divider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(payments.size) { index ->
                val payment = payments[index]
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp)) {
                        Text(
                            text = payment.recipientEmail,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Amount: ${payment.amount} ${payment.currency}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Timestamp: ${formatTimestamp(payment.timestamp)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadPayments()
    }
}

@Composable
fun DropdownMenuCurrency(selected: String, onSelected: (String) -> Unit) {
    val currencies = listOf("USD", "EUR")
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text("Currency: $selected")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            currencies.forEach {
                DropdownMenuItem(onClick = {
                    onSelected(it)
                    expanded = false
                }, text = { Text(it) })
            }
        }
    }
}

// Helper function to format timestamp (assuming millis)
fun formatTimestamp(ts: Long): String {
    return try {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
        sdf.format(java.util.Date(ts))
    } catch (e: Exception) {
        ""
    }
}
