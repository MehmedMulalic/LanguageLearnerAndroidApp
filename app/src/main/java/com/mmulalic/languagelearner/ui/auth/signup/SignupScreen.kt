package com.mmulalic.languagelearner.ui.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onLoginSelect: () -> Unit,
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    val state by signupViewModel.uiState.collectAsState()

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onSignupSuccess()
        }
    }

    SignupForm(
        state,
        signupViewModel::onUsernameChange,
        signupViewModel::onPasswordChange,
        signupViewModel::onConfirmPasswordChange,
        signupViewModel::signup,
        onLoginSelect,
    )
}

@Composable
fun SignupForm(
    state: SignupUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignupClick: () -> Unit,
    onLoginSelect: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "App Name",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            "Register",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            value = state.username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            maxLines = 1,
            isError = state.errorMessage != null,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            maxLines = 1,
            isError = state.errorMessage != null,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                )
            }
        )
        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirm Password") },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            maxLines = 1,
            isError = state.errorMessage != null,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(
                    imageVector = image,
                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                    modifier = Modifier.clickable { confirmPasswordVisible = !confirmPasswordVisible }
                )
            }
        )
        if (state.errorMessage != null) {
            TODO()
        }
        Button(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            onClick = onSignupClick
        ) {
            Text("Sign up")
        }
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            "Already have an account?",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = { onLoginSelect() }
        ) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    SignupForm(
        SignupUiState(),
        {},
        {},
        {},
        {},
        {}
    )
}