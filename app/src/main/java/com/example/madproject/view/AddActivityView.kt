package com.example.madproject.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.data.models.Location
import com.example.madproject.viewModel.ActivityViewModel

@Composable
fun AddActivityView(
    userID: Int,
    viewModel: ActivityViewModel = viewModel(),
    onSubmit: () -> Unit,
) {

    viewModel.userID = userID
    viewModel.getUser()

    var name by remember { mutableStateOf("dcfghj") }
    var description by remember { mutableStateOf("dfrvghnm") }
    var startDate by remember { mutableStateOf("2024-09-27T00:00:00.000Z") }
    var endDate by remember { mutableStateOf("2024-09-27T00:00:00.000Z") }
    var startName by remember { mutableStateOf("Home") }
    var endName by remember { mutableStateOf("Uni") }
    var startAddress by remember { mutableStateOf("Kingston Train Station") }
    var endAddress by remember { mutableStateOf("Kingston Uni Penrhyn ROad") }
    var startPostcode by remember { mutableStateOf("KT11UJ") }
    var endPostcode by remember { mutableStateOf("KT12EE") }
    var status by remember { mutableStateOf("Planned") }

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column {
                // Activity
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })

                HorizontalDivider()

                // Starting Location
                TextField(value = startName, onValueChange = { startName = it }, label = { Text("Start Name") })
                TextField(value = startAddress, onValueChange = { startAddress = it }, label = { Text("Start Location") })
                TextField(value = startPostcode, onValueChange = { startPostcode = it }, label = { Text("Start Postcode") })
                TextField(value = startDate.toString(), onValueChange = { startDate = it }, label = { Text("Start Date") })

                HorizontalDivider()

                // Ending Location
                TextField(value = endName, onValueChange = { endName = it }, label = { Text("End Name") })
                TextField(value = endAddress, onValueChange = { endAddress = it }, label = { Text("End Location") })
                TextField(value = endPostcode, onValueChange = { endPostcode = it }, label = { Text("End Postcode") })
                TextField(value = endDate.toString(), onValueChange = { endDate = it }, label = { Text("End Date") })

            }

            Button(
                onClick = {

                    val startCoords = viewModel.getLocation(startAddress, startPostcode)
                    val endCoords = viewModel.getLocation(endAddress, endPostcode)

                    val start = Location(
                        name = startName,
                        description = startName,
                        address = startAddress,
                        postcode = startPostcode,
                        latitude = startCoords?.first ?: 0.0,
                        longitude = startCoords?.second ?: 0.0,
                    )

                    val end = Location(
                        name = endName,
                        description = endName,
                        address = endAddress,
                        postcode = endPostcode,
                        latitude = endCoords?.first ?: 0.0,
                        longitude = endCoords?.second ?: 0.0,
                    )
                    viewModel.createActivity(
                        name, description, startDate, endDate, start, end
                    )
                    onSubmit()
                },
            ) {
                Text("Create activity")
            }
        }
    }
}