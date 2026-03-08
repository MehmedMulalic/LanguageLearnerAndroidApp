package com.mmulalic.languagelearner.ui.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupSelect: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val state by loginViewModel.state.collectAsState()

    when (state) {
        LoginState.Loading -> LoadingForm()
        LoginState.Authenticated -> onLoginSuccess()
        LoginState.Unauthenticated -> LoginForm(loginViewModel, onSignupSelect)
        is LoginState.Error -> LoginForm(loginViewModel, onSignupSelect, true)
    }
}

@Composable
fun LoadingForm() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun LoginForm(viewModel: LoginViewModel, onSignupSelect: () -> Unit, isError: Boolean = false) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
            "Login",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            maxLines = 1,
            isError = isError,
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            maxLines = 1,
            isError = isError,
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
        if (isError) {
            Text(
                "Wrong username or password. Try again.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Button(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.login(username, password)
            }
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            "Don't have an account?",
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
            onClick = { onSignupSelect() }
        ) {
            Text("Sign up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSupportingText() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text("Password") },
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
        maxLines = 1,
        isError = false,
        modifier = Modifier
            .padding(horizontal = 36.dp)
            .fillMaxWidth(),
        supportingText = {
            Text("support")
        }
    )
}