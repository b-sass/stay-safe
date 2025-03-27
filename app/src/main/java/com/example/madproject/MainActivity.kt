package com.example.madproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.madproject.navigation.AppNavigation
import com.example.madproject.ui.theme.MadProjectTheme
import com.example.madproject.view.MapView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            MadProjectTheme {
                AppNavigation(applicationContext)
            }
        }
    }
}