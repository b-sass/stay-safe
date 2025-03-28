package com.example.madproject.data.models

data class RouteMatrix(
    val distanceMeters: Int,
    val durationSeconds: Int,
    val polyline: String? = null
)