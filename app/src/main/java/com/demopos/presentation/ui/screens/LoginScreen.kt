package com.demopos.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.demopos.presentation.state.AuthEvent
import com.demopos.presentation.ui.components.AuthButton
import com.demopos.presentation.ui.components.AuthTextField
import com.demopos.presentation.ui.components.PosCheckbox
import com.demopos.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val events by viewModel.events.collectAsState()
    val rememberedUser by viewModel.rememberedUser.collectAsState()

    LaunchedEffect(events) {
        when (events) {
            is AuthEvent.LoginSuccess -> {
                viewModel.clearEvent()
                navController.navigate("dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is AuthEvent.LoginError -> {
                // Error is shown in UI state
                viewModel.clearEvent()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title
            Text(
                text = "DemoPOS",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Point of Sale System",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email Input
            AuthTextField(
                value = uiState.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                error = if (uiState.error?.contains("email") == true) uiState.error else null,
                enabled = !uiState.isLoading
            )

            // Password Input
            AuthTextField(
                value = uiState.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = "Password",
                isPassword = true,
                isPasswordVisible = uiState.isPasswordVisible,
                onPasswordVisibilityToggle = { viewModel.togglePasswordVisibility() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                error = if (uiState.error?.contains("password") == true) uiState.error else null,
                enabled = !uiState.isLoading
            )

            // Remember Me
            PosCheckbox(
                checked = uiState.rememberMe,
                onCheckedChange = { viewModel.toggleRememberMe() },
                label = "Remember me",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // Error Message
            if (uiState.error != null && !uiState.error!!.contains("email") && !uiState.error!!.contains("password")) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Login Button
            AuthButton(
                text = "Login",
                onClick = { viewModel.login() },
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Divider
            Divider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Quick PIN Login Link
            Text(
                text = "Quick PIN Login",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("pin_login")
                }
            )
        }
    }
}
