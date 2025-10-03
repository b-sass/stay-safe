package com.example.staysafe.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
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
    var selectedTab by remember { mutableIntStateOf(0) }


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

            Spacer(modifier = Modifier.padding(8.dp))

            SecondaryTabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    text = { Text("Stats") },
                    onClick = { selectedTab = 0}
                )
                Tab(
                    selected = selectedTab == 1,
                    text = {Text("Settings")},
                    onClick = { selectedTab = 1}
                )
            }

            //TODO: Replace with HorizontalPager
            when (selectedTab) {
                0 -> StatsPage(user.value)
                1 -> SettingsPage(user.value)
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
fun StatsPage(user: User?) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Contacts", 15, Icons.Outlined.Person)
            StatCard("Activities", 24, Icons.AutoMirrored.Outlined.DirectionsWalk)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("Places", 8, Icons.Outlined.LocationOn)
            StatCard("Panic Button", 2, Icons.Outlined.Notifications)
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: Int,
    icon: ImageVector
) {
    Box(
        modifier = Modifier.size(120.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = "$title icon")
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = value.toString(), fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            }
        }
    }
}

@Composable
fun SettingsPage(user: User?) {
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

@Preview
@Composable
fun ProfilePreview() {
    ProfileView(
        userID = 1,
        onMapClicked = {},
        onPlacesClicked = {},
        onContactsClicked = {}
    )
}