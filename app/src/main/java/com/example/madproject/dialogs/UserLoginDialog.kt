package com.example.madproject.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.data.models.User
import com.example.madproject.viewModel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLoginDialog(
    onDismissRequest: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
            ) {
                Text(
                    text = "Log in to your account.",
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                        .padding(bottom = 16.dp),
                )
                HorizontalDivider()

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it } ,
                    label = { Text("Username") },
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it } ,
                    label = { Text("Password") },
                )

                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(bottom = 24.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Cancel",
                        modifier = Modifier.clickable { onDismissRequest() }
                    )
                    Spacer(Modifier.padding(horizontal = 8.dp))
                    Text(
                        text = "Sign in",
                        // Only delete book when delete button is clicked
                        modifier = Modifier.clickable {
                            val currentUser = viewModel.getUserWithCredentials(username, password)
                            currentUser?.let {
                                onDismissRequest()
                            }
                        }
                    )
                }
            }
        }
    }
}