package com.example.es2cosmobile.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.es2cosmobile.R

@Composable
fun LoginScreen(
    state: LoginState,
    actions: LoginActions,
    onSubmit: () -> Unit,
    isPortrait: Boolean
) {

    if (isPortrait) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp, top = 50.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(60.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                "App Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.2f)

            )

            LoginForm(
                state = state,
                actions = actions,
                onSubmit = onSubmit,
                spacerHeight = 60
            )

        }

    } else {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxHeight()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.4f)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.size(16.dp))


                LoginForm(
                    state = state,
                    actions = actions,
                    onSubmit = onSubmit,
                    spacerHeight = 25
                )


            }
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun LoginForm(
    state: LoginState,
    actions: LoginActions,
    onSubmit: () -> Unit,
    spacerHeight: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(spacerHeight.dp))

        state.errorMessage?.let {
            Text(text = it, color = Color.Red)
            Spacer(modifier = Modifier.size(10.dp))
        }

        // Campo Username
        OutlinedTextField(
            value = state.username,
            onValueChange = actions::setUsername,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            label = { Text("Username") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f),
            enabled = !state.isLoading
        )

        Spacer(modifier = Modifier.size(spacerHeight.dp))

        // Campo Password
        OutlinedTextField(
            value = state.password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = actions::setPassword,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            label = { Text("Password") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f),
            enabled = !state.isLoading
        )

        Spacer(modifier = Modifier.size(spacerHeight.dp))

        // Pulsante di Login
        Button(
            onClick = {
                if (!state.canSubmit) return@Button
                onSubmit()
            },
            modifier = Modifier.width(150.dp),
            border = BorderStroke(1.dp, Color.Gray),
            enabled = !state.isLoading
        ) {
            Text("Accedi")
        }
    }
}
