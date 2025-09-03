package com.example.staysafe.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.staysafe.R
import com.example.staysafe.viewModel.ActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityView(
    userID: Int,
    viewModel: ActivityViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            LazyColumn() {
                items(5) { item ->
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = stringResource(id = R.string.placeholder_description),
                        )
                        Column {
                            Text("Activity $item")
                            Text("Activity description")
                        }
                    }
                }
            }
        }
    ) {
        content()
    }
}
