package com.example.staysafe.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.staysafe.R
import com.example.staysafe.data.models.User
import com.example.staysafe.dialogs.MessageDialog
import com.example.staysafe.viewModel.ProfileViewModel
import kotlinx.coroutines.launch

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
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()



    LaunchedEffect(user) {
        viewModel.getUser(userID)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
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
                    icon = { Icon(Icons.Outlined.Flag, contentDescription = "Places") },
                    label = { Text("Places") },
                    onClick = { onPlacesClicked(userID) },
                    selected = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
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
                    onClick = { scope.launch {
                        pagerState.animateScrollToPage(0)
                    } }
                )
                Tab(
                    selected = selectedTab == 1,
                    text = {Text("Settings")},
                    onClick = { scope.launch {
                        pagerState.animateScrollToPage(1)
                    } }
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { page ->
                when (page) {
                    1 -> { StatsPage(user.value) }
                    0 -> { SettingsPage(user.value) }
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
fun StatsPage(user: User?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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

    Column() {
        AppSettings()
        AccountSettings(user)
    }

}

@Composable
fun AppSettings() {
    var themeState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text("App Settings", color = MaterialTheme.colorScheme.primary)

        SettingRow(
            "Toggle theme",
            "Dark mode"
        ) {
            Switch(
                checked = themeState,
                onCheckedChange = { themeState = !themeState}
            )
        }
    }
}

@Composable
fun AccountSettings(user: User?) {

    var showUsernameUpdate by remember { mutableStateOf(false) }
    var showPasswordUpdate by remember { mutableStateOf(false) }
    var showDeleteAccount by remember { mutableStateOf(false) }


    if (showUsernameUpdate) {
        var newUsername by remember { mutableStateOf(user?.username ?: "Loading...") }

        MessageDialog(
            header = "Update username",
            onOkButtonClicked = { showUsernameUpdate = false },
            onDismissRequest = { showUsernameUpdate = false },
            okButtonLabel = "Save"
        ) {
            OutlinedTextField(
                value = newUsername,
                onValueChange = { newUsername = it },
                label = { Text("New username") }
            )
        }
    }

    if (showPasswordUpdate) {
        var newPassword by remember { mutableStateOf("") }

        MessageDialog(
            header = "Update password",
            onOkButtonClicked = { showPasswordUpdate = false },
            onDismissRequest = { showPasswordUpdate = false },
            okButtonLabel = "Save"
        ) {
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New password") },
                placeholder = { Text("New password") }
            )
        }
    }

    if (showDeleteAccount) {
        MessageDialog(
            header = "Delete Account",
            message = "This action cannot be undone",
            onOkButtonClicked = { showDeleteAccount = false },
            onDismissRequest = { showDeleteAccount = false },
            okButtonLabel = "Delete"
        )
    }

    Column(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text("Account Settings", color = MaterialTheme.colorScheme.primary)

        SettingRow(
            "Update profile photo"
        )

        SettingRow(
            title = "Update username",
            description = "[${user?.username ?: "Loading.."}]",
            onClick = {
                showUsernameUpdate = true
            }
        )

        SettingRow(
            "Update password",
            "[********]",
            onClick = {
                showPasswordUpdate = true
            }
        )

        SettingRow(
            "Delete account",
            "This action cannot be undone",
            titleColor = MaterialTheme.colorScheme.error,
            descriptionColor = MaterialTheme.colorScheme.onErrorContainer,
            onClick = {
                showDeleteAccount = true
            }
        )
    }

}

@Composable
fun SettingRow(
    title: String,
    description: String = "",
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {

    SettingRow(
        title = title,
        description = description,
        titleColor = Color.Black,
        onClick = onClick,
        descriptionColor = Color.Gray
    ) {
        content()
    }
}

@Composable
fun SettingRow(
    title: String,
    description: String = "",
    titleColor: Color,
    descriptionColor: Color,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Column() {
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 4.dp),
                color = titleColor,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            if (description.isNotEmpty()) {
                Text(
                    description,
                    color = descriptionColor,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            }
        }

        content()
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