package com.example.staysafe.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.staysafe.R
import com.example.staysafe.data.models.User
import com.example.staysafe.viewModel.ProfileViewModel
import com.google.maps.android.compose.Circle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    viewModel: ProfileViewModel = viewModel(),
    userID: Int,
    onMapClicked: (userID: Int) -> Unit,
    onContactsClicked: (userID: Int) -> Unit,
    onPlacesClicked: (userID: Int) -> Unit,
) {
    val user = viewModel.user.collectAsStateWithLifecycle()
    val selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Stats", "Settings")


    LaunchedEffect(user) {
        viewModel.getUser(userID)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
            )
        },
        bottomBar = {
            NavigationBar() {
                // Map
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.LocationOn, contentDescription = "Map") },
                    label = { Text("Map") },
                    onClick = { onMapClicked(userID) },
                    selected = false
                )
                // Contacts
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    onClick = { onContactsClicked(userID) },
                    selected = false
                )
                // Places
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Flag, contentDescription = "Places") },
                    label = { Text("Places") },
                    onClick = { onPlacesClicked(userID) },
                    selected = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
                    label = { Text("Settings") },
                    onClick = { },
                    selected = true
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            UserDetails(user.value)

            SecondaryTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, tab ->
                    Tab(

                        )
                }
            }
        }
    }
}

@Composable
fun UserDetails(user: User?) {

    val placeholderImage = painterResource(id = R.drawable.placeholder)

    Row() {
        Image(
            painter = placeholderImage,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Gray, CircleShape)
                .clip(CircleShape)
        )


        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            Text(user?.username ?: "Loading...")
            Text("${user?.firstName ?: "Loading..."} ${user?.lastName ?: ""}")
            Text(user?.phone ?: "Loading...")
        }
    }
}

@Composable
fun UserStats(user: User?) {
    Column() {
        Text("No. contacts: 0")
        Text("No. places: 1")
        Text("No. activities: 2")
    }
}

@Composable
fun SettingsPage() {
    UpdateDetails()

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {}
    ) {
        Text("Delete account")
    }
}

@Composable
fun UpdateDetails() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Update your details")
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = {}
            ) {
                Text("Change username")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {}
            ) {
                Text("Change password")
            }
        }
    }
}