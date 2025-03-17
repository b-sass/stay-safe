package com.example.madproject.data.repositories

import com.example.madproject.data.sources.ActivityRemoteDataSource
import com.example.madproject.data.sources.LocationRemoteDataSource
import com.example.madproject.data.sources.StatusRemoteDataSource

class ActivityRepository (
    private val activityRemoteDataSource: ActivityRemoteDataSource,
    private val locationRemoteDataSource: LocationRemoteDataSource,
    private val statusRemoteDataSource: StatusRemoteDataSource,
)