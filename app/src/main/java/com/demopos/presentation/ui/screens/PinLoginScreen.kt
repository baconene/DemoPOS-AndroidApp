package com.demopos.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.demopos.presentation.state.AuthEvent
import com.demopos.presentation.viewmodels.PinLoginViewModel

@Composable
fun PinLoginScreen(
    navController: NavController,
    viewModel: PinLoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val events by viewModel.events.collectAsState()

    LaunchedEffect(events) {
        when (events) {
            is AuthEvent.LoginSuccess -> {
                viewModel.clearEvent()
                navController.navigate("dashboard") {
                    popUpTo("pin_login") { inclusive = true }
                }
            }
            is AuthEvent.PinLoginError -> {
                viewModel.clearEvent()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Quick PIN Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.size(48.dp)) // Spacer
            }

            // PIN Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "*".repeat(uiState.pin.length) + "_".repeat(maxOf(0, 4 - uiState.pin.length)),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 8.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Error or attempts message
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (uiState.attemptCount > 0) {
                Text(
                    text = "Attempts left: ${uiState.maxAttempts - uiState.attemptCount}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Spacer
            Box(modifier = Modifier.weight(1f))

            // PIN Pad
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Rows 1-3 (0-8)
                for (row in 0..2) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (col in 0..2) {
                            val digit = row * 3 + col + 1
                            PinPadButton(
                                text = digit.toString(),
                                onClick = { viewModel.appendPin(digit.toString()) },
                                enabled = !uiState.isLoading,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Row 4 (0, Delete)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PinPadButton(
                        text = "0",
                        onClick = { viewModel.appendPin("0") },
                        enabled = !uiState.isLoading,
                        modifier = Modifier.weight(1f)
                    )
                    PinPadButton(
                        text = "DEL",
                        onClick = { viewModel.deletePin() },
                        isDelete = true,
                        enabled = !uiState.isLoading,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Login button
                androidx.compose.material3.Button(
                    onClick = { viewModel.loginWithPin() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    enabled = uiState.pin.length >= 4 && !uiState.isLoading
                ) {
                    Text(if (uiState.isLoading) "Logging in..." else "Login")
                }
            }
        }
    }
}

@Composable
fun PinPadButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDelete: Boolean = false,
    enabled: Boolean = true
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .then(
                Modifier then androidx.compose.foundation.layout.heightIn(min = 60.dp)
            ),
        enabled = enabled,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = if (isDelete) FontWeight.Bold else FontWeight.Normal
        )
    }
}
